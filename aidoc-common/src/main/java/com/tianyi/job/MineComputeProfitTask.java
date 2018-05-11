/*
 * Copyright (c) 2018. Tianyi AIDOC Company.Inc. All Rights Reserved.
 */

package com.tianyi.job;

import com.tianyi.bo.match.UserMatchSteps;
import com.tianyi.bo.match.UserSportMatch;
import com.tianyi.bo.mine.MineComputeProfit;
import com.tianyi.framework.util.Constants;
import com.tianyi.service.match.UserMatchStepsService;
import com.tianyi.service.match.UserSportMatchService;
import com.tianyi.service.mine.MineComputeProfitService;
import java.math.BigDecimal;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 矿池算力收益任务
 *
 * @author Gray.Z
 * @date 2018/4/24 9:23.
 */
@Component
public class MineComputeProfitTask {

    private final Logger logger = LogManager.getLogger(this.getClass());
    @Autowired
    private MineComputeProfitService mineComputeProfitService;
    @Autowired
    private UserSportMatchService userSportMatchService;
    @Autowired
    private UserMatchStepsService userMatchStepsService;

    @Scheduled(cron = "0 0 1 * * ?")
    public void addStepsComputeAndUpdateProfit() {
        String date = DateTime.now().minusDays(1).toString(Constants.DATE_PATTERN);
        logger.info("【AIDOC】开始执行矿池算力收益任务,当前收益结算日期为：{}", date);
        boolean success = mineComputeProfitService.addSportStepsComputePower(date);

        if (success) {
            logger.info("增加运动步数算力成功,开始更新矿池算力收益...");
            mineComputeProfitService.updateComputeProfit(date);
            addUserMatchSteps(date);
        } else {
            logger.error("增加运动步数算力失败...");
        }
        logger.info("【AIDOC】矿池算力收益任务执行完成!");
    }

    private void addUserMatchSteps(String date) {
        logger.info("矿池收益任务完成，开始用户赛事任务。。。");
        try {
            List<UserSportMatch> userSportMatches = userSportMatchService.getAll(1);
            for (UserSportMatch userSportMatch : userSportMatches) {
                UserMatchSteps userMatchSteps = new UserMatchSteps();
                userMatchSteps.setUserId(userSportMatch.getUserId());
                userMatchSteps.setMatchId(userSportMatch.getSportMatch().getId());
                userMatchSteps.setAddition(false);
                userMatchSteps.setMatchDate(new DateTime(date).secondOfDay().withMaximumValue().toDate());
                userMatchSteps.setPeriod(userSportMatch.getSportMatch().getPeriod());

                MineComputeProfit mineComputeProfit = mineComputeProfitService.getUserSportStepsProfit(userSportMatch.getUserId(), date);
                if (mineComputeProfit != null) {
                    userMatchSteps.setRewards(mineComputeProfit.getComputeProfit());
                    userMatchSteps.setSteps(Integer.valueOf(mineComputeProfit.getBusinessId()));
                } else {
                    userMatchSteps.setRewards(BigDecimal.ZERO);
                    userMatchSteps.setSteps(0);
                }

                userMatchStepsService.save(userMatchSteps);
            }
        } catch (Exception e) {
            logger.error("用户赛事增加用户步数任务异常：", e);
        }
        logger.info("用户赛事任务执行完成。。。");
    }

}
