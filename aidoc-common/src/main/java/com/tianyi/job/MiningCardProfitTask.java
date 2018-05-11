/*
 * Copyright (c) 2018. Tianyi AIDOC Company.Inc. All Rights Reserved.
 */

package com.tianyi.job;

import com.tianyi.bo.AidocMarketQuotation;
import com.tianyi.bo.AidocSet;
import com.tianyi.bo.MiningSecretCard;
import com.tianyi.bo.enums.AccountChannelEnum;
import com.tianyi.bo.enums.SetTypeEnum;
import com.tianyi.service.AccountService;
import com.tianyi.service.AidocMarketQuotationService;
import com.tianyi.service.AidocSetService;
import com.tianyi.service.mine.MiningSecretCardService;
import java.math.BigDecimal;
import java.util.List;
import org.apache.commons.collections.CollectionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 挖矿卡收益任务
 *
 * @author Gray.Z
 * @date 2018/4/24 9:22.
 */
@Component
public class MiningCardProfitTask {

    private final Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    private MiningSecretCardService miningSecretCardService;
    @Autowired
    private AidocMarketQuotationService aidocMarketQuotationService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private AidocSetService aidocSetService;

    @Scheduled(cron = "0 0 3 * * ?")
    public void addMiningCardProfit() {
        logger.info("【AIDOC】开始执行挖矿卡收益任务....");
        List<MiningSecretCard> activeCards = miningSecretCardService.getAllActiveCards();
        //当日平均币价
        AidocMarketQuotation aidocAvgPrice = aidocMarketQuotationService.getAvailableAidocMarketQuotation();
        //收益率
        AidocSet profitRate = aidocSetService.getAvailableAidocSet(SetTypeEnum.RateOfReturn.getCode());
        if (CollectionUtils.isNotEmpty(activeCards) && profitRate != null && aidocAvgPrice != null) {
            logger.info("挖矿卡收益计算当日平均币价：{},收益率：{}", aidocAvgPrice, profitRate.getSetValue());
            for (MiningSecretCard activeCard : activeCards) {
                logger.info("挖矿卡收益用户ID:{},用户昵称：{}", activeCard.getUserId(), activeCard.getUsername());
                //每日成本
                BigDecimal costOfDay = activeCard.getPrice().divide(BigDecimal.valueOf(activeCard.getValidDay()));
                //每日成本*收益率/当日AIDOC币价
                Long coin = costOfDay.multiply(new BigDecimal(profitRate.getSetValue()))
                    .divide(BigDecimal.valueOf(aidocAvgPrice.getPriceAvg()), 3, BigDecimal.ROUND_HALF_UP).multiply(BigDecimal.valueOf(1000))
                    .longValue();
                accountService.consumeCoin(activeCard.getUserId(), AccountChannelEnum.MINING_CARD.name(), coin, DateTime.now().toDate());
            }
        } else {
            logger.error("activeCards:{} or aidocAvgPrice:{} or profitRate :{} is null", activeCards, aidocAvgPrice, profitRate);
        }

        logger.info("【AIDOC】挖矿卡收益任务执行完成!");
    }

}
