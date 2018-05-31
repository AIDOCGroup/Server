/*
 * Copyright (c) 2018. Tianyi AIDOC Company.Inc. All Rights Reserved.
 */

package com.tianyi.web.controller.mine;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.tianyi.bo.Account;
import com.tianyi.bo.MiningSecretCard;
import com.tianyi.bo.User;
import com.tianyi.bo.enums.ComputeItemType;
import com.tianyi.bo.mine.MineComputeProfit;
import com.tianyi.bo.mine.UserComputeRanking;
import com.tianyi.framework.bean.Filter;
import com.tianyi.framework.bean.Filter.Operator;
import com.tianyi.framework.bean.Order.Direction;
import com.tianyi.framework.bean.Page;
import com.tianyi.framework.bean.Pageable;
import com.tianyi.framework.controller.BaseController;
import com.tianyi.framework.util.Constants;
import com.tianyi.framework.util.Localize;
import com.tianyi.service.AccountService;
import com.tianyi.service.UserService;
import com.tianyi.service.mine.MineComputeProfitService;
import com.tianyi.service.mine.MiningSecretCardService;
import com.tianyi.web.controller.vo.MineCollectedProfitVO;
import com.tianyi.web.controller.vo.MineComputeVO;
import com.tianyi.web.controller.vo.MineProfitVO;
import com.tianyi.web.model.ResponseResult;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.joda.time.DateTime;
import org.joda.time.Hours;
import org.joda.time.Minutes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 矿池算力收益
 *
 * @author Gray.Z
 * @date 2018/4/21 11:02.
 */
@RestController
@RequestMapping(value = "/mine/v1")
public class MineComputeProfitController extends BaseController {

    @Autowired
    private MineComputeProfitService mineComputeProfitService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private MiningSecretCardService miningSecretCardService;
    @Autowired
    private UserService userService;

    /**
     * 矿池收益
     *
     * @return 矿池收益首页数据
     */
    @RequestMapping(value = "/compute_profit/{userId}", method = RequestMethod.GET)
    public Map<String, Object> getProfit(@PathVariable Long userId) {
        Map<String, Object> result = Maps.newHashMap();
        User user = userService.getUserByUserId(userId);
        if (user == null) {
            result.put("err_code", "401");
            result.put("err_msg", Localize.getMessage("res.message.111"));
            return result;
        }

        //登录奖励算力
        boolean success = mineComputeProfitService.addLogonReward(userId);
        result.put("logonReward", success);

        UserComputeRanking userCompute = mineComputeProfitService.getUserComputeRanking(userId);

        if (userCompute == null) {
            userCompute = new UserComputeRanking();
            //用户默认算力为100
            userCompute.setComputeValue(100);
            userCompute.setRanking(0);
        } else {
            userCompute.setComputeValue(userCompute.getComputeValue() + 100);
        }
        result.put("computePower", userCompute);

        Account account = accountService.getAccountByUserId(userId);
        if (account != null && account.getBalance() != null) {
            result.put("aidocBalance", BigDecimal.valueOf(account.getBalance()).divide(BigDecimal.valueOf(1000),
                Constants.DEFAULT_DECIMAL_SCALE, BigDecimal.ROUND_HALF_UP).toString());
        } else {
            result.put("aidocBalance", Constants.DECIMAL_ZERO_STRING);
        }

        List<MiningSecretCard> miningSecretCards = miningSecretCardService.getUserMiningSecretCards(userId);
        if (CollectionUtils.isNotEmpty(miningSecretCards)) {
            result.put("card", Hours.hoursBetween(DateTime.now(), new DateTime(miningSecretCards.get(0).getInvalidDate())).getHours());
        } else {
            result.put("card", 0);
        }

        List<MineComputeProfit> userMineProfits = mineComputeProfitService.getUserMineProfit(userId);
        List<MineProfitVO> profits = userMineProfits.stream().filter(profit -> profit.getComputeProfit().compareTo(BigDecimal.ZERO) != 0)
            .map(profit -> {
                    int leftTime =
                        Minutes.minutesBetween(DateTime.now(), new DateTime(profit.getProfitValidDate())).getMinutes() + RandomUtils.nextInt(10);
                    MineProfitVO mineProfitVO = new MineProfitVO(profit.getId().toString(),
                        ComputeItemType.getByCode(profit.getComputeType()).getDescription(),
                        profit.getComputeProfit().setScale(3, BigDecimal.ROUND_DOWN).toString());
                    mineProfitVO.setLeftTime(leftTime < 0 ? 0 : leftTime);
                    return mineProfitVO;
                }
            ).collect(Collectors.toList());

        if (CollectionUtils.isNotEmpty(profits)) {
            result.put("profits", profits);
        } else {
            result.put("profits", Lists.newArrayList());
        }

        List<MineComputeProfit> userLastProfits = mineComputeProfitService.getUserProfitTopN(userId, 2);
        List<MineCollectedProfitVO> historyProfits = userLastProfits.stream().map(getToCollectedVOFunction()
        ).collect(Collectors.toList());

        if (CollectionUtils.isNotEmpty(historyProfits)) {
            result.put("historyProfits", historyProfits);
        } else {
            result.put("historyProfits", Lists.newArrayList());
        }
        return result;
    }

