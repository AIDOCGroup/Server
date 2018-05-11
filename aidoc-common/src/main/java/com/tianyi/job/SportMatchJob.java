/*
 * Copyright (c) 2018. Tianyi AIDOC Company.Inc. All Rights Reserved.
 */

package com.tianyi.job;

import com.tianyi.bo.enums.AccountChannelEnum;
import com.tianyi.bo.match.SportMatch;
import com.tianyi.bo.match.UserMatchSteps;
import com.tianyi.bo.match.UserSportMatch;
import com.tianyi.service.AccountService;
import com.tianyi.service.match.SportMatchService;
import com.tianyi.service.match.UserMatchStepsService;
import com.tianyi.service.match.UserSportMatchService;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 赛事奖励发放调度
 *
 * @author Gray.Z
 * @date 2018/5/5 14:37.
 */
public class SportMatchJob implements Job, Serializable {

    protected final Logger logger = LogManager.getLogger(this.getClass());
    private static final long serialVersionUID = 1L;

    @Autowired
    private AccountService accountService;
    @Autowired
    private UserMatchStepsService userMatchStepsService;
    @Autowired
    private SportMatchService sportMatchService;
    @Autowired
    private UserSportMatchService userSportMatchService;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        logger.info("赛事调度任务开始执行，任务名称：{}", context.getJobDetail().getKey().getName());
        JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
        Long matchId = jobDataMap.getLongValue("data0");
        SportMatch sportMatch = sportMatchService.find(matchId);
        if (sportMatch != null) {
            //当前赛事参与的用户
            List<UserSportMatch> enteredMatchUsers = userSportMatchService.getEnteredMatchUsers(sportMatch.getId());
            for (UserSportMatch enteredMatchUser : enteredMatchUsers) {
                List<UserMatchSteps> userMatchSteps = userMatchStepsService.getUserMatchSteps(enteredMatchUser.getUserId(), sportMatch.getId());

                boolean matchResult = true;
                for (UserMatchSteps userMatchStep : userMatchSteps) {
                    if (userMatchStep.getSteps() < sportMatch.getTargetSteps() && !userMatchStep.getAddition()) {
                        matchResult = false;
                        break;
                    }
                }

                if (matchResult) {
                    Double stepsRewards = userMatchSteps.stream().mapToDouble(userSteps -> userSteps.getRewards().doubleValue()).sum();
                    //赛事奖励为步数奖励的0.2倍+契约金
                    Double matchRewards = stepsRewards * 0.2 + sportMatch.getEnterCost().doubleValue();
                    accountService.consumeCoin(enteredMatchUser.getUserId(), AccountChannelEnum.SPORT_MATCH_REWARD.name(), matchRewards.longValue()
                        * 1000, DateTime.now().toDate());

                    enteredMatchUser.setMatchResult(matchResult ? 1 : 0);
                    enteredMatchUser.setMatchReward(BigDecimal.valueOf(matchRewards));
                    userSportMatchService.update(enteredMatchUser);
                }
            }
        }
    }
}
