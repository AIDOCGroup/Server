package com.tianyi.dao;

import com.tianyi.bo.User;
import com.tianyi.bo.UserDataResult;
import com.tianyi.bo.enums.SexEnum;
import com.tianyi.bo.enums.UserStatusEnum;
import com.tianyi.bo.enums.UserType;
import com.tianyi.dao.base.BaseDao;
import com.tianyi.util.DateUtil;
import com.tianyi.util.StringUtil;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

/**
 * Created by 雪峰 on 2018/1/1.
 */
@Repository
public class UserDao extends BaseDao<User> {

    public static final Logger logger = LogManager.getLogger(UserDao.class);
    public User getUserByUsername(final String username) {
        return execute(session -> session
                .createNativeQuery("SELECT * FROM user WHERE username=:username", User.class)
                .setParameter("username", username)
                .uniqueResult());
    }

    public User getUserByUsername(final String username, final UserType userType) {
        return execute(session -> session
                .createNativeQuery("SELECT * FROM user WHERE username=:username and user_type=:user_type", User.class)
                .setParameter("username", username)
                .setParameter("user_type",  userType.ordinal())
                .uniqueResult());
    }

    public User getUserByMobile(final int countryCode ,final String mobile) {
        return execute(session -> session
                .createNativeQuery("SELECT * FROM user WHERE mobile=:mobile and country_code=:countryCode", User.class)
                .setParameter("mobile", mobile)
                .setParameter("countryCode", countryCode)
                .uniqueResult());
    }

    public User getUserByMobile(final int countryCode ,final String mobile, final UserType userType) {
        return execute(session -> session
                .createNativeQuery("SELECT * FROM user WHERE mobile=:mobile and user_type=:user_type", User.class)
                .setParameter("mobile", mobile)
                .setParameter("user_type",  userType.ordinal())
                //.setParameter("countryCode", countryCode)
                .uniqueResult());
    }

    public List<User> getUserList(final UserType userType, final int page, final int pageSize) {
        List<User> results = execute(session -> (List<User>) session
                .createNativeQuery("SELECT * FROM user where user_type=:user_type ORDER BY id DESC ", User.class)
                .setParameter("user_type", userType.ordinal())
                .setFirstResult((page - 1) * pageSize)
                .setMaxResults(pageSize)
                .getResultList());
        return results != null ? results : new ArrayList<User>();
    }

    public BigInteger getUserTotalNum(final UserType userType) {
        return execute(session -> (BigInteger) session
                .createNativeQuery("select count(*)  from user where user_type=:user_type")
                .setParameter("user_type",  userType.ordinal())
                .getSingleResult());
    }



    public List<User> getUserList(final UserType userType, final int page, final int pageSize, final int start_areaId, final int end_areaId, final String keyword, final UserStatusEnum userStatus,
                                  final SexEnum sex ) {


        StringBuffer strb = new StringBuffer("select * from user  where  1=1");

        if (start_areaId != 0 && end_areaId == 0) {
            strb.append(" and area_id = ").append(start_areaId);
        } else if (start_areaId != 0 && end_areaId != 0) {
            strb.append(" and area_id > ").append(start_areaId);
            strb.append(" and  area_id <").append(end_areaId);
        }
        if (!StringUtils.isEmpty(keyword)) {
            if(keyword.length() == 11&&keyword.matches("[0-9]+")){
                strb.append(" and mobile = '").append(keyword).append("'");
            }else{
                strb.append(" and real_name like '%").append(keyword).append("%'");
            }
        }
        if(userStatus !=null){
            strb.append(" and user_status = ").append(userStatus.ordinal());
        }
        if(sex !=null){
            strb.append(" and sex =").append(sex.ordinal());
        }

        strb.append(" and user_type =").append(userType.ordinal());

        strb.append(" order by id desc");


        List<User> results = execute(session -> session
                .createNativeQuery(strb.toString(), User.class)
                .setFirstResult((page - 1) * pageSize)
                .setMaxResults(pageSize)
                .getResultList());
        return results != null ? results : new ArrayList<User>();
    }


