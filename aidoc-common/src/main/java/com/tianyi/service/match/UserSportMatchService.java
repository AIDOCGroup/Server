/*
 * Copyright (c) 2018. Tianyi AIDOC Company.Inc. All Rights Reserved.
 */

package com.tianyi.service.match;

import com.tianyi.bo.match.SportMatch;
import com.tianyi.bo.match.UserSportMatch;
import com.tianyi.framework.service.IGenericService;
import java.util.List;

/**
 * server
 *
 * @author Gray.Z
 * @date 2018/5/7 12:01.
 */
public interface UserSportMatchService extends IGenericService<UserSportMatch, Long> {

    /**
     * 查询用户报名的所有赛事
     *
     * @param userId 用户ID
     * @return 用户赛事
     */
    List<UserSportMatch> getUserEnteredMatches(Long userId);

    /**
     * 查询已经报名赛事的用户
     *
     * @param matchId 赛事ID
     * @return 用户赛事
     */
    List<UserSportMatch> getEnteredMatchUsers(Long matchId);

    /**
     * 查询用户报名的赛事
     *
     * @param userId 用户ID
     * @param matchId 赛事ID
     * @return 用户赛事
     */
    UserSportMatch getUserSportMatch(Long userId, Long matchId);

    /**
     * 查询最近10个报名的用户赛事
     *
     * @return 用户赛事
     */
    List<UserSportMatch> getUserSportMatchTop10();

    /**
     * 查询报名赛事的用户数量
     *
     * @param matchId 赛事ID
     * @return 报名数量
     */
    Long getMatchEnterUserCount(Long matchId);

    /**
     * 用户报名赛事
     *
     * @param userId 用户ID
     * @param sportMatch 赛事
     * @return 报名结果
     */
    boolean enterMatch(Long userId, SportMatch sportMatch);

    /**
     * 查询指定报名状态的用户赛事
     *
     * @param enterStatus 报名状态
     * @return 用户赛事
     */
    List<UserSportMatch> getAll(Integer enterStatus);
}
