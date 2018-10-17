package com.tianyi.vo;

import java.math.BigDecimal;
import java.util.Date;

/**
 * server
 * APP邀请明细用户列表VO
 * @author GaoZhilai
 * @date 2018.10.16.
 */
public class AppDividendsDetailUserVO {
    private String nickname;
    private Date registDate;
    private BigDecimal reward;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Date getRegistDate() {
        return registDate;
    }

    public void setRegistDate(Date registDate) {
        this.registDate = registDate;
    }

    public BigDecimal getReward() {
        return reward;
    }

    public void setReward(BigDecimal reward) {
        this.reward = reward;
    }
}