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
}
