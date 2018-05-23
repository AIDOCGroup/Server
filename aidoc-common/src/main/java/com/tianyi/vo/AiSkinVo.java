package com.tianyi.vo;

/**
 * author:CDH
 * Date:2018/5/11
 */
public class AiSkinVo {

    /**
     * 年龄检测
     */
    private String faceAge;
    /**
     * 面部疾病检测
     */
    private String faceDiagnose;
    /**
     * 面部斑点检测数量
     */
    private String spotNum;
    /**
     * 面部斑点检测图片路径
     */
    private String spotUrl;

    /**
     * 面部皱纹检测图片路径
     */
    private String wrinkleUrl;

    /**
     * 面部皱纹检测数量
     */
    private String wrinkleNum;

    /**
     * 脸部基本属性
     */
    private String faceAttr;
    /**
     * oily:油性
     dry：干性
     normal：中性
     mix：混合性
     */
    private String faceDry;

    /**
     * 得分
     */
    private Integer score;

    private long updatedTimestamp;

    private long userId;

    private int err_code;
    private String err_msg;

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

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public long getUpdatedTimestamp() {
        return updatedTimestamp;
    }

    public void setUpdatedTimestamp(long updatedTimestamp) {
        this.updatedTimestamp = updatedTimestamp;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getFaceAge() {
        return faceAge;
    }

    public void setFaceAge(String faceAge) {
        this.faceAge = faceAge;
    }

    public String getFaceDiagnose() {
        return faceDiagnose;
    }

    public void setFaceDiagnose(String faceDiagnose) {
        this.faceDiagnose = faceDiagnose;
    }

    public String getSpotNum() {
        return spotNum;
    }

    public void setSpotNum(String spotNum) {
        this.spotNum = spotNum;
    }

    public String getSpotUrl() {
        return spotUrl;
    }

    public void setSpotUrl(String spotUrl) {
        this.spotUrl = spotUrl;
    }

    public String getWrinkleUrl() {
        return wrinkleUrl;
    }

    public void setWrinkleUrl(String wrinkleUrl) {
        this.wrinkleUrl = wrinkleUrl;
    }

    public String getWrinkleNum() {
        return wrinkleNum;
    }

    public void setWrinkleNum(String wrinkleNum) {
        this.wrinkleNum = wrinkleNum;
    }

    public String getFaceAttr() {
        return faceAttr;
    }

    public void setFaceAttr(String faceAttr) {
        this.faceAttr = faceAttr;
    }

    public String getFaceDry() {
        return faceDry;
    }

    public void setFaceDry(String faceDry) {
        this.faceDry = faceDry;
    }
}
