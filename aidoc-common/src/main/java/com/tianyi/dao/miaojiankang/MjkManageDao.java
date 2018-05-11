package com.tianyi.dao.miaojiankang;

import com.tianyi.bo.miaojiankang.MjkManageResult;
import com.tianyi.dao.base.BaseDao;
import com.tianyi.util.DateUtil;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

/**
 * Created by gaozhilai on 2018/3/26.
 */
@Repository
public class MjkManageDao extends BaseDao<MjkManageResult>{
    public static final Logger logger = LogManager.getLogger(MjkManageDao.class);
    public List<MjkManageResult> getMjkInfo(Integer page,Integer size,Integer dataType){
       /* String sql="SELECT data.created_on 时间,data.data 数据,data.unit 单位,u.nickname 昵称,u.mobile 电话,data.data_type 检测类型,device.device_name 设备品牌,device.type_name 设备类型 " +
                "FROM (user_device_data data LEFT JOIN USER u ON data .user_id = u.id) " +
                "LEFT JOIN user_device device ON u.id = device.user_id " +
                "WHERE u.id = device.user_id AND device.user_id = data.user_id ";*/
        String sql="SELECT dd.created_on 时间,dd.data 数据,dd.unit 单位,u.mobile 电话,u.nickname 昵称,dd.data_type 监测类型,t.device_name 设备品牌,t.type_name 设备类型 " +
                "FROM user_device_data dd " +
                "LEFT JOIN (SELECT DISTINCT device_name,device_id,type_name,user_id FROM user_device  ORDER BY user_id DESC,device_id DESC) t " +
                "ON dd.device_id= t.device_id AND dd.user_id =t.user_id LEFT JOIN user u ON u.id = t.user_id " +
                "WHERE  dd.device_id IS NOT NULL";

        if(dataType!=null){
            if(dataType==1){
                sql=sql+" AND dd.data_type LIKE '%体重%'";
            }else if(dataType==2){
                sql=sql+" AND dd.data_type LIKE '%血糖%'";
            }else if(dataType==3){
                sql=sql+" AND dd.data_type LIKE '%血压%'";
            }else if(dataType==4){
                sql=sql+" AND dd.data_type LIKE '%心率%'";
            }else if(dataType==5){
                sql=sql+" AND dd.data_type LIKE '%步数%'";
            }
        }
        sql=sql+" ORDER BY dd.created_on DESC,u.id ASC";
        if(dataType==3){
            size=size*2;
        }
        if(page!=null&&size!=null){
            int offset=0;
            if(page>0){
                offset=(page-1)*size;
            }
            sql=sql+" LIMIT "+offset+","+size;
        }
        logger.debug("获取妙健康信息sql为{}",sql);
        Session session=getSessionFactory().openSession();
        List<Object[]> tempResult=session.createNativeQuery(sql).list();
        List<MjkManageResult> result=new ArrayList<>();
        for (Object[] ele :
                tempResult) {
            MjkManageResult temp=new MjkManageResult();
            temp.setTime(DateUtil.formatTime((Date)ele[0]));
            temp.setData(ele[1]+"");
            temp.setUnit(ele[2]+"");
            temp.setNickname(ele[3]+"");
            temp.setMobile(ele[4]+"");
            temp.setDatatype(ele[5]+"");
            temp.setDevicename(ele[6]+"");
            temp.setDevicetype(ele[7]+"");
            result.add(temp);
        }
        for (MjkManageResult ele :
                result) {
            if("步数".equals(ele.getDatatype())){
                ele.setDatatype("运动");
            }
        }
        if(dataType==3){
            result=fixBloodPressure(result);
        }
        session.close();
        return result;
    }

    public List<MjkManageResult> fixBloodPressure(List<MjkManageResult> param){
        List<MjkManageResult> highResult=new ArrayList<>();
        List<MjkManageResult> lowResult=new ArrayList<>();
        for(int i=0;i<param.size();i++){
            MjkManageResult ele = param.get(i);
            if(ele.getDatatype().contains("高血压值")){
                highResult.add(ele);
            }else if(ele.getDatatype().contains("低血压值")){
                lowResult.add(ele);
            }
        }
        for(int i=0;i<highResult.size();i++){
            MjkManageResult high=highResult.get(i);
            MjkManageResult low=lowResult.get(i);
                high.setData(high.getData()+"/"+low.getData());
                high.setDatatype("血压");
        }
        return highResult;
    }

    public int getMjkInfoTotal(Integer dataType){
        String sql="SELECT COUNT(*) " +
                "FROM user_device_data dd " +
                "LEFT JOIN (SELECT DISTINCT device_name,device_id,type_name,user_id FROM user_device  ORDER BY user_id DESC,device_id DESC) t " +
                "ON dd.device_id= t.device_id AND dd.user_id =t.user_id LEFT JOIN user u ON u.id = t.user_id " +
                "WHERE  dd.device_id IS NOT NULL AND dd.data_type!='低血压值'";

        if(dataType!=null){
            if(dataType==1){
                sql=sql+" AND dd.data_type LIKE '%体重%'";
            }else if(dataType==2){
                sql=sql+" AND dd.data_type LIKE '%血糖%'";
            }else if(dataType==3){
                sql=sql+" AND dd.data_type LIKE '%高血压值%'";
            }else if(dataType==4){
                sql=sql+" AND dd.data_type LIKE '%心率%'";
            }
        }
        logger.debug("测试获取妙健康信息总数sql为{}",sql);
        Session session=getSessionFactory().openSession();
        int total=Integer.parseInt(session.createNativeQuery(sql).uniqueResult()+"");
       session.close();
        return total;
    }
}
