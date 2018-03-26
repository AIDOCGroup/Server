package com.tianyi.bo.miaojiankang;

import com.tianyi.bo.base.BaseBo;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.io.Serializable;

/**
 * Created by anhui on 2018/3/22.
 */
@Entity
@DynamicUpdate
@DynamicInsert
public class UserDevice extends BaseBo implements Serializable {

    @Column(columnDefinition = ("varchar(100) default null comment '设备id'"))
    private String deviceId;

    public String getDeviceId() {
        return this.deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    @Column(columnDefinition = ("varchar(255) default null comment '设备名称'"))
    private String deviceName;

    public String getDeviceName() {
        return this.deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    @Column(columnDefinition = ("bigint(20) default null comment '用户id'"))
    private Long userId;

    public Long getUserId() {
        return this.userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }


    @Column(columnDefinition = ("int default null comment '选中状态: 1选中 0未选中 2删除'"))
    private int status;

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Column(columnDefinition = ("varchar(100) default null comment '设备uuid'"))
    private String uuid;

    public String getUuid() {
        return this.uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @Column(columnDefinition = ("varchar(100) default null comment '设备编码'"))
    private String deviceSn;

    public String getDeviceSn() {
        return this.deviceSn;
    }

    public void setDeviceSn(String deviceSn) {
        this.deviceSn = deviceSn;
    }

    @Column(columnDefinition = ("varchar(200) default null comment '设备URL'"))
    private String desUrl;

    public String getDesUrl() {
        return this.desUrl;
    }

    public void setDesUrl(String desUrl) {
        this.desUrl = desUrl;
    }

    @Column(columnDefinition = ("varchar(100) default null comment '链接类型'"))
    private String linkType;

    public String getLinkType() {
        return this.linkType;
    }

    public void setLinkType(String linkType) {
        this.linkType = linkType;
    }

    @Column(columnDefinition = ("varchar(100) default null comment '设备描述'"))
    private String deviceDes;

    public String getDeviceDes() {
        return this.deviceDes;
    }

    public void setDeviceDes(String deviceDes) {
        this.deviceDes = deviceDes;
    }

    @Column(columnDefinition = ("varchar(100) default null comment '绑定数量'"))
    private String bindNum;

    public String getBindNum() {
        return this.bindNum;
    }

    public void setBindNum(String bindNum) {
        this.bindNum = bindNum;
    }

    @Column(columnDefinition = ("varchar(100) default null comment '绑定状态'"))
    private String bindStatus;

    public String getBindStatus() {
        return this.bindStatus;
    }

    public void setBindStatus(String bindStatus) {
        this.bindStatus = bindStatus;
    }

    @Column(columnDefinition = ("varchar(100) default null comment 'logo'"))
    private String logo;

    public String getLogo() {
        return this.logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }


    @Column(columnDefinition = ("varchar(100) default null comment '设备类型ID'"))
    private String tid;

    public String getTid() {
        return this.tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    @Column(columnDefinition = ("varchar(100) default null comment '设备类型名称'"))
    private String typeName;

    public String getTypeName() {
        return this.typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    @Column(columnDefinition = ("varchar(100) default null comment '设备类型描述'"))
    private String typeDesc;

    public String getTypeDesc() {
        return this.typeDesc;
    }

    public void setTypeDesc(String typeDesc) {
        this.typeDesc = typeDesc;
    }

    @Column(columnDefinition = ("varchar(200) default null comment '设备类型logo'"))
    private String typeLogo;

    public String getTypeLogo() {
        return this.typeLogo;
    }

    public void setTypeLogo(String typeLogo) {
        this.typeLogo = typeLogo;
    }

}
