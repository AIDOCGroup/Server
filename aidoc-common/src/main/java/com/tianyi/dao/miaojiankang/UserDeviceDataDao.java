package com.tianyi.dao.miaojiankang;

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
                      "select  " +
                              "id, " +
                              "max(created_on) created_on, " +
                              "max(updated_on) updated_on, " +
                              "max(updated_timestamp) updated_timestamp, " +
                              "max(data) data, " +
                              "data_type, " +
                              "max(device_category) device_category, " +
                              "max(device_name) device_name, " +
                              "max(device_name_en) device_name_en, " +
                              "max(unit) unit, " +
                              "user_id ,device_id " +
                              "from user_device_data  " +
                              "where user_id=:user_id and data_type='步数' " +
                              " and cast(data as SIGNED )>0 " +
                              "group by user_id,data_type, left(created_on,10) " +
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

    public List<UserDeviceData> getUserDeviceKcalHistory(Long userId,int page,int pageSize){
        StringBuffer sql = new StringBuffer(
                "select  " +
                        "id, " +
                        "max(created_on) created_on, " +
                        "max(updated_on) updated_on, " +
                        "max(updated_timestamp) updated_timestamp, " +
                        "max(data) data, " +
                        "data_type, " +
                        "max(device_category) device_category, " +
                        "max(device_name) device_name, " +
                        "max(device_name_en) device_name_en, " +
                        "max(unit) unit, " +
                        "user_id,device_id " +
                        "from user_device_data  " +
                        "where user_id=:user_id and data_type='卡路里' " +
                        " and cast(data as SIGNED )>0 " +
                        "group by user_id,data_type, left(created_on,10) " +
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

    public List<UserDeviceData> getUserDeviceHeartRateHistory(Long userId,int page,int pageSize){
        StringBuffer sql = new StringBuffer(
                "select  id, " +
                        "created_on created_on," +
                        "updated_on,  " +
                        "updated_timestamp, " +
                        "data, " +
                        "data_type, " +
                        "device_category,  " +
                        "device_name, " +
                        "device_name_en, " +
                        "unit, " +
                        "user_id,device_id " +
                        "from user_device_data  " +
                        "where " +
                        "     left(created_on,10)>= (select if(exists(select  t.created_on from ( select distinct left(created_on,10) created_on from user_device_data where data_type='心率' order by created_on desc limit :offset,:limit) t order by t.created_on asc limit 1),(select  t.created_on from ( select distinct left(created_on,10) created_on from user_device_data where data_type='心率' order by created_on desc limit :offset,:limit) t order by t.created_on asc limit 1),left(DATE_ADD(NOW(), INTERVAL 1 DAY),10)) created_on) " +
                        " and left(created_on,10)<= (select if(exists(select  t.created_on from ( select distinct left(created_on,10) created_on from user_device_data where data_type='心率' order by created_on desc limit :offset,:limit) t order by t.created_on desc limit 1),(select  t.created_on from ( select distinct left(created_on,10) created_on from user_device_data where data_type='心率' order by created_on desc limit :offset,:limit) t order by t.created_on desc limit 1),left(DATE_ADD(NOW(), INTERVAL -1 DAY),10)) created_on) " +
                        " and user_id=:user_id and data_type='心率' and cast(data as SIGNED )>0 order by created_on desc"
        );


        List<UserDeviceData>  results = execute(session ->session
                .createNativeQuery(sql.toString(),UserDeviceData.class)
                .setParameter("user_id",userId)
                .setParameter("offset",(page-1)*pageSize)
                .setParameter("limit",pageSize)
                .getResultList()
        );

        return results != null ? results : new ArrayList<UserDeviceData>();
    }

    public List<UserDeviceData> getUserDeviceBloodLowHistory(Long userId,int page,int pageSize){
        StringBuffer sql = new StringBuffer(
                "select  id, " +
                        "created_on created_on," +
                        "updated_on,  " +
                        "updated_timestamp, " +
                        "data, " +
                        "data_type, " +
                        "device_category,  " +
                        "device_name, " +
                        "device_name_en, " +
                        "unit, " +
                        "user_id,device_id " +
                        "from user_device_data  " +
                        "where " +
                        "     left(created_on,10)>= (select if(exists(select  t.created_on from ( select distinct left(created_on,10) created_on from user_device_data where data_type='低血压值' order by created_on desc limit :offset,:limit) t order by t.created_on asc limit 1),(select  t.created_on from ( select distinct left(created_on,10) created_on from user_device_data where data_type='低血压值' order by created_on desc limit :offset,:limit) t order by t.created_on asc limit 1),left(DATE_ADD(NOW(), INTERVAL 1 DAY),10)) created_on) " +
                        " and left(created_on,10)<= (select if(exists(select  t.created_on from ( select distinct left(created_on,10) created_on from user_device_data where data_type='低血压值' order by created_on desc limit :offset,:limit) t order by t.created_on desc limit 1),(select  t.created_on from ( select distinct left(created_on,10) created_on from user_device_data where data_type='低血压值' order by created_on desc limit :offset,:limit) t order by t.created_on desc limit 1),left(DATE_ADD(NOW(), INTERVAL -1 DAY),10)) created_on) " +
                        " and user_id=:user_id and data_type='低血压值' and cast(data as SIGNED )>0 order by created_on desc"
        );


        List<UserDeviceData>  results = execute(session ->session
                .createNativeQuery(sql.toString(),UserDeviceData.class)
                .setParameter("user_id",userId)
                .setParameter("offset",(page-1)*pageSize)
                .setParameter("limit",pageSize)
                .getResultList()
        );

        return results != null ? results : new ArrayList<UserDeviceData>();
    }

    public List<UserDeviceData> getUserDeviceBloodHighHistory(Long userId,int page,int pageSize){
        StringBuffer sql = new StringBuffer(
                "select  id, " +
                        "created_on created_on," +
                        "updated_on,  " +
                        "updated_timestamp, " +
                        "data, " +
                        "data_type, " +
                        "device_category,  " +
                        "device_name, " +
                        "device_name_en, " +
                        "unit, " +
                        "user_id,device_id " +
                        "from user_device_data  " +
                        "where " +
                        "     left(created_on,10)>= (select if(exists(select  t.created_on from ( select distinct left(created_on,10) created_on from user_device_data where data_type='高血压值' order by created_on desc limit :offset,:limit) t order by t.created_on asc limit 1),(select  t.created_on from ( select distinct left(created_on,10) created_on from user_device_data where data_type='高血压值' order by created_on desc limit :offset,:limit) t order by t.created_on asc limit 1),left(DATE_ADD(NOW(), INTERVAL 1 DAY),10)) created_on) " +
                        " and left(created_on,10)<= (select if(exists(select  t.created_on from ( select distinct left(created_on,10) created_on from user_device_data where data_type='高血压值' order by created_on desc limit :offset,:limit) t order by t.created_on desc limit 1),(select  t.created_on from ( select distinct left(created_on,10) created_on from user_device_data where data_type='高血压值' order by created_on desc limit :offset,:limit) t order by t.created_on desc limit 1),left(DATE_ADD(NOW(), INTERVAL -1 DAY),10)) created_on) " +
                        " and  user_id=:user_id and data_type='高血压值' and cast(data as SIGNED )>0 order by created_on desc"
        );

        List<UserDeviceData>  results = execute(session ->session
                .createNativeQuery(sql.toString(),UserDeviceData.class)
                .setParameter("user_id",userId)
                .setParameter("offset",(page-1)*pageSize)
                .setParameter("limit",pageSize)
                .getResultList()
        );
        return results != null ? results : new ArrayList<UserDeviceData>();
    }

    public List<UserDeviceData> getUserDeviceWeightHistory(Long userId,int page,int pageSize){
        StringBuffer sql = new StringBuffer(
                "select  id, " +
                        "created_on created_on," +
                        "updated_on,  " +
                        "updated_timestamp, " +
                        "data, " +
                        "data_type, " +
                        "device_category,  " +
                        "device_name, " +
                        "device_name_en, " +
                        "unit, " +
                        "user_id,device_id " +
                        "from user_device_data  " +
                        "where " +
                        "     left(created_on,10)>= (select if(exists(select  t.created_on from ( select distinct left(created_on,10) created_on from user_device_data where data_type='体重' order by created_on desc limit :offset,:limit) t order by t.created_on asc limit 1),(select  t.created_on from ( select distinct left(created_on,10) created_on from user_device_data where data_type='体重' order by created_on desc limit :offset,:limit) t order by t.created_on asc limit 1),left(DATE_ADD(NOW(), INTERVAL 1 DAY),10)) created_on) " +
                        " and left(created_on,10)<= (select if(exists(select  t.created_on from ( select distinct left(created_on,10) created_on from user_device_data where data_type='体重' order by created_on desc limit :offset,:limit) t order by t.created_on desc limit 1),(select  t.created_on from ( select distinct left(created_on,10) created_on from user_device_data where data_type='体重' order by created_on desc limit :offset,:limit) t order by t.created_on desc limit 1),left(DATE_ADD(NOW(), INTERVAL -1 DAY),10)) created_on) " +

                        " and user_id=:user_id and data_type='体重' and cast(data as SIGNED )>0 order by created_on desc"
        );

        List<UserDeviceData>  results = execute(session ->session
                .createNativeQuery(sql.toString(),UserDeviceData.class)
                .setParameter("user_id",userId)
                .setParameter("offset",(page-1)*pageSize)
                .setParameter("limit",pageSize)
                .getResultList()
        );
        return results != null ? results : new ArrayList<UserDeviceData>();
    }


}
