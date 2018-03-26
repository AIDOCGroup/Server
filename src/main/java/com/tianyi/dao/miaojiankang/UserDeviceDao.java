package com.tianyi.dao.miaojiankang;

import com.tianyi.bo.User;
import com.tianyi.bo.enums.UserType;
import com.tianyi.bo.miaojiankang.UserDevice;
import com.tianyi.bo.miaojiankang.UserDeviceData;
import com.tianyi.dao.base.BaseDao;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anhui on 2018/3/22.
 */
@Repository
public class UserDeviceDao extends BaseDao<UserDevice> {


    public List<UserDevice> getUserDevice(long userId){

        List<UserDevice> results = execute(session -> (List<UserDevice>) session
                .createNativeQuery("SELECT * FROM user_device where user_id=:user_id and status in (0,1) and tid in (4,7) ORDER BY id DESC ", UserDevice.class)
                .setParameter("user_id", userId)
                .getResultList());
        return results != null ? results : new ArrayList<UserDevice>();
    }


    public UserDevice getUserDeviceByDeviceId(String deviceId,long userId){
        return execute(session -> session
                .createNativeQuery("SELECT * FROM user_device where user_id=:user_id and status in (0,1) and device_id=:device_id ORDER BY id DESC", UserDevice.class)
                .setParameter("user_id", userId)
                .setParameter("device_id",deviceId)
                .uniqueResult());
    }
}
