package com.tianyi.bo;

import com.tianyi.bo.enums.ChannelEnum;
import com.tianyi.bo.enums.StatusEnum;

/**
 * Created by gaozhilai on 2018/3/27.
 */
public class InvitationResult {
    private String registTime;
    private long ordernumber;
    private long userId;
    private String nickName;
    private String invitationCode;
    private Integer invitationNum;
    private ChannelEnum invitationChannelTemp;
    private StatusEnum invitationStatusTemp;
    private String invitationChannel;
    private String invitationStatus;

    public ChannelEnum getInvitationChannelTemp() {
        return invitationChannelTemp;
    }

    public void setInvitationChannelTemp(ChannelEnum invitationChannelTemp) {
        this.invitationChannelTemp = invitationChannelTemp;
    }

    public StatusEnum getInvitationStatusTemp() {
        return invitationStatusTemp;
    }

    public void setInvitationStatusTemp(StatusEnum invitationStatusTemp) {
        this.invitationStatusTemp = invitationStatusTemp;
    }

    public void setInvitationChannel(String invitationChannel) {
        this.invitationChannel = invitationChannel;
    }

    public void setInvitationStatus(String invitationStatus) {
        this.invitationStatus = invitationStatus;
    }

    public String getInvitationChannel() {
        return invitationChannel;
    }

    public String getInvitationStatus() {
        return invitationStatus;
    }

    public String getRegistTime() {
        return registTime;
    }

    public void setRegistTime(String registTime) {
        this.registTime = registTime;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getInvitationCode() {
        return invitationCode;
    }

    public void setInvitationCode(String invitationCode) {
        this.invitationCode = invitationCode;
    }

    public Integer getInvitationNum() {
        return invitationNum;
    }

    public void setInvitationNum(Integer invitationNum) {
        this.invitationNum = invitationNum;
    }

//    public String getInvitationChannel() {
//        return invitationChannel;
//    }
//
//    public void setInvitationChannel(String invitationChannel) {
//        this.invitationChannel = invitationChannel;
//    }


    /*public ChannelEnum getInvitationChannel() {
        return invitationChannel;
    }

    public void setInvitationChannel(ChannelEnum invitationChannel) {
        this.invitationChannel = invitationChannel;
    }*/

    /*public String getInvitationStatus() {
        return invitationStatus;
    }

    public void setInvitationStatus(String invitationStatus) {
        this.invitationStatus = invitationStatus;
    }*/

/*    public StatusEnum getInvitationStatus() {
        return invitationStatus;
    }

    public void setInvitationStatus(StatusEnum invitationStatus) {
        this.invitationStatus = invitationStatus;
    }*/

    public long getOrdernumber() {
        return ordernumber;
    }

    public void setOrdernumber(long ordernumber) {
        this.ordernumber = ordernumber;
    }
}
