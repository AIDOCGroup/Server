package com.tianyi.vo;

/**
 * 用户收款地址
 * author:CDH
 * Date:2018/5/15
 */
public class AccountEthAddressVo {

    private long id;
    private String ethName;
    private String ethAddress;
    private int ethType;
    private Long userId;
    private long updatedTimestamp;
    private String accountAddress;
    /**
     * 0新增，1修改，2历史
     */
    private Integer modeType;

    public int getEthType() {
        return ethType;
    }

    public void setEthType(int ethType) {
        this.ethType = ethType;
    }

    public String getAccountAddress() {
        return accountAddress;
    }

    public void setAccountAddress(String accountAddress) {
        this.accountAddress = accountAddress;
    }

    public Integer getModeType() {
        return modeType;
    }

    public void setModeType(Integer modeType) {
        this.modeType = modeType;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public long getUpdatedTimestamp() {
        return updatedTimestamp;
    }

    public void setUpdatedTimestamp(long updatedTimestamp) {
        this.updatedTimestamp = updatedTimestamp;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEthName() {
        return ethName;
    }

    public void setEthName(String ethName) {
        this.ethName = ethName;
    }

    public String getEthAddress() {
        return ethAddress;
    }

    public void setEthAddress(String ethAddress) {
        this.ethAddress = ethAddress;
    }

}
