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

}
