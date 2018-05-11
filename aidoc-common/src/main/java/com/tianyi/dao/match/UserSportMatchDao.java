/*
 * Copyright (c) 2018. Tianyi AIDOC Company.Inc. All Rights Reserved.
 */

package com.tianyi.dao.match;

import com.tianyi.bo.match.UserSportMatch;
import com.tianyi.framework.dao.IGenericRepository;
import java.util.List;
import java.util.Map;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * server
 *
 * @author Gray.Z
 * @date 2018/5/7 11:58.
 */
@Repository
public interface UserSportMatchDao extends IGenericRepository<UserSportMatch, Long> {

    /**
     * 查询最新10个用户报名的信息
     * @return 结果
     */
    @Query(value = "SELECT usm.*,u.nickname AS username ,sm.type AS match_type  FROM user_sport_match usm LEFT JOIN sport_match sm "
        + " ON sm.ID = usm.match_id LEFT JOIN  USER u ON usm.user_id=u.id "
        + " WHERE usm.enter_status=1 ORDER BY create_date DESC LIMIT 10", nativeQuery = true)
    List<Map<String, Object>> getUserSportMatchTop10();

}
