package com.tianyi.bo;

import com.tianyi.bo.base.BaseBo;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import java.io.Serializable;

/**
 * Created by gaozhilai on 2018/3/27.
 */
@Entity
@DynamicUpdate
@DynamicInsert
public class Invitation extends BaseBo implements Serializable {
    private Long userId;
    private String invitationCode;
//    private int invitationNum;
    private String invitationReward;
    //1有效,0无效
    private int status;

    /**
     * 1 微信
     * 2 微博
     * 3 电报群
     * 4 内测群
     */
    private int channel;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getInvitationCode() {
        return invitationCode;
    }

    public void setInvitationCode(String invitationCode) {
        this.invitationCode = invitationCode;
    }

/*    public int getInvitationNum() {
        return invitationNum;
    }

    public void setInvitationNum(int invitationNum) {
        this.invitationNum = invitationNum;
    }*/

    public String getInvitationReward() {
        return invitationReward;
    }

    public void setInvitationReward(String invitationReward) {
        this.invitationReward = invitationReward;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getChannel() {
        return channel;
    }

    public void setChannel(int channel) {
        this.channel = channel;
    }
}