    public long getTotalUserNumber(final UserType userType, final int start_areaId, final int end_areaId, final String keyword, final UserStatusEnum userStatus,
                                         final SexEnum sex ) {
        StringBuffer strb = new StringBuffer("select count(*) from user  where 1=1");

        if (start_areaId != 0 && end_areaId == 0) {
            strb.append(" and area_id = ").append(start_areaId);
        } else if (start_areaId != 0 && end_areaId != 0) {
            strb.append(" and area_id > ").append(start_areaId);
            strb.append(" and  area_id <").append(end_areaId);
        }


        if (!StringUtils.isEmpty(keyword)) {
            if(keyword.length() == 11&&keyword.matches("[0-9]+")){
                strb.append(" and mobile = '").append(keyword).append("'");
            }else{
                strb.append(" and real_name like '%").append(keyword).append("%'");
            }
        }
        if(userStatus !=null){
            strb.append(" and user_status = ").append(userStatus.ordinal());
        }
        if(sex !=null){
            strb.append(" and sex =").append(sex.ordinal());
        }

        strb.append(" and user_type =").append(userType.ordinal());
        BigInteger num =  execute(session -> (BigInteger) session
                .createNativeQuery(strb.toString())
                .getSingleResult());

        return num ==null?0:num.longValue();
    }




    public int getTotalUserNumber(final UserType userType, final Date start_Date, final Date end_Date) {
        BigInteger num =   execute(session -> (BigInteger)session
                .createNativeQuery("select count(*) from user where user_type=:user_type and created_on>:start_date and created_on<:end_date")
                .setParameter("user_type",  userType.ordinal())
                .setParameter("start_date",  DateUtil.formatTime(start_Date))
                .setParameter("end_date",  DateUtil.formatTime(end_Date))
                .getSingleResult());
        return num ==null?0:num.intValue();
    }


    public List<UserDataResult> getUserRanking(){

        StringBuffer sql = new StringBuffer(
                "select " +
                        "u.mobile mobile, " +
                        "u.nickname nickname, " +
                        "ifnull(u.signature,'未设置') signature, " +
                        "d.user_id userId, " +
                        "d.data_number dataNumber, " +
                        "u.avatar avatar  "+
                        "from user u  " +
                        "inner join ( " +
                        "SELECT user_id,data_number FROM user_day_data " +
                        "where " +
                        "left(created_on,10)=left(date_sub(curdate(),interval 1 day),10) and data_number<60000 " +
                        "order by data_number desc " +
                        "limit 50 " +
                        ") d on d.user_id=u.id " +
                        " ");
        Object results = execute(session ->session
                .createNativeQuery(sql.toString()).getResultList()
        );
        return results != null ? (ArrayList<UserDataResult>)results : new ArrayList<UserDataResult>();
    }

    public List<UserDataResult> getUserTotalRanking(){

        StringBuffer sql = new StringBuffer(
                "select " +
                        "u.mobile, " +
                        "u.nickname, " +
                        "ifnull(u.signature,'未设置') signature, " +
                        "d.user_id userId, " +
                        "d.data_number  dataNumber, " +
                        "u.avatar "+
                        "from user u  " +
                        "inner join ( " +
                        "SELECT max(user_id) user_id,sum(data_number) data_number FROM user_day_data " +
                        "group by user_id "+
                        "order by sum(data_number) desc " +
                        "limit 50 " +
                        ") d on d.user_id=u.id " +
                        " ");





        Object results = execute(session ->session
                .createNativeQuery(sql.toString()).getResultList()
        );
        return results != null ? (ArrayList<UserDataResult>)results : new ArrayList<UserDataResult>();

    }

