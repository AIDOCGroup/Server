package com.tianyi.dao.miaojiankang;

import com.tianyi.bo.miaojiankang.MjkManageResult;
import com.tianyi.dao.base.BaseDao;
import com.tianyi.service.UserService;
import com.tianyi.service.miaojiankang.MjkManageService;
import com.tianyi.web.util.DateUtil;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by gaozhilai on 2018/3/26.
 */
@Repository
public class MjkManageDao extends BaseDao<MjkManageResult>{
    public List<MjkManageResult> getMjkInfo(Integer page,Integer size,Integer dataType){
        String sql="SELECT data.created_on 时间,data.data 数据,data.unit 单位,u.nickname 昵称,u.mobile 电话,data.data_type 检测类型,device.device_name 设备品牌,device.type_name 设备类型 " +
                "FROM (user_device_data data LEFT JOIN USER u ON data .user_id = u.id) " +
                "LEFT JOIN user_device device ON u.id = device.user_id " +
                "WHERE u.id = device.user_id AND device.user_id = data.user_id ";

        if(dataType!=null){
            if(dataType==1){
                sql=sql+"AND data.data_type LIKE '%体重%'";
            }else if(dataType==2){
                sql=sql+"AND data.data_type LIKE '%血糖%'";
            }else if(dataType==3){
                sql=sql+"AND data.data_type LIKE '%血压%'";
            }else if(dataType==4){
                sql=sql+"AND data.data_type LIKE '%心率%'";
            }
        }
        if(page!=null&&size!=null){
            int offset=0;
            if(page>0){
                offset=(page-1)*size;
            }
            sql=sql+" LIMIT "+offset+","+size;
        }
        //System.out.println("测试sql:::"+sql);
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
        session.close();
        return result;
    }

    public int getMjkInfoTotal(Integer dataType){
        String sql="SELECT COUNT(*) FROM user_device_data";
        if(dataType!=null){
            if(dataType==1){
                sql=sql+" WHERE data_type LIKE '%体重%'";
            }else if(dataType==2){
                sql=sql+" WHERE data_type LIKE '%血糖%'";
            }else if(dataType==3){
                sql=sql+" WHERE data_type LIKE '%血压%'";
            }else if(dataType==4){
                sql=sql+" WHERE data_type LIKE '%心率%'";
            }
        }
        System.out.println("测试sql:::"+sql);
        Session session=getSessionFactory().openSession();
        int total=Integer.parseInt(session.createNativeQuery(sql).uniqueResult()+"");
        return total;
    }
}
