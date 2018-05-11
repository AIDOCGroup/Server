/*
 * Copyright (c) 2018. Tianyi AIDOC Company.Inc. All Rights Reserved.
 */

package com.tianyi.dao.match;

import com.tianyi.bo.match.SportMatch;
import com.tianyi.framework.dao.IGenericRepository;
import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * server
 *
 * @author Gray.Z
 * @date 2018/5/7 11:58.
 */
@Repository
public interface SportMatchDao extends IGenericRepository<SportMatch, Long> {

    @Query(value = "SELECT t1.*,(SELECT count(*) FROM user_sport_match WHERE match_id=t1.id) AS enter_count FROM sport_match t1 where end_date>=now() ORDER BY create_date DESC ",
        countQuery = "SELECT count(*) FROM sport_match where end_date>=now()",
        nativeQuery = true)
    Page<Map<String, Object>> findInProgressByPage(Pageable pageable);


    @Query(value = "SELECT t1.*,(SELECT count(*) FROM user_sport_match WHERE match_id=t1.id) AS enter_count FROM sport_match t1 ORDER BY create_date DESC ",
        countQuery = "SELECT count(*) FROM sport_match",
        nativeQuery = true)
    Page<Map<String, Object>> findAllByPage(Pageable pageable);
}
