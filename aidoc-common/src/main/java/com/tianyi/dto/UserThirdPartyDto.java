package com.tianyi.dto;

/**
 * author:CDH
 * Date:2018/5/7
 */
public class UserThirdPartyDto {

    private Long userId;
    private String unionId;
    private String screenName;
    private String city;
    private String accessToken;
    private String refreshToken;
    private String gender;
    private String province;
    private String openId;
    private String profileImageUrl;
    private String country;
    private String iconurl;
    private String name;
    private String uid;
    private String expiration;
    private String language;
    private String expiresIn;
    /**
     * 手机
     */
    private String mobile;
    /**
     * 验证码
     */
    private String code;
    /**
     * 国家编码
     */
    private String countryCode;
    private long updatedTimestamp;

    public long getUpdatedTimestamp() {
        return updatedTimestamp;
    }

    public void setUpdatedTimestamp(long updatedTimestamp) {
        this.updatedTimestamp = updatedTimestamp;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUnionId() {
        return unionId;
    }

    public void setUnionId(String unionId) {
        this.unionId = unionId;
    }

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getIconurl() {
        return iconurl;
    }

    public void setIconurl(String iconurl) {
        this.iconurl = iconurl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getExpiration() {
        return expiration;
    }

    public void setExpiration(String expiration) {
        this.expiration = expiration;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(String expiresIn) {
        this.expiresIn = expiresIn;
    }

    @Override
    public String toString() {
        return "UserThirdPartyDto{" +
                "userId=" + userId +
                ", unionId='" + unionId + '\'' +
                ", screenName='" + screenName + '\'' +
                ", city='" + city + '\'' +
                ", accessToken='" + accessToken + '\'' +
                ", refreshToken='" + refreshToken + '\'' +
                ", gender='" + gender + '\'' +
                ", province='" + province + '\'' +
                ", openId='" + openId + '\'' +
                ", profileImageUrl='" + profileImageUrl + '\'' +
                ", country='" + country + '\'' +
                ", iconurl='" + iconurl + '\'' +
                ", name='" + name + '\'' +
                ", uid='" + uid + '\'' +
                ", expiration='" + expiration + '\'' +
                ", language='" + language + '\'' +
                ", expiresIn='" + expiresIn + '\'' +
                ", mobile='" + mobile + '\'' +
                ", code='" + code + '\'' +
                ", countryCode='" + countryCode + '\'' +
                ", updatedTimestamp=" + updatedTimestamp +
                '}';
    }
}
