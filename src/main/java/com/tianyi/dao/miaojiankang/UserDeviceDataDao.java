package com.tianyi.dao.miaojiankang;

import com.tianyi.bo.Account;
import com.tianyi.bo.UserDataResult;
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
public class UserDeviceDataDao extends BaseDao<UserDeviceData> {

    public List<UserDeviceData> getUserDeviceDataHistory(Long userId,int page,int pageSize){

        StringBuffer sql = new StringBuffer(
                        "select " +
                        " * " +
                        "from user_device_data " +
                        "where user_id=:user_id " +
                        "order by created_on desc "
        );


        List<UserDeviceData>  results = execute(session ->session
                .createNativeQuery(sql.toString(),UserDeviceData.class)
                .setParameter("user_id",userId)
                .setFirstResult((page - 1) * pageSize)
                .setMaxResults(pageSize)
                .getResultList()
        );

        return results != null ? results : new ArrayList<UserDeviceData>();
    }

}
