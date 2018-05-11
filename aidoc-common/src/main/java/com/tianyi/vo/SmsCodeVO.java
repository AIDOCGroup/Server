package com.tianyi.vo;

/**
 * @author CDH
 * @date 2018/5/4
 */
public class SmsCodeVO {

    private String mobile;
    private String code;
    private Long updatedTimestamp;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getUpdatedTimestamp() {
        return updatedTimestamp;
    }

    public void setUpdatedTimestamp(Long updatedTimestamp) {
        this.updatedTimestamp = updatedTimestamp;
    }

    @Override
    public String toString() {
        return "SmsCodeVO{" +
                "mobile='" + mobile + '\'' +
                ", code='" + code + '\'' +
                ", updated_timestamp=" + updatedTimestamp +
                '}';
    }
}
