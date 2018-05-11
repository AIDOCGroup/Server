package com.tianyi.bo;

public class UserStepAidocResult {
    private int userId;
    private String nickName;
    private String mobile;
    private double aidoc;
    private int stepNumber;
    private String day;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public double getAidoc() {
        return aidoc;
    }

    public void setAidoc(double aidoc) {
        this.aidoc = aidoc;
    }

    public int getStepNumber() {
        return stepNumber;
    }

    public void setStepNumber(int stepNumber) {
        this.stepNumber = stepNumber;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    @Override
    public String toString() {
        return "UserStepAidocResult{" +
                "userId=" + userId +
                ", nickName='" + nickName + '\'' +
                ", mobile='" + mobile + '\'' +
                ", aidoc=" + aidoc +
                ", stepNumber=" + stepNumber +
                ", day='" + day + '\'' +
                '}';
    }
}