package com.tianyi.dao;

import com.tianyi.bo.KfzHistoryList;
import com.tianyi.bo.KfzQuestion;
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
 * Created by anhui on 2018/3/2.
 */

@Repository
public class KfzQuestionDao extends BaseDao<KfzQuestion> {
    public static final Logger logger = LogManager.getLogger(KfzQuestionDao.class);
    public List<KfzQuestion> getKfzQuestionHistory(String userId,Integer page,Integer size){
        List<KfzQuestion> result=new ArrayList<>();
        String sql="SELECT kfz.kfz_qid,kfz.kfz_question_body,kfz.kfz_appid,kfz.user_id,kfz.kfz_app_name,kfz.created_on,kfz.kfz_answer,user.mobile FROM kfz_question kfz,user WHERE kfz.user_id=user.id";
        logger.debug("本次获取康夫子历史的session_id为{}",userId);
        if(userId!=null){
            sql=sql+" AND session_id="+userId;
        }
        sql=sql+" ORDER BY user_id ASC, created_on DESC";
        logger.debug("获取康夫子历史sql为{}",sql);
        if(page!=null&&size!=null){
            int offset=0;
            if(page>0){
                offset=(page-1)*size;
            }
            sql=sql+" LIMIT "+offset+","+size;
        }
        Session session=getSessionFactory().openSession();
        List<Object[]> userStepAidocs=session.createNativeQuery(sql).list();
        for (Object[] ele :
                userStepAidocs) {
            KfzQuestion temp=new KfzQuestion();
                temp.setKfzQid(ele[0]+"");
                temp.setKfzQuestionBody(ele[1]+"");
                temp.setKfzAppid(ele[2]+"");
                temp.setUserId(Long.parseLong(ele[3]+""));
                temp.setKfzAppName(ele[4]+"");
                temp.setCreatedOn((Date) ele[5]);
                temp.setCreatedOnFormat(DateUtil.formatTime((Date) ele[5]));
                temp.setKfzAnswer(ele[6]+"");
                temp.setMobile(ele[7]+"");
                result.add(temp);
        }
        session.close();
        return result;
    }
    public int getTotalNumber(String userId){
        String sql="SELECT count(*) FROM kfz_question";
                if(userId!=null){
                    sql=sql+" WHERE user_id="+userId;
                }
                Session session=getSessionFactory().openSession();
        double pageNum=Double.parseDouble(session.createNativeQuery(sql).uniqueResult()+"");
        session.close();
        return (int)pageNum;
    }

    public List<KfzHistoryList> getHistoryList(Integer page,Integer size){
        List<KfzHistoryList> result=new ArrayList<>();
        String sql;
        sql="SELECT kfz.created_on,kfz.kfz_app_name,user.nickname,user.mobile,kfz.session_id FROM kfz_question kfz,user WHERE kfz.user_id=user.id GROUP BY kfz.session_id ORDER BY kfz.created_on DESC";
        if(page!=null&&size!=null){
            int offset=0;
            if(page>0){
                offset=(page-1)*size;
            }
            sql=sql+" LIMIT "+offset+","+size;
        }
        Session session=getSessionFactory().openSession();
        List<Object[]> userStepAidocs=session.createNativeQuery(sql).list();
        for (Object[] ele :
                userStepAidocs) {
            KfzHistoryList temp=new KfzHistoryList();
            temp.setCreateOn(DateUtil.formatTime((Date)ele[0]));
            temp.setType(ele[1]+"");
            temp.setNickName(ele[2]+"");
            temp.setMobile(ele[3]+"");
            temp.setSessionId(ele[4]+"");
            result.add(temp);
        }
        session.close();
        return result;
    }

    public int getKfzHistoryTotalNum(){
        String sql;
        sql="SELECT COUNT(DISTINCT kfz.user_id) FROM kfz_question kfz,user WHERE kfz.user_id=user.id";
        Session session=getSessionFactory().openSession();
        int pageNum=Integer.parseInt(session.createNativeQuery(sql).uniqueResult()+"");
        session.close();
        return pageNum;
    }
}
