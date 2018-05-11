package com.tianyi.mapper;

import com.tianyi.bo.Account;
import com.tianyi.bo.AccountDetail;
import com.tianyi.dto.WithdrawMyWalletDto;
import com.tianyi.vo.SmsCodeVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * server
 *
 * @author GaoZhilai
 * @date 2018/4/10.
 */
@Mapper
@Component
public interface AccountDetailMapper {
    Long getInvitationReward(Long accountId);
    Account getAccountIdByUserId(Long userId);
    List<AccountDetail> getAccountDetail(Map param);
    long getAccountDetailCount(Map param);

    long updateMyWallet(WithdrawMyWalletDto withdrawMyWalletDto);

    long insertMyWallet(WithdrawMyWalletDto withdrawMyWalletDto);

    Long getDailyMyWallet(@Param("accountId")Long accountId ,@Param("transDir")Integer transDir,@Param("month")Date month);

    Long getMonthlyMyWallet(@Param("accountId")Long accountId ,@Param("transDir")Integer transDir,@Param("month")Date month);

    String getAidocSet(int type);

    SmsCodeVO getSmsCode(@Param("code")String code, @Param("mobile")String mobile);

}
