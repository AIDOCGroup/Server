package com.tianyi.bo;

import com.tianyi.bo.base.BaseBo;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import java.io.Serializable;

/**
 * Created by 雪峰 on 2018/01/26.
 */



@Entity
@DynamicUpdate
@DynamicInsert
public class AppVersion extends BaseBo implements Serializable {
    private long srcId;
    private String mobileOs;
    private String version;
    private String downUrl;
    private String updateLog;
    private String targetSize;
    private int isMust;
    private String pgyerUrl;

    //0:个人，1：企业
    private Integer versionType;

    public Integer getVersionType() {
        return this.versionType;
    }

    public void setVersionType(Integer versionType) {
        this.versionType = versionType;
    }

//    @Column(columnDefinition = ("tinyint(1) default null comment '是否开启升级：1开启 0关闭'"))
//    private Boolean open_Update;
//
//    public Boolean getOpenUpdate() {
//        return this.open_Update;
//    }
//
//    public void setOpenUpdate(Boolean open_Update) {
//        this.open_Update = open_Update;
//    }


    public long getSrcId() {
        return srcId;
    }

    public void setSrcId(long srcId) {
        this.srcId = srcId;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDownUrl() {
        return downUrl;
    }

    public void setDownUrl(String downUrl) {
        this.downUrl = downUrl;
    }

    public String getUpdateLog() {
        return updateLog;
    }

    public void setUpdateLog(String updateLog) {
        this.updateLog = updateLog;
    }


    public int getIsMust() {
        return isMust;
    }

    public void setIsMust(int isMust) {
        this.isMust = isMust;
    }

    public String getMobileOs() {
        return mobileOs;
    }

    public void setMobileOs(String mobileOs) {
        this.mobileOs = mobileOs;
    }

    public String getTargetSize() {
        return targetSize;
    }

    public void setTargetSize(String targetSize) {
        this.targetSize = targetSize;
    }

    public String getPgyerUrl() {
        return pgyerUrl;
    }

    public void setPgyerUrl(String pgyerUrl) {
        this.pgyerUrl = pgyerUrl;
    }
}
