package com.tianyi.dto;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author CDH
 * @date 2018/5/3
 */
public class WithdrawMyWalletDto {


    private Long id;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SS")
    private Date month;

    /**
     * 交易方向-0:收,1:付
     */
    private Integer transDir;

    private Long userId;

    /**
     * 奖励金额
     */
    private Long rewardAmount;
    /**
     * 账户余额
     */
    private Long balance;
    /**
     * 渠道
     */
    private String channel;

    /**
     * 用户eth地址
     */
    private String eth;

    /**
     * 交易流水号
     */
    private String tranNo;

    private long updatedTimestamp;

    private Long accountId;

    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Date getMonth() {
        return month;
    }

    public void setMonth(Date month) {
        this.month = month;
    }

    public String getTranNo() {
        return tranNo;
    }

    public void setTranNo(String tranNo) {
        this.tranNo = tranNo;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getTransDir() {
        return transDir;
    }

    public void setTransDir(Integer transDir) {
        this.transDir = transDir;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }


    public Long getRewardAmount() {
        return rewardAmount;
    }

    public void setRewardAmount(Long rewardAmount) {
        this.rewardAmount = rewardAmount;
    }

    public Long getBalance() {
        return balance;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getEth() {
        return eth;
    }

    public void setEth(String eth) {
        this.eth = eth;
    }

    public long getUpdatedTimestamp() {
        return updatedTimestamp;
    }

    public void setUpdatedTimestamp(long updatedTimestamp) {
        this.updatedTimestamp = updatedTimestamp;
    }

    @Override
    public String toString() {
        return "WithdrawMyWalletDto{" +
                "transDir=" + transDir +
                ", userId=" + userId +
                ", rewardAmount=" + rewardAmount +
                ", balance=" + balance +
                ", channel='" + channel + '\'' +
                ", eth='" + eth + '\'' +
                '}';
    }
}
