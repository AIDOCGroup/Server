package com.tianyi.service;

import com.tianyi.bo.AccountDetail;
import com.tianyi.bo.WalletDetailEle;
import com.tianyi.bo.enums.AccountChannelEnum;
import com.tianyi.dto.WithdrawMyWalletDto;
import com.tianyi.mapper.AccountDetailMapper;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.tianyi.util.Tools;
import com.tianyi.vo.SmsCodeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * server
 *
 * @author GaoZhilai
 * @date 2018/4/17.
 */
@Service
public class AccountDetailService {
  @Autowired
  AccountDetailMapper accountDetailMapper;

  /**
   * 获取账户交易详情
   * @param param 分页参数,时间参数
   * @return 包含钱包首页列表字段
   */
  public List<WalletDetailEle> getAccountDetail(Map param) {
    List<WalletDetailEle> result = new ArrayList<>();
    List<AccountDetail> lists = accountDetailMapper.getAccountDetail(param);
    for (AccountDetail ele :
            lists) {
      WalletDetailEle temp = new WalletDetailEle();
      AccountChannelEnum enumEle;
      try {
        enumEle = AccountChannelEnum.valueOf((ele.getChannel()+"").toUpperCase());
      } catch (Exception e) {
        enumEle = null;
      }
      temp.setTime(ele.getCreatedOn().getTime());
      temp.setTransDir(ele.getTransDir());
      if (ele.getTransDir() == null || ele.getTransDir() == 0){
        temp.setMark("+");
      }else {
        temp.setMark("-");
      }
      temp.setMoney(Tools.getDecimalThree(Double.parseDouble(ele.getRewardAmount()+"")/1000));
      if (ele != null) {
        temp.setTypeCode(enumEle.getCode());
        temp.setDescription(enumEle.getDescription());
      }else {
        temp.setTypeCode(AccountChannelEnum.UNKNOWN.getCode());
        temp.setDescription(AccountChannelEnum.UNKNOWN.getDescription());
      }
      /*if (enumEle.getCode() == 1) {
        temp.setDescription("步数奖励");
        temp.setMoney(Double.parseDouble(ele.getRewardAmount() + "") / 1000);
      } else if (enumEle.getCode() == 2) {
        temp.setDescription("邀请好友奖励");
        temp.setMoney(Double.parseDouble(ele.getRewardAmount() + "") / 1000);
      } else if (enumEle.getCode() == 3) {
        temp.setDescription("新注册奖励");
        temp.setMoney(Double.parseDouble(ele.getRewardAmount() + "") / 1000);
      } else {
        temp.setDescription("未知类型");
        temp.setMoney(Double.parseDouble(ele.getRewardAmount() + "") / 1000);
      }*/
      result.add(temp);
    }
    return result;
  }

  public long getAccountDetailCount(Map param) {
    return accountDetailMapper.getAccountDetailCount(param);
  }



  @Transactional(readOnly = false,value="transactionManager")
  public WithdrawMyWalletDto withdrawMyWallet(WithdrawMyWalletDto withdrawMyWalletDto) {
    accountDetailMapper.updateMyWallet(withdrawMyWalletDto);
    accountDetailMapper.insertMyWallet(withdrawMyWalletDto);
    return withdrawMyWalletDto;
  }

  /**
   * 查询每日提币
  * @Author:CDH
  * @Date: 2018/5/3
  * @params:  month
  * @return:  long
  */
  public boolean getDailyMyWallet(Long accountId ,Integer transDir ,Date month ,Long rewardAmount) {
    Long dailyMyWallet = accountDetailMapper.getDailyMyWallet(accountId, transDir, month );
    if (dailyMyWallet == null ){
      return false;
    }
    String aidocSet = accountDetailMapper.getAidocSet(6);
    long l = Long.valueOf(aidocSet) * 1000;
    return l - dailyMyWallet >= rewardAmount ? false : true;
  }

  /**
   * 查询每月提币
  * @Author:CDH
  * @Date: 2018/5/3
  * @params:  month
  * @return:  long
  */
  public boolean getMonthlyMyWallet(Long accountId ,Integer transDir ,Date month ,Long rewardAmount) {
    Long monthlyMyWallet = accountDetailMapper.getMonthlyMyWallet(accountId, transDir, month );
    if (monthlyMyWallet == null ){
      return false;
    }
    String aidocSet = accountDetailMapper.getAidocSet(7);
    long l = Long.valueOf(aidocSet) * 1000;
    return l - monthlyMyWallet>= rewardAmount ? false : true;
  }

  public boolean getSmsCode(String code, String mobile ,Date month) {
    SmsCodeVO smsCode = accountDetailMapper.getSmsCode(code, mobile);
    if (smsCode == null){
      return true;
    }
    double v = (double) (smsCode.getUpdatedTimestamp() - month.getTime()) / 1000 / 60;
    if (v <= 6 && v >= -1 ){
      return false;
    }
      return true;
  }
}
