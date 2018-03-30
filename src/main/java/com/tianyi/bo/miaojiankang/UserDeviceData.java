package com.tianyi.bo.miaojiankang;

import com.tianyi.bo.base.BaseBo;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by anhui on 2018/3/22.
 */
@Entity
@DynamicUpdate
@DynamicInsert
public class UserDeviceData  extends BaseBo implements Serializable {

    @Column(columnDefinition = ("varchar(100) default null comment '设备种类'"))
    private String deviceCategory;

    public String getDeviceCategory() {
        return this.deviceCategory;
    }

    public void setDeviceCategory(String deviceCategory) {
        this.deviceCategory = deviceCategory;
    }


    @Column(columnDefinition = ("varchar(255) default null comment '设备名称'"))
    private String deviceName;

    public String getDeviceName() {
        return this.deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    @Column(columnDefinition = ("varchar(255) default null comment '设备名称英文'"))
    private String deviceNameEn;

    public String getDeviceNameEn() {
        return this.deviceNameEn;
    }

    public void setDeviceNameEn(String deviceNameEn) {
        this.deviceNameEn = deviceNameEn;
    }

    @Column(columnDefinition = ("varchar(20) default null comment '计量单位'"))
    private String unit;

    public String getUnit() {
        return this.unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    @Column(columnDefinition = ("varchar(10) default null comment '指标数据'"))
    private String data;

    public String getData() {
        return this.data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Column(columnDefinition = ("varchar(20) default null comment '指标类型'"))
    private String dataType;

    public String getDataType() {
        return this.dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    @Column(columnDefinition = ("bigint(20) default null comment '用户id'"))
    private Long userId;

    public Long getUserId() {
        return this.userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Date getCreatedOnTxt() {
        return super.getCreatedOn();
    }

    public String deviceId;
    public String getDeviceId() {
        return deviceId;
    }
    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
}
