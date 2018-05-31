package com.tianyi.vo.redpackage;

import java.math.BigDecimal;
import java.util.Date;

public class LuckyMoney {

    public enum RedPacketType {
        RECEIVE, RANDOM, AVG
    }

    private Long id;

    private Long userId;

    private String userName;

    private Date createdTime;

    private Date updatedTime;

    private Date effectedTime;

    private Date loseEffectedTime;

    private BigDecimal totalAmount;

    private Integer type;

    private Long totalNum;

    private String mobile;

    private Long version;

    private String comments;

    private BigDecimal remainAmount;

    private Long remainNum;


    private String nickName;

    private String  avatar;

    private String phone;

    private  BigDecimal amount;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public BigDecimal getRemainAmount() {
        return remainAmount;
    }

    public void setRemainAmount(BigDecimal remainAmount) {
        this.remainAmount = remainAmount;
    }

    public Long getRemainNum() {
        return remainNum;
    }

    public void setRemainNum(Long remainNum) {
        this.remainNum = remainNum;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Long getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(Long totalNum) {
        this.totalNum = totalNum;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments == null ? null : comments.trim();
    }

    public Date getEffectedTime() {
        return effectedTime;
    }

    public void setEffectedTime(Date effectedTime) {
        this.effectedTime = effectedTime;
    }

    public Date getLoseEffectedTime() {
        return loseEffectedTime;
    }

    public void setLoseEffectedTime(Date loseEffectedTime) {
        this.loseEffectedTime = loseEffectedTime;
    }

    @Override
    public String toString() {
        return "LuckyMoney{" +
                "id=" + id +
                ", userId=" + userId +
                ", userName='" + userName + '\'' +
                ", createdTime=" + createdTime +
                ", updatedTime=" + updatedTime +
                ", effectedTime=" + effectedTime +
                ", loseEffectedTime=" + loseEffectedTime +
                ", totalAmount=" + totalAmount +
                ", type=" + type +
                ", totalNum=" + totalNum +
                ", mobile='" + mobile + '\'' +
                ", version=" + version +
                ", comments='" + comments + '\'' +
                ", remainAmount=" + remainAmount +
                ", remainNum=" + remainNum +
                ", nickName='" + nickName + '\'' +
                ", avatar='" + avatar + '\'' +
                ", phone='" + phone + '\'' +
                ", amount=" + amount +
                '}';
    }

}