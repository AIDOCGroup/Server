/*
 * Copyright (c) 2018. Tianyi AIDOC Company.Inc. All Rights Reserved.
 */

package com.tianyi.dao.mine;

import com.tianyi.bo.mine.MineComputeProfit;
import com.tianyi.framework.dao.IGenericRepository;
import java.util.List;
import java.util.Map;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * server
 *
 * @author Gray.Z
 * @date 2018/4/21 11:07.
 */
@Repository
public interface MineComputeProfitDao extends IGenericRepository<MineComputeProfit, Long> {


    /**
     * 查询有效矿池算力
     *
     * @param userId 用户id
     * @param businessId 业务表ID
     * @param computeType 算力类型
     * @return MineComputeProfit
     */
    @Query(value = " SELECT * FROM mine_compute_profit WHERE user_id=?1 AND is_compute_enable=1 AND compute_invalid_date>now() "
        + " AND business_id=?2 AND compute_type=?3", nativeQuery = true)
    MineComputeProfit getValidCompute(Long userId, String businessId, Integer computeType);

    /**
     * 根据用户ID查询收益和算力
     *
     * @param userId 用户ID
     * @return 算力收益集合
     */
    List<MineComputeProfit> findByUserId(Long userId);


    /**
     * 获取用户登录收益数量
     *
     * @param userId 用户ID
     * @param date 日期
     * @return 数量
     */
    @Query(value = "SELECT count(*) FROM mine_compute_profit WHERE user_id=?1 AND compute_type=11  AND LEFT(create_date,10)=?2"
        , nativeQuery = true)
    Integer getUserLogonProfitCount(Long userId, String date);

    /**
     * 查询用户未收取的矿池收益
     *
     * @param userId 用户ID
     * @return 用户矿池收益集合
     */
    @Query(value = "SELECT * FROM mine_compute_profit WHERE user_id=?1 AND is_profit_collect=false"
        + " AND profit_invalid_date>now() ORDER BY compute_profit DESC", nativeQuery = true)
    List<MineComputeProfit> getUserProfit(Long userId);

    /**
     * 查询用户最近N天已收取的矿池收益
     *
     * @param userId 用户ID
     * @param topN 前N条记录
     */
    @Query(value = "SELECT * FROM mine_compute_profit WHERE user_id=?1 AND is_profit_collect=true "
        + " AND LEFT(profit_collect_date,10) IN ( SELECT t.date  FROM ( "
        + "    SELECT LEFT(profit_collect_date,10) AS date FROM mine_compute_profit WHERE user_id=?1"
        + "         AND is_profit_collect=true "
        + "         GROUP BY LEFT(profit_collect_date,10)  ORDER BY  LEFT(profit_collect_date,10)  DESC LIMIT ?2 "
        + "         ) t) order by profit_collect_date DESC ", nativeQuery = true)
    List<MineComputeProfit> getUserProfitLastDay(Long userId, Integer topN);


    /**
     * 查询数量
     *
     * @param userId 用户ID
     * @param computeTypeCode 业务表ID
     * @param dateTimeFrom 开始日期
     * @param dateTimeTo 结束日期
     * @return Integer
     */
    @Query(value = "select count(*) as count from mine_compute_profit WHERE user_id=?1 and "
        + "compute_type=?2 AND LEFT(create_date,10)>=?3 AND LEFT(create_date,10)<=?4", nativeQuery = true)
    Integer getCountOfSpan(Long userId, int computeTypeCode, String dateTimeFrom,
        String dateTimeTo);


    /**
     * 获取活跃总算力
     *
     * @param date 日期
     * @return 活跃总算力
     */
    @Query(value =
        "SELECT sum(compute_value) FROM mine_compute_profit WHERE is_compute_enable=true "
            + " AND compute_invalid_date>now() AND user_id IN ("
            + " SELECT user_id FROM user_day_data  WHERE"
            + " LEFT(created_on,10)=?1  GROUP BY user_id)", nativeQuery = true)
    Integer getTotalActiveCompute(String date);


    /**
     * 获取用户算力（截止到当前时间的有效算力）
     *
     * @param userId 用户ID
     * @return 用户算力
     */
    @Query(value =
        "SELECT sum(compute_value) FROM mine_compute_profit WHERE is_compute_enable=true "
            + " AND compute_invalid_date>now() AND user_id=?1", nativeQuery = true)
    Integer getUserCompute(Long userId);


    @Query(value = " SELECT * FROM ( SELECT (@rowNum\\:=@rowNum+1) as ranking, compute_value,user_id FROM ("
        + "  SELECT  sum(compute_value) AS compute_value ,user_id FROM mine_compute_profit a "
        + "  WHERE is_compute_enable=true AND compute_invalid_date>now()"
        + "  GROUP BY user_id ORDER BY compute_value DESC"
        + ") t ,(Select (@rowNum\\:=0) ) b ) t1 WHERE user_id=?1", nativeQuery = true)
    List<Map<String, Object>> getUserComputeRanking(Long userId);

    /**
     * 获取预计发币量（平台当日活跃用户量*N币量）
     *
     * @param date 日期
     * @return 预计发币量
     */
    @Query(value = "SELECT count(*) * ( "
        + "SELECT ifnull(set_value,5) FROM aidoc_set WHERE status=1  AND set_type=5)AS count FROM ( "
        + "    SELECT user_id  FROM user_day_data  WHERE"
        + "    LEFT(created_on,10)=?1"
        + "    GROUP BY user_id"
        + ")t", nativeQuery = true)
    Integer getEstimatedAmountOfAidoc(String date);


    /**
     * 更新AIDOC收益,算力小于30的话收益除2，收益最大为5
     *
     * @param totalActiveCompute 活跃总算力
     * @param estimatedAmountOfAidoc 预计发币量
     * @param date 日期
     * @return 更新行数
     */
    @Modifying
    @Query(value = "UPDATE mine_compute_profit SET compute_profit = IF(compute_value<30,compute_value/?1*?2/2,"
        + "IF(compute_value/?1*?2>5,5,compute_value/?1*?2)) WHERE "
        + " is_compute_enable=true  AND compute_invalid_date>now() AND compute_profit=0 ", nativeQuery = true)
    Integer updateComputeProfit(Integer totalActiveCompute, Integer estimatedAmountOfAidoc, String date);

    /**
     * 更新收益状态
     *
     * @param id 矿池收益ID
     */
    @Modifying
    @Query(value = "UPDATE mine_compute_profit SET profit_collect_date = now(3),is_profit_collect=true WHERE id=?1 ", nativeQuery = true)
    void updateMineProfit(Long id);
}