    public List<User> getNonAdminUserWithArea(Integer page, Integer size,String startTime,String endTime,String keyword){
        String sql;
        sql="SELECT u.id,u.created_on,u.updated_on, u.updated_timestamp,u.address," +
                "u.area_id,u.avatar,u.longin_ip,u.longin_num,u.mobile,u.nickname," +
                "u.password_hash,u.real_name,u.signature,u.user_status,u.user_type," +
                "u.username,u.user_language,u.country_code,area.area " +
                "FROM user u LEFT JOIN area ON u.area_id=area.id WHERE 1=1";
        if(startTime!=null&&!startTime.equals("")&&endTime!=null&&!endTime.equals("")){
            sql=sql+" AND u.created_on>'"+startTime+"'"+" AND u.created_on<'"+endTime+"'";
        }
        if(!StringUtil.isStringEmpty(keyword)){
            sql = sql+" AND mobile = '"+keyword+"' OR nickname LIKE '%"+keyword+"%'";
        }
        sql=sql+" ORDER BY created_on DESC ";
        if(page!=null&&size!=null){
            int offset=0;
            if(page>0){
                offset=(page-1)*size;
            }
            sql=sql+" LIMIT "+offset+","+size;
        }
        logger.debug("获取非管理员用户列表带着地区信息的sql为{}",sql);
        Session session=getSessionFactory().openSession();
        List<Object[]> dataresults=session.createNativeQuery(sql).list();
        List<User> users=new ArrayList<>();
        for (Object[] ele :
                dataresults) {
            User temp=new User();
            temp.setId(Long.parseLong(ele[0]+""));
            temp.setCreatedOn((Date) ele[1]);
            temp.setUpdatedOn((Date) ele[2]);
            //Date datetemp=(Date) ele[3];
            temp.setUpdatedTimestamp(Long.parseLong(ele[3]+""));
            temp.setAddress(ele[4]+"");
            temp.setAreaId(Integer.parseInt(ele[5]+""));
            temp.setAvatar(ele[6]+"");
            temp.setLonginIp(ele[7]+"");
            temp.setLonginNum(Long.parseLong(ele[8]+""));
            temp.setMobile(ele[9]+"");
            temp.setNickname(ele[10]+"");
            temp.setPasswordHash(ele[11]+"");
            temp.setRealName(ele[12]+"");
            temp.setSignature(ele[13]+"");
            int value=Integer.parseInt(ele[14]+"");
            if(value==0){
                temp.setUserStatus(UserStatusEnum.EFFECTIVE);
            }else{
                temp.setUserStatus(UserStatusEnum.INVALID);
            }
            int vvvvv=Integer.parseInt(ele[15]+"");
            if(vvvvv==0){
                temp.setUserType(UserType.USER);
            }else{
                temp.setUserType(UserType.ADMIN);
            }
            temp.setUsername(ele[16]+"");
            temp.setUserLanguage(Integer.parseInt(ele[17]+""));
            temp.setCountryCode(Integer.parseInt(ele[18]+""));
            temp.setArea(ele[19]+"");
            users.add(temp);
        }
        session.close();
        return users;
    }

    public Integer getNonAdminTotalNum(String startTime,String endTime,String keyword){
        String sql;
        sql="SELECT COUNT(*) FROM user";
        if(!StringUtil.isStringEmpty(startTime)&&!StringUtil.isStringEmpty(endTime)||!StringUtil.isStringEmpty(keyword)){
            sql = sql+" WHERE 1=1 ";
        }
        if(startTime!=null&&!startTime.equals("")&&endTime!=null&&!endTime.equals("")){
            sql=sql+" AND created_on>'"+startTime+"'"+" AND created_on<'"+endTime+"'";
        }
        if(!StringUtil.isStringEmpty(keyword)){
            sql = sql+" AND mobile = '"+keyword+"' OR nickname LIKE '%"+keyword+"%'";
        }
        logger.debug("获取非管理员用户列表带着地区信息总数的sql为{}",sql);
        Session session=getSessionFactory().openSession();
        int result=Integer.parseInt(session.createNativeQuery(sql).uniqueResult()+"");
        session.close();
       return result;
    }

    public boolean isUserExist(long userId){
        User user=execute(session -> session.createNativeQuery("SELECT * FROM user WHERE id=:userId",User.class)
        .setParameter("userId",userId)
        .uniqueResult());
        return user!=null?true:false;
    }

    public List<User> getUserByType(int userType){
        List<User> users=execute(session -> session.createNativeQuery("SELECT * FROM  user WHERE user_type=:userType",User.class)
        .setParameter("userType",userType)
        .getResultList());
        return users;
    }
}
