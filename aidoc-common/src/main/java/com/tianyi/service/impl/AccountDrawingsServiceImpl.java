package me.aidoc.appserver.service.user.impl;

import com.github.pagehelper.PageHelper;
import me.aidoc.appserver.common.PageData;
import me.aidoc.appserver.common.exception.ResponseCodeEnum;
import me.aidoc.appserver.common.exception.ServerBizException;
import me.aidoc.appserver.dao.user.AccountDrawingsMapper;
import me.aidoc.appserver.po.setting.AccountTransferSetting;
import me.aidoc.appserver.po.user.Account;
import me.aidoc.appserver.po.user.AccountDrawings;
import me.aidoc.appserver.po.user.AccountDrawingsRules;
import me.aidoc.appserver.service.BusinessEnum;
import me.aidoc.appserver.service.sys.AidocSetService;
import me.aidoc.appserver.service.user.AccountDrawingsService;
import me.aidoc.appserver.service.user.AccountFreezeDTO;
import me.aidoc.appserver.service.user.AccountService;
import me.aidoc.appserver.service.user.WalletUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 提币服务
 * @author vliu
 * @create 2018-08-27 18:53
 **/
@Service
public class AccountDrawingsServiceImpl implements AccountDrawingsService {

    @Autowired
    private AccountDrawingsMapper accountDrawingsMapper;

    @Autowired
    private AccountService accountService;

    @Autowired
    private AidocSetService aidocSetService;


    /**
     * 获取提币申请列表
     * @param businessNo       查询条件:业务号(可以为空)
     * @param phoneNumber      查询条件:手机号(可以为空)
     * @param startTime        查询条件:开始时间(可以为空)
     * @param stopTime         查询条件:结束时间(可以为空)
     * @param drawingsStatus   审核状态:(可以为空)
     * @param page             页数索引
     * @param size             分页大小
     * @return
     */
    @Override
    public PageData drawingsAplyList(String businessNo, String phoneNumber, Date startTime, Date stopTime,
                                     AccountDrawings.DrawingsStatus drawingsStatus, Integer page, Integer size) {
        PageHelper.startPage(page,size);
        return new PageData(accountDrawingsMapper.selectDrawingsList(businessNo,phoneNumber,startTime,stopTime,drawingsStatus));
    }

    /**
     * 查询申请提现的规则
     * @param userId
     * @return
     */
    @Override
    public AccountDrawingsRules getAccountDrawingsRules(Long userId) {
        AccountDrawingsRules drawingsRules = new AccountDrawingsRules();
        List<BigDecimal> drawingsAmountTodayAndMonth = accountDrawingsMapper.selectDrawingsAmountTodayAndMonth(userId);
        BigDecimal todayDrawingsAmount = drawingsAmountTodayAndMonth.get(0);//今日已提金额
        BigDecimal currentMonthDrawingsAmount = drawingsAmountTodayAndMonth.get(1);//本月已提金额
        if (todayDrawingsAmount == null){
            todayDrawingsAmount = BigDecimal.ZERO;
        }
        if (currentMonthDrawingsAmount == null){
            currentMonthDrawingsAmount = BigDecimal.ZERO;
        }
        AccountTransferSetting setting = aidocSetService.getAccountTransferSetting();//提币相关的设置
        drawingsRules.setBalance(accountService.getAccountBalance(userId));//账户余额
        drawingsRules.setTodayDrawingsAmount(todayDrawingsAmount);//今天已经提现金额
        drawingsRules.setCurrentMonthDrawingsAmount(currentMonthDrawingsAmount);//本月已经提现金额
        drawingsRules.setBalanceOfToday(setting.getDrawingsMaxOfDay().subtract(todayDrawingsAmount));//今天剩余可提现金额
        drawingsRules.setBalanceOfMonth(setting.getDrawingsMaxOfMonth().subtract(currentMonthDrawingsAmount));//本月剩余可提现金额
        drawingsRules.setMaxLimitOfDay(setting.getDrawingsMaxOfDay());//今天可提现总金额
        drawingsRules.setMaxLimitOfMonth(setting.getDrawingsMaxOfMonth());//本月可提现总金额
        drawingsRules.setMinLimitOfTime(setting.getDrawingsMinOfTime());//每次提现的最小金额
        drawingsRules.setServiceCharge(setting.getDrawingServiceCharge());//每笔提现的手续费
        return drawingsRules;
    }

