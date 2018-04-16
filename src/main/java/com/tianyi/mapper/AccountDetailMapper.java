package com.tianyi.mapper;

import com.tianyi.bo.Account;
import com.tianyi.bo.AccountDetail;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
 * server
 *
 * @author GaoZhilai
 * @date 2018/4/10.
 */
@Mapper
@Component
public interface AccountDetailMapper {
    public abstract Long getInvitationReward(Long accountId);
    public abstract Account getAccountIdByUserId(Long userId);
}
