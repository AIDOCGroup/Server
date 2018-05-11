/*
 * Copyright (c) 2018. Tianyi AIDOC Company.Inc. All Rights Reserved.
 */

package com.tianyi.dao;

import com.tianyi.bo.UserBehaviorLog;
import com.tianyi.framework.dao.IGenericRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * 用户行为日志DAO
 *
 * @author Gray.Z
 * @date 2018/4/11 16:46.
 */
@Repository
public interface UserBehaviorLogDao extends IGenericRepository<UserBehaviorLog, Long> {

    /**
     * 获取指定日期的活跃用户(同一天内访问5个不同的URL)数量
     *
     * @param date 日期 YYYY-MM-DD
     * @return 活跃用户数量
     */
    @Query(value = "SELECT count(*) as count from ("
        + "  SELECT t.user_id FROM ("
        + "     SELECT user_id,request_url FROM  user_behavior_log WHERE LEFT(create_date,10)=?1"
        + "     GROUP BY user_id,request_url"
        + "  ) t GROUP BY t.user_id  HAVING count(*)>5"
        + ") t1", nativeQuery = true)
    Long getActiveUserCount(String date);

}
