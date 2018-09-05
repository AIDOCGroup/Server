package com.tianyi.job;

import com.tianyi.bo.match.SportMatch;
import com.tianyi.bo.match.UserMatchSteps;
import com.tianyi.bo.match.UserSportMatch;
import com.tianyi.bo.mine.MineComputeProfit;
import com.tianyi.framework.util.Constants;
import com.tianyi.framework.util.JSONUtils;
import com.tianyi.service.match.UserMatchStepsService;
import com.tianyi.service.match.UserSportMatchService;
import com.tianyi.service.mine.MineComputeProfitService;
import java.io.Serializable;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * server
 *
 * @author GaoZhilai
 * @date 2018/8/17.
 */
public class StepProfitJob implements Job,Serializable {
  private final Logger logger = LogManager.getLogger(this.getClass());

  @Autowired
  private MineComputeProfitService mineComputeProfitService;
  @Autowired
  private UserSportMatchService userSportMatchService;
  @Autowired
  private UserMatchStepsService userMatchStepsService;

  @Override
  public void execute(JobExecutionContext context) throws JobExecutionException {
//    System.out.println("测试奖励调度"+System.currentTimeMillis());
//    System.out.println("已经执行完成");
    addStepsComputeAndUpdateProfit();
  }

  public void addStepsComputeAndUpdateProfit() {
      //业务代码开始
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
      //业务代码结束
      InetAddress addr = null;
      try {
        addr = InetAddress.getLocalHost();
      } catch (UnknownHostException e) {
        e.printStackTrace();
      }
      String ip=addr.getHostAddress().toString();
      logger.info("步数奖励调度执行项目所在服务器ip为{}", ip);
  }

  private void addUserMatchSteps(String date) {
    logger.info("矿池收益任务完成，开始用户赛事任务。。。");
    DateTime yesterday = new DateTime(date).secondOfDay().withMaximumValue();
    try {
      List<UserSportMatch> userSportMatches = userSportMatchService.getAll(1);
      for (UserSportMatch userSportMatch : userSportMatches) {
        SportMatch sportMatch = userSportMatch.getSportMatch();
        Date endDate = sportMatch.getEndDate();
        Date startDate = sportMatch.getStartDate();
        logger.info("sport match info,period:{},start date:{},end date:{}", sportMatch.getPeriod(), startDate, endDate);
        //比赛已经开始，并且没有结束
        if (startDate.compareTo(yesterday.toDate()) < 0 && endDate.compareTo(yesterday.toDate()) >= 0) {
          UserMatchSteps userMatchSteps = new UserMatchSteps();
          userMatchSteps.setUserId(userSportMatch.getUserId());
          userMatchSteps.setMatchId(sportMatch.getId());
          userMatchSteps.setAddition(false);
          userMatchSteps.setMatchDate(new DateTime(date).secondOfDay().withMaximumValue().toDate());
          userMatchSteps.setPeriod(sportMatch.getPeriod());

          MineComputeProfit mineComputeProfit = mineComputeProfitService.getUserSportStepsProfit(userSportMatch.getUserId(), date);
          if (mineComputeProfit != null) {
            userMatchSteps.setRewards(mineComputeProfit.getComputeProfit());
            userMatchSteps.setSteps(Integer.valueOf(mineComputeProfit.getBusinessId()));
          } else {
            userMatchSteps.setRewards(BigDecimal.ZERO);
            userMatchSteps.setSteps(0);
          }
          userMatchSteps.setCreateDate(DateTime.now().toDate());
          userMatchSteps.setModifyDate(DateTime.now().toDate());
          userMatchStepsService.save(userMatchSteps);
        } else {
          logger.warn("add sport match steps warning :{}", JSONUtils.writeValueAsString(sportMatch));
        }
      }
    } catch (Exception e) {
      logger.error("用户赛事增加用户步数任务异常：", e);
    }
    logger.info("用户赛事任务执行完成。。。");
  }
}
