package com.tianyi.vo;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * @author CDH
 * @date 2018/5/3
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WithdrawMyWalletVO{

    /**
     * 用户eth地址
     */
    private String eth;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 奖励金额
     */
    private String rewardAmount;

    private String month;

    private String mark;
    /**
     * 状态值 odd_status
     */
    private String oddStatus;
    private String tranNo;

    private int err_code;
    private String err_msg;

    public String getOddStatus() {
        return oddStatus;
    }

    public void setOddStatus(String oddStatus) {
        this.oddStatus = oddStatus;
    }

    public String getMark() {
        return mark;
    }

    public String getTranNo() {
        return tranNo;
    }

    public void setTranNo(String tranNo) {
        this.tranNo = tranNo;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public int getErr_code() {
        return err_code;
    }

    public void setErr_code(int err_code) {
        this.err_code = err_code;
    }

    public String getErr_msg() {
        return err_msg;
    }

    public void setErr_msg(String err_msg) {
        this.err_msg = err_msg;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getEth() {
        return eth;
    }

    public void setEth(String eth) {
        this.eth = eth;
    }


    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getRewardAmount() {
        return rewardAmount;
    }

    public void setRewardAmount(String rewardAmount) {
        this.rewardAmount = rewardAmount;
    }

    @Override
    public String toString() {
        return "WithdrawMyWalletVO{" +
                "eth='" + eth + '\'' +
                ", mobile='" + mobile + '\'' +
                ", rewardAmount=" + rewardAmount +
                '}';
    }
}