    private Function<MineComputeProfit, MineCollectedProfitVO> getToCollectedVOFunction() {
        return profit -> {
            MineCollectedProfitVO mineCollectedProfitVO = new MineCollectedProfitVO(
                ComputeItemType.getByCode(profit.getComputeType()).getDescription(),
                profit.getComputeProfit().setScale(3, BigDecimal.ROUND_DOWN).toString());
            mineCollectedProfitVO.setCollectedDate(profit.getProfitCollectDate());
            mineCollectedProfitVO.setCreateTime(profit.getCreateDate());
            return mineCollectedProfitVO;
        };
    }

    /**
     * 收取AIDOC
     *
     * @param id 收益ID
     * @return 账户余额
     */
    @RequestMapping(value = "/compute_profit/{userId}/{id}", method = RequestMethod.PUT)
    public ResponseEntity updateAccount(@PathVariable Long userId, @PathVariable Long id) {
        mineComputeProfitService.updateMineProfit(userId, id);
        Map<String, Object> balanceMap = Maps.newHashMap();
        Account account = accountService.getAccountByUserId(userId);
        MineComputeProfit profit = mineComputeProfitService.getUserMineProfit(userId, id);

        MineCollectedProfitVO mineCollectedProfitVO = new MineCollectedProfitVO(
            ComputeItemType.getByCode(profit.getComputeType()).getDescription(),
            profit.getComputeProfit().setScale(3, BigDecimal.ROUND_DOWN).toString());
        mineCollectedProfitVO.setCollectedDate(profit.getProfitCollectDate());
        mineCollectedProfitVO.setCreateTime(profit.getCreateDate());

        balanceMap.put("aidocBalance",
            BigDecimal.valueOf(account.getBalance()).divide(BigDecimal.valueOf(1000), Constants.DEFAULT_DECIMAL_SCALE, BigDecimal.ROUND_HALF_UP)
                .toString());
        balanceMap.put("collectResult", mineCollectedProfitVO);

        return ResponseEntity.ok(balanceMap);
    }

    @RequestMapping(value = "/compute/{userId}")
    public ResponseEntity getUserComputeDetail(@PathVariable Long userId, @RequestBody(required = false) Pageable pageable) {
        if (pageable == null) {
            pageable = new Pageable();
        }

        pageable.setOrderProperty("modifyDate");
        pageable.setOrderDirection(Direction.desc);
        Filter filter = new Filter("userId", Operator.eq, userId);
        pageable.setFilters(Lists.newArrayList(filter));
        Page<MineComputeProfit> userComputes = mineComputeProfitService.findByPageable(pageable);
        Map<String, List<MineComputeProfit>> groupMap = userComputes.getContent().stream()
            .collect(Collectors.groupingBy(m -> m.getComputeInvalidDate().compareTo(new Date()) > 0 ? "enable" : "disable"));

        Map<String, List<MineComputeVO>> resultMap = Maps.newHashMap();
        for (String key : groupMap.keySet()) {
            resultMap.put(key, groupMap.get(key).stream().map(m -> {
                MineComputeVO computeVO = new MineComputeVO(ComputeItemType.getByCode(m.getComputeType()).getDescription(), m.getComputeValue() + "");
                computeVO.setDate(m.getModifyDate());
                computeVO.setType(m.getComputeInvalidDate().compareTo(DateTime.now().plusYears(50).toDate()) > 0 ? Localize
                    .getMessage("mine_compute_type_forever_desc") : Localize.getMessage("mine_compute_type_temp_desc"));
                return computeVO;
            }).collect(Collectors.toList()));
        }
        return ResponseEntity.ok(resultMap);
    }

    @RequestMapping(value = "/profit/{userId}", method = RequestMethod.POST)
    public ResponseEntity getMineProfitDetail(@PathVariable Long userId, @RequestBody(required = false) Pageable pageable) {
        if (pageable == null) {
            pageable = new Pageable();
        }

        pageable.setOrderProperty("profitCollectDate");
        pageable.setOrderDirection(Direction.desc);
        Map<String, Object> resultMap = Maps.newLinkedHashMap();
        try {
            Filter userIdFilter = new Filter("userId", Operator.eq, userId);
            Filter isCollectedFilter = new Filter("profitCollect", Operator.eq, true);
            pageable.setFilters(Lists.newArrayList(userIdFilter, isCollectedFilter));

            Page<MineComputeProfit> pageData = mineComputeProfitService.findPage(pageable);

            List<MineCollectedProfitVO> profitHistories = pageData.getContent().stream().map(getToCollectedVOFunction()).collect(Collectors.toList());

            resultMap.put("data", profitHistories);
            resultMap.put("pageNumber", pageData.getPageNumber());
            resultMap.put("pageSize", pageData.getPageSize());
            resultMap.put("total", pageData.getTotal());
            resultMap.put("totalPages", pageData.getTotalPages());
        } catch (Exception e) {
            logger.error("获取用户收益明细异常:", e);
            return ResponseResult.INTERNAL_SERVER_ERROR;
        }

        return ResponseEntity.ok(resultMap);
    }

}
