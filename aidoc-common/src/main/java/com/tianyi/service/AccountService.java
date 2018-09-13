package me.aidoc.appserver.service.user;

import me.aidoc.appserver.common.PageData;
import me.aidoc.appserver.common.exception.ServerBizException;
import me.aidoc.appserver.po.user.Account;
import me.aidoc.appserver.po.user.AccountFreeze;
import java.math.BigDecimal;

/**
 * @author vliu
 * @create 2018-08-16 15:57
 **/
public interface AccountService {

    /**
     *  操作结果:成功
     */
   int RESULT_SUCCESS = 0x01;

    /**
     * 操作结果:失败，用户ID不能为空
     */
    int RESULT_USERID_IS_NOT_NULL = 0x02;

    /**
     * 操作结果:失败，用户已经存在
     */
    int RESULT_ACCOUNT_HAS_EXIST = 0x03;

    /**
     * 操作结果:失败，用户不存在
     */
    int RESULT_ACCOUNT_NOT_EXIST = 0x04;

    /**
     * 操作结果:失败，用户状态异常
     */
    int RESULT_ACCOUNT_ABNORMAL = 0x05;

    /**
     * 操作结果:失败，余额不足
     */
    int RESULT_BALANCE_INSUFFICIENT = 0x06;
    /**
     * 操作结果:失败，businessNo错误
     */
    int RESULT_BUSINESS_NO_ERROR = 0x07;

    /**
     * 操作失败，没有任何更新
     */
    int RESULT_NO_UPDATE = 0xEF;

    /**
     * 操纵结果:失败，数据库错误
     */
    int RESULT_DATABASE_ERROR = 0xFF;

    /**
     * 账户状态:正常
     */
    int STATUS_NORMAL = 0;

    /**
     * 账户状态:冻结
     */
    int STATUS_FREEZE = 2;

    /**
     * 创建账户，返回值说明:
     *      1.成功
     *      2.失败，账户已存在
     *      3.失败，余额不能为负数
     *      4.失败，eth地址不合法
     *      110.数据库连接错误
     *
     * @param createAccountInfoDTO
     *
     * @return
     */
    int createAccount(CreateAccountInfoDTO createAccountInfoDTO);

    /**
     * 更新用户的账户信息，返回值说明:
     *     1.更新成功
     *     2.失败，userId不能为空
     *     3.失败，userId不存在
     *     4.失败，不支持的status
     *     110.数据库连接错误
     *
     * @param updateAccountInfoDTO
     * @return
     */
    int updateAccount(UpdateAccountInfoDTO updateAccountInfoDTO);

    /**
     * 增加余额
     * @return
     */
    int increaseBalance(AccountInOrOutDTO accountInOrOutDTO);

    /**
     * 减少余额
     * @return
     */
    int reduceBalance(AccountInOrOutDTO accountInOrOutDTO);

    /**
     * 冻结余额
     * @return
     */
    int freezeBalance(AccountFreezeDTO accountFreezeDTO);

    /**
     * 更新用户的冻结信息
     * @param updateAccountFeezeDTO
     *
     * @return
     */
    int updateFreeze(UpdateAccountFeezeDTO updateAccountFeezeDTO);


    /**
     *
     * @param userId         需要解冻用户的Id
     * @param businessNo     业务号
     * @param needGiveBack   是否需要归还冻结金额，慎重，根据具体业务自己确定
     * @return
     */
    int unfreezeBalance(Long userId,String businessNo,boolean needGiveBack);

    /**
     * 获取用户的余额
     * @param userId
     * @return
     */
    BigDecimal getAccountBalance(Long userId);

    /**
     *
     * @param userId
     * @return
     */
    Account getAccountByUserId(Long userId);

    /**
     * 根据eth地址查询账户
     * @param ethAddress
     * @return
     */
    Account getAccountByEthAddress(String ethAddress);

    /**
     * 校验支付密码是否正确
     * @param userId         被校验者的userId
     * @param payPassword    支付密码
     * @return
     */
    boolean checkPayPassword(Long userId,String payPassword);

    /**
     * 查询用户的收支明细
     * @param userId
     * @param page
     * @param size
     * @return
     */
    PageData getAccountDetailByUserId(Long userId, Integer page, Integer size);

    /**
     * 根据业务号查询冻结相关的信息
     * @param businessNo
     * @return
     */
    AccountFreeze getAccountFreeze(String businessNo);

    /**
     * 获取用户的冻结列表
     * @param userId
     * @return
     */
    PageData getAccountFreezeList(Long userId,Integer page,Integer size);

    /**
     * 给指定用户生成eth地址
     * @param userId
     */
    void createEthAddress(Long userId) throws ServerBizException;
}
