package com.tianyi.service.miaojiankang;

import com.tianyi.bo.miaojiankang.UserDevice;
import com.tianyi.bo.miaojiankang.UserDeviceData;
import com.tianyi.dao.AccountDao;
import com.tianyi.dao.miaojiankang.UserDeviceDao;
import com.tianyi.dao.miaojiankang.UserDeviceDataDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by anhui on 2018/3/22.
 */

@Service("userDeviceDataService")
public class UserDeviceDataService {

    @Resource
    UserDeviceDao userDeviceDao;

    @Resource
    UserDeviceDataDao userDeviceDataDao;

    public UserDeviceData addUserDeviceData(UserDeviceData deviceData){

        long id =(Long)userDeviceDataDao.add(deviceData);
        deviceData.setId(id);
        return deviceData;
    };

    public List<UserDeviceData> getUserDeviceDataHistory(long userId,int page,int pageSize){

        return userDeviceDataDao.getUserDeviceDataHistory(userId,page,pageSize);
    }

    public List<UserDeviceData> getUserDeviceKralHistory(long userId,int page,int pageSize){

        return userDeviceDataDao.getUserDeviceKcalHistory(userId,page,pageSize);
    }

    public List<UserDeviceData> getUserDeviceHeartRateHistory(long userId,int page,int pageSize){
        return userDeviceDataDao.getUserDeviceHeartRateHistory(userId,page,pageSize);
    }

    public List<UserDeviceData> getUserDeviceBloodLowHistory(long userId,int page,int pageSize){
        return userDeviceDataDao.getUserDeviceBloodLowHistory(userId,page,pageSize);
    }

    public List<UserDeviceData> getUserDeviceBloodHighHistory(long userId,int page,int pageSize){
        return userDeviceDataDao.getUserDeviceBloodHighHistory(userId,page,pageSize);
    }

    public  List<UserDeviceData> getUserDeviceWeightHistory(long userId,int page,int pageSize){
        return userDeviceDataDao.getUserDeviceWeightHistory(userId,page,pageSize);
    }



}
