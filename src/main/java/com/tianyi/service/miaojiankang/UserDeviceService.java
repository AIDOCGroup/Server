package com.tianyi.service.miaojiankang;

import com.tianyi.bo.miaojiankang.UserDevice;
import com.tianyi.bo.miaojiankang.UserDeviceData;
import com.tianyi.dao.miaojiankang.UserDeviceDao;
import com.tianyi.dao.miaojiankang.UserDeviceDataDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by anhui on 2018/3/22.
 */

@Service("userDeviceService")
public class UserDeviceService {

    @Resource
    UserDeviceDao userDeviceDao;

    public UserDevice addUserDevice(UserDevice deviceData){

        long id =(Long)userDeviceDao.add(deviceData);
        deviceData.setId(id);
        return deviceData;
    };

    public List<UserDevice> getUserDevice(long userId){

        return userDeviceDao.getUserDevice(userId);
    }
    public UserDevice updateDevice(UserDevice userDevice){

        userDeviceDao.update(userDevice);
        return userDevice;
    }

    public UserDevice getUserDeviceByDeviceId(String deviceUuid,long userId){

        return userDeviceDao.getUserDeviceByDeviceId(deviceUuid,userId);
    }







}
