package com.tianyi.vo;

/**
 * author:CDH
 * Date:2018/5/8
 */
public class ThirdPartyVo {

    private long userId;

    /**
     * 手机号
     */
    private String mobile;

    private long userThirdPartyId;

    private int  countryCode;

    public int getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(int countryCode) {
        this.countryCode = countryCode;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public long getUserThirdPartyId() {
        return userThirdPartyId;
    }

    public void setUserThirdPartyId(long userThirdPartyId) {
        this.userThirdPartyId = userThirdPartyId;
    }

    @Override
    public String toString() {
        return "ThirdPartyVo{" +
                "userId=" + userId +
                ", mobile='" + mobile + '\'' +
                ", userThirdPartyId=" + userThirdPartyId +
                '}';
    }
}
