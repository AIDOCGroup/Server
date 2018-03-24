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
                .createNativeQuery("SELECT * FROM user_device where user_id=:user_id ORDER BY id DESC ", UserDevice.class)
                .setParameter("user_id", userId)
                .getResultList());
        return results != null ? results : new ArrayList<UserDevice>();
    }
}
