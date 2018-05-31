package com.tianyi.vo.redpackage;

import java.math.BigDecimal;
import java.util.Date;

public class LuckyMoneyDetail {
    private Long id;

    private Long parentId;

    private Integer type;

    private Long takeSendUserId;

    private String takeSendUserName;

    private Date createdTime;

    private BigDecimal amount;

    private String mobile;

    private String nickName;

    private String avatar;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Long getTakeSendUserId() {
        return takeSendUserId;
    }

    public void setTakeSendUserId(Long takeSendUserId) {
        this.takeSendUserId = takeSendUserId;
    }

    public String getTakeSendUserName() {
        return takeSendUserName;
    }

    public void setTakeSendUserName(String takeSendUserName) {
        this.takeSendUserName = takeSendUserName == null ? null : takeSendUserName.trim();
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}