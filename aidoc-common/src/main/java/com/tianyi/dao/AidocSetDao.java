package com.tianyi.dao;

import com.tianyi.bo.AidocSet;
import com.tianyi.bo.UserStepAidocResult;
import com.tianyi.dao.base.BaseDao;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

/**
 * Created by 雪峰 on 2018/1/16.
 */
@Repository
public class AidocSetDao extends BaseDao<AidocSet> {
    public static final Logger logger = LogManager.getLogger(AidocSetDao.class);
    public List<AidocSet> getAidocSets(final int type, final int page, final int pageSize,final int status) {

        List<AidocSet> results = execute(session -> (List<AidocSet>) session
                .createNativeQuery("SELECT * FROM aidoc_set WHERE set_type=:setType and status=:status order by id desc", AidocSet.class)
                .setParameter("setType", type)
                .setParameter("status",status)
                .setFirstResult((page - 1) * pageSize)
                .setMaxResults(pageSize)
                .getResultList());


        return results != null ? results : new ArrayList<AidocSet>();
    }


    public List<AidocSet> getEffectiveAidocSets(final int type, final int page, final int pageSize,final int status) {

        List<AidocSet> results = execute(session -> (List<AidocSet>) session
                .createNativeQuery("SELECT * FROM aidoc_set WHERE set_type=:setType and status=:status and now()>effective_date and now()<invalid_date order by id desc", AidocSet.class)
                .setParameter("setType", type)
                .setParameter("status",status)
                .setFirstResult((page - 1) * pageSize)
                .setMaxResults(pageSize)
                .getResultList());


        return results != null ? results : new ArrayList<AidocSet>();
    }



    public Integer getTotalNumber(final int type,final int status) {
        StringBuffer strb = new StringBuffer("SELECT count(*) FROM  aidoc_set WHERE set_type=:setType and status=:status order by id desc ");
        BigInteger num = execute(session -> (BigInteger) session
                .createNativeQuery(strb.toString())
                .setParameter("setType", type)
                .setParameter("status",status)
                .getSingleResult());
        return num == null ? 0 : num.intValue();
    }

    public List<AidocSet> getAidocSetsByDay(final int type, final String day,int status) {

        List<AidocSet> results = execute(session -> (List<AidocSet>) session
                .createNativeQuery("SELECT * FROM aidoc_set WHERE effective_date<:day and invalid_date >:day and set_type=:setType  and status=:status order by id desc", AidocSet.class)
                .setParameter("setType", type)
                .setParameter("day", day)
                .setParameter("status",status)
                .getResultList());


        return results != null ? results : new ArrayList<AidocSet>();
    }

    public List<UserStepAidocResult> getUserStepAidoc(String time, Integer page, Integer size,String keyword,Integer orderBy,Integer aidocOrder,Integer stepOrder){
        Map finalResult=new HashMap<>();
        String sql;
        //if(time!=null){
            sql="SELECT u.id user_id,u.nickname nick_name,u.mobile mobile,IFNULL(ac.reward_amount / 1000, 0) aidoc,nu.num step_number,LEFT(nu.created_on,10) FROM user u LEFT JOIN account a ON a.user_id = u.id LEFT JOIN (SELECT * FROM account_detail ac ";
                    if(time!=null&&!time.equals("")){
                        sql=sql+"WHERE LEFT(created_on, 10) = '"+time+"' " ;
                    }
                    sql=sql+") ac ON ac.account_id = a.id INNER JOIN (SELECT d.user_id, data_number num,created_on FROM user_day_data d ";
                    if(time!=null&&!time.equals("")){
                        sql=sql+"WHERE LEFT(created_on, 10) = '"+time+"' " ;
                    }
                    sql=sql+"GROUP BY d.user_id) nu ON nu.user_id = u.id  " ;
                    if (keyword!=null&&!keyword.equals("")){
                        sql=sql+"WHERE u.nickname LIKE '%"+keyword+"%' OR u.mobile= '"+keyword+"' OR u.id='"+keyword+"'";
                    }
                    if(orderBy!=null||aidocOrder!=null||stepOrder!=null){
                        sql=sql+" ORDER BY ";
                    }
                    if(orderBy!=null&&orderBy==0){
                        sql=sql+"LEFT(nu.created_on,10) DESC";
                    }else{
                        sql=sql+"LEFT(nu.created_on,10) ASC";
                    }
                   /* if(orderBy!=null){
                        sql=sql+"";
                    }*/
                    if(aidocOrder!=null&&aidocOrder==0){
                        sql=sql+" ,aidoc DESC";
                    }else{
                        sql=sql+" ,aidoc ASC ";
                    }
                    /*if(aidocOrder!=null&stepOrder!=null){
                        sql=sql+",";
                    }*/
                    if (stepOrder!=null&&stepOrder==0){
                        sql=sql+" ,nu.num DESC ";
                    }else{
                        sql=sql+" ,nu.num ASC ";
                    }
                    logger.debug("测试获取奖励步数信息sql为{}",sql);
        /*}else{
            sql="SELECT u.id user_id,u.nickname nick_name,u.mobile mobile,IFNULL(ac.reward_amount / 1000, 0) aidoc,nu.num step_number,LEFT(nu.created_on,10) FROM user u LEFT JOIN account a ON a.user_id = u.id LEFT JOIN (SELECT * FROM account_detail ac " +
                    ") " +
                    "ac ON ac.account_id = a.id INNER JOIN (SELECT d.user_id, data_number num,created_on FROM user_day_data d " +
                    "GROUP BY d.user_id) nu ON nu.user_id = u.id  ORDER BY nu.created_on DESC,u.id ASC";
        }*/
        if(page!=null&&size!=null){
            int offset=0;
            if(page>0){
                offset=(page-1)*size;
            }
            sql=sql+" LIMIT "+offset+","+size;
        }
        List<UserStepAidocResult> result=new ArrayList<>();
        Session session=getSessionFactory().openSession();
        List<Object[]> userStepAidocs=session.createNativeQuery(sql).list();
        for (Object[] ele :
                userStepAidocs) {
            UserStepAidocResult userStepAidocResult =new UserStepAidocResult();
            userStepAidocResult.setUserId(Integer.parseInt(ele[0]+""));
            userStepAidocResult.setNickName((String)ele[1]);
            userStepAidocResult.setMobile((String)ele[2]);
            userStepAidocResult.setAidoc(Double.parseDouble(ele[3]+""));
            userStepAidocResult.setStepNumber(Integer.parseInt(ele[4]+""));
            userStepAidocResult.setDay(ele[5]+"");
            result.add(userStepAidocResult);
        }
        /*finalResult.put("allpage",getUserStepAidocPageNum());
        finalResult.put("currentpage",page);
        finalResult.put("pagesize",size);
        finalResult.put("content",result);
        finalResult.put("msg","ok");*/
        session.close();
        return result;
    }

    public int getUserStepAidocPageNum(String time,String keyword){
        String sql;
        sql="SELECT COUNT(*) FROM user u LEFT JOIN account a ON a.user_id = u.id LEFT JOIN (SELECT * FROM account_detail ac ";
        if(time!=null&&!time.equals("")){
            sql=sql+"WHERE LEFT(created_on, 10) = '"+time+"' " ;
        }
        sql=sql+") ac ON ac.account_id = a.id INNER JOIN (SELECT d.user_id, data_number num,created_on FROM user_day_data d ";
        if(time!=null&&!time.equals("")){
            sql=sql+"WHERE LEFT(created_on, 10) = '"+time+"' " ;
        }
        sql=sql+"GROUP BY d.user_id) nu ON nu.user_id = u.id  " ;
        if (keyword!=null&&!keyword.equals("")){
            sql=sql+"WHERE u.nickname LIKE '%"+keyword+"%' OR u.mobile= '"+keyword+"' OR u.id='"+keyword+"'";
        }
        sql=sql+" ORDER BY nu.created_on DESC,u.id ASC";
        Session session=getSessionFactory().openSession();
        double pageNum=Double.parseDouble(session.createNativeQuery(sql).uniqueResult()+"");
        //int result=(int)Math.ceil(pageNum/size);
        session.close();
        return (int)pageNum;
    }
}
