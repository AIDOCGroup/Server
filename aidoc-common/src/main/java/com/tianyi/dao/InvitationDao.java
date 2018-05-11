package com.tianyi.dao;

import com.tianyi.bo.Invitation;
import com.tianyi.bo.enums.UserType;
import com.tianyi.dao.base.BaseDao;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.*;

/**
 * Created by gaozhilai on 2018/3/27.
 */
@Repository
public class InvitationDao extends BaseDao<Invitation> {
    public List getOperateList(){
        String sql="SELECT id,nickname FROM user WHERE user_type="+ UserType.OPERATE.ordinal();
        Session session=getSessionFactory().openSession();
        List<Object[]> results=session.createNativeQuery(sql).getResultList();
        List result=new ArrayList();
        for (Object[] ele :
                results) {
            Map temp=new HashMap();
            temp.put("user_id",ele[0]);
            temp.put("nickname",ele[1]);
            result.add(temp);
        }
        session.close();
        return result;
    }
}