    /**
     *
     * @param userId             发起者的用户ID
     * @param oppositeAddress    提币地址
     * @param amount             提币数量
     * @throws ServerBizException
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void drawings(Long userId, String oppositeAddress, BigDecimal amount) throws ServerBizException{
        Account account = accountService.getAccountByUserId(userId);
        if (account == null){//用户不存在
            throw new ServerBizException(ResponseCodeEnum.LOGIN_USER_NO_EXIST);
        }
        if (account.getStatus() != Account.AccountStatus.Normal){//用户状态异常
            throw new ServerBizException(ResponseCodeEnum.LOGIN_USER_NO_EXIST);
        }
        if (accountService.getAccountBalance(userId).compareTo(amount) < 0){//余额不足，提币失败
            throw new ServerBizException(ResponseCodeEnum.WALLET_WITHDRAW_BALANCE_LACK);
        }
        List<BigDecimal> drawingsAmountTodayAndMonth = accountDrawingsMapper.selectDrawingsAmountTodayAndMonth(userId);
        BigDecimal todayDrawingsAmount = drawingsAmountTodayAndMonth.get(0);//今日已提金额
        BigDecimal currentMonthDrawingsAmount = drawingsAmountTodayAndMonth.get(1);//本月已提金额
        if (todayDrawingsAmount == null){
            todayDrawingsAmount = BigDecimal.ZERO;
        }
        if (currentMonthDrawingsAmount == null){
            currentMonthDrawingsAmount = BigDecimal.ZERO;
        }

        AccountTransferSetting setting = aidocSetService.getAccountTransferSetting();

        if (todayDrawingsAmount.add(amount).compareTo(setting.getDrawingsMaxOfDay()) > 0){//达到今日提币上限
            throw new ServerBizException(ResponseCodeEnum.DRAWINGS_MAX_OF_DAY);
        }
        if (currentMonthDrawingsAmount.add(amount).compareTo(setting.getDrawingsMaxOfMonth())> 0){//达到本月提币上限
            throw  new ServerBizException(ResponseCodeEnum.DRAWINGS_MAX_OF_MONTH);
        }

        //==============================================================================================
        String businessNo = WalletUtils.generateOrderNo(BusinessEnum.Drawings);//业务流水号
        AccountFreezeDTO accountFreezeDTO = new AccountFreezeDTO();
        accountFreezeDTO.setUserId(userId);
        accountFreezeDTO.setAmount(amount);
        accountFreezeDTO.setBusinessType(BusinessEnum.Drawings);//提币冻结
        accountFreezeDTO.setBusinessNo(businessNo);
        int result = accountService.freezeBalance(accountFreezeDTO);//要提的币进入冻结状态
        if (result == AccountService.RESULT_ACCOUNT_NOT_EXIST){//用户不存在
            throw new ServerBizException(ResponseCodeEnum.LOGIN_USER_NO_EXIST);
        }else if (result == AccountService.RESULT_BALANCE_INSUFFICIENT){//余额不足
            throw new ServerBizException(ResponseCodeEnum.WALLET_WITHDRAW_BALANCE_LACK);
        }else if (result == AccountService.RESULT_SUCCESS){//冻结成功
            AccountDrawings accountDrawings = new AccountDrawings();
            accountDrawings.setAmount(amount);
            accountDrawings.setUserId(userId);
            accountDrawings.setStatus(AccountDrawings.DrawingsStatus.Pending);
            accountDrawings.setOppositeAddress(oppositeAddress);
            if (accountDrawingsMapper.insertDrawings(accountDrawings) == 0){//最后写入失败
                throw new ServerBizException(ResponseCodeEnum.SYSTEM_SERVER_ERROR);
            }
        }
    }


    /**
     *
     * @param userId       用户ID
     * @param adminId      操作的管理员ID
     * @param businessNo   业务流水号
     * @param pass         是否通过
     * @param remark       备注
     * @throws ServerBizException
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void audit(Long userId, Long adminId, String businessNo,boolean pass, String remark) throws ServerBizException {
        AccountDrawings accountDrawings = accountDrawingsMapper.selectDrawingsByBusinessNo(businessNo);
        if (accountDrawings == null){
            throw new ServerBizException(ResponseCodeEnum.SYSTEM_BUSINESS_NO_ERROR);
        }
        accountDrawings.setRemark(remark);//设置备注
        if (pass){//通过
            if (accountDrawings.getStatus() == AccountDrawings.DrawingsStatus.Pass){//已经通过了不用重复操作
                return;
            }
            accountDrawings.setStatus(AccountDrawings.DrawingsStatus.Pass);
            if (accountDrawingsMapper.updateDrawings(accountDrawings) == 0){//更新失败
                throw new ServerBizException(ResponseCodeEnum.SYSTEM_SERVER_ERROR);
            }
            //向钱包服务器发起调用
            callWalletServerDrawings();
        }else {//拒绝
            if (accountDrawings.getStatus() == AccountDrawings.DrawingsStatus.Reject){//已经拒绝了不用重复操作
                return;
            }
            accountDrawings.setStatus(AccountDrawings.DrawingsStatus.Reject);
            if (accountDrawingsMapper.updateDrawings(accountDrawings) == 0){//更新失败
                throw new ServerBizException(ResponseCodeEnum.SYSTEM_SERVER_ERROR);
            }
            int result = accountService.unfreezeBalance(userId,businessNo,true);//解冻被冻结的币
            if (result == 0){

            }
        }
    }


    /**
     * 提现回调
     * @param businessNo   业务流水号
     * @param txId          TXID
     * @param result       提币结果
     * @param amount       提币金额
     * @throws ServerBizException
     */
    @Override
    public void drawingsCallback(String businessNo, String txId, AccountDrawings.DrawingsResult result, BigDecimal amount) throws ServerBizException{
        AccountDrawings accountDrawings = accountDrawingsMapper.selectDrawingsByBusinessNo(businessNo);
        if (accountDrawings == null){
            throw new ServerBizException(ResponseCodeEnum.SYSTEM_BUSINESS_NO_ERROR);
        }
        if (result == AccountDrawings.DrawingsResult.Success){//提币成功
            accountDrawings.setResult(AccountDrawings.DrawingsResult.Success);
            accountDrawings.setTxId(txId);
            accountService.unfreezeBalance(accountDrawings.getUserId(),businessNo,false);//解冻被冻结的币,扣除提走的部份
        }else if (result == AccountDrawings.DrawingsResult.Faild){//
            accountService.unfreezeBalance(accountDrawings.getUserId(),businessNo,true);//解冻被冻结的币，但是因为提币失败，需要归还本次冻结
        }
        if (accountDrawingsMapper.updateDrawings(accountDrawings) == 0){//更新失败
            throw new ServerBizException(ResponseCodeEnum.SYSTEM_SERVER_ERROR);
        }
    }


    /**
     *
     * 调用钱包服务器执行提币操作
     */
    private void callWalletServerDrawings() {

    }
}
