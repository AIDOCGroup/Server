/*
 * Copyright (c) 2018. Tianyi AIDOC Company.Inc. All Rights Reserved.
 */

package com.tianyi.service.mine;

import com.tianyi.bo.enums.ComputeItemType;
import com.tianyi.bo.mine.MineComputeProfit;
import com.tianyi.bo.mine.UserComputeRanking;
import com.tianyi.framework.bean.Page;
import com.tianyi.framework.bean.Pageable;
import com.tianyi.framework.service.IGenericService;
import java.util.List;

/**
 * server
 *
 * @author Gray.Z
 * @date 2018/4/21 13:31.
 */
public interface MineComputeProfitService extends IGenericService<MineComputeProfit, Long> {


    /**
     * 增加运动步数算力
     *
     * @param date 日期
     * @return 执行状态
     */
    boolean addSportStepsComputePower(String date);


    /**
     * 获取用户步数收益
     *
     * @param userId 用户ID
     * @param date 日期
     * @return 用户步数收益
     */
    MineComputeProfit getUserSportStepsProfit(Long userId, String date);

    /**
     * 获取用户为收取的矿池收益
     *
     * @param userId 用户ID
     * @return List
     */
    List<MineComputeProfit> getUserMineProfit(Long userId);


    /**
     * 查询矿池收益项
     *
     * @param userId 用户ID
     * @param id 矿池收益项ID
     * @return 矿池收益项
     */
    MineComputeProfit getUserMineProfit(Long userId, Long id);

    /**
     * 获取最近一周的用户收益
     *
     * @param userId 用户ID
     * @param topN 前N条
     * @return List
     */
    List<MineComputeProfit> getUserProfitTopN(Long userId, Integer topN);

    /**
     * 获取用户算力
     *
     * @param userId 用户ID
     * @return 用户算力
     */
    Integer getUserCompute(Long userId);

    /**
     * 获取用户算力
     *
     * @param userId 用户ID
     * @return 用户算力
     */
    UserComputeRanking getUserComputeRanking(Long userId);


    /**
     * 分页获取用户收益
     *
     * @param pageable 分页对象
     * @return 分页数据
     */
    Page<MineComputeProfit> findByPageable(Pageable pageable);

    /**
     * 查询用户算力闽西
     *
     * @param userId 用户ID
     * @return 用户算力明细
     */
    List<MineComputeProfit> getUserComputeDetail(Long userId);

    /**
     * 更新用户算力收益
     *
     * @param updateDate 更新日期
     */
    void updateComputeProfit(String updateDate);

    /**
     * 增加算力
     *
     * @param userId 用户ID
     * @param computeItemType 算力类型
     * @param businessId 业务表ID
     * @param params 步数或者其他参数
     * @return 是否增加成功
     */
    boolean addComputePower(Long userId, ComputeItemType computeItemType, String businessId,
        String... params);

    /**
     * 移除算力
     *
     * @param userId 用户ID
     * @param businessId 业务表ID
     * @param computeType 算力类型
     */
    void removeComputePower(Long userId, String businessId, ComputeItemType computeType);


    /**
     * 用户登录奖励
     *
     * @param userId 用户ID
     * @return 登录奖励是否成功
     */
    boolean addLogonReward(Long userId);

    /**
     * 更新用户收益
     *
     * @param userId 用户ID
     * @param id 矿池收益ID
     */
    void updateMineProfit(Long userId, Long id);

}
