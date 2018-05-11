/*
 * Copyright (c) 2018. Tianyi AIDOC Company.Inc. All Rights Reserved.
 */

package com.tianyi.service.mine.impl;

import com.google.common.collect.Lists;
import com.tianyi.bo.enums.AccountChannelEnum;
import com.tianyi.bo.enums.ComputeItemType;
import com.tianyi.bo.miaojiankang.UserDevice;
import com.tianyi.bo.mine.ComputeRuleConfig;
import com.tianyi.bo.mine.ComputeRuleConfig.ComputeValidType;
import com.tianyi.bo.mine.MineComputeProfit;
import com.tianyi.bo.mine.UserComputeRanking;
import com.tianyi.dao.mine.ComputeRuleConfigDao;
import com.tianyi.dao.mine.MineComputeProfitDao;
import com.tianyi.framework.bean.Page;
import com.tianyi.framework.bean.Pageable;
import com.tianyi.framework.service.impl.GenericServiceImpl;
import com.tianyi.framework.util.Constants;
import com.tianyi.service.AccountService;
import com.tianyi.service.UserDayDataService;
import com.tianyi.service.miaojiankang.UserDeviceService;
import com.tianyi.service.mine.MineComputeProfitService;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.persistence.criteria.Predicate;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * server
 *
 * @author Gray.Z
 * @date 2018/4/21 13:33.
 */
@Service
@Transactional(value = "jpaTransactionManager")
public class MineComputeProfitServiceImpl extends GenericServiceImpl<MineComputeProfit, Long> implements
    MineComputeProfitService {

    @Autowired
    private MineComputeProfitDao mineComputeProfitDao;
    @Autowired
    private ComputeRuleConfigDao computeRuleConfigDao;
    @Autowired
    private UserDayDataService userDayDataService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private UserDeviceService userDeviceService;

    @Override
    public List<MineComputeProfit> getUserMineProfit(Long userId) {
        return mineComputeProfitDao.getUserProfit(userId);
    }

    @Override
    public List<MineComputeProfit> getUserProfitTopN(Long userId, Integer topN) {
        return mineComputeProfitDao.getUserProfitLastDay(userId, topN);
    }

    @Override
    public Integer getUserCompute(Long userId) {
        return mineComputeProfitDao.getUserCompute(userId);
    }

    @Override
    public UserComputeRanking getUserComputeRanking(Long userId) {
        List<Map<String, Object>> listMap = mineComputeProfitDao.getUserComputeRanking(userId);
        if (CollectionUtils.isNotEmpty(listMap)) {
            Map<String, Object> resultMap = listMap.get(0);
            UserComputeRanking userComputeRanking = new UserComputeRanking();
            userComputeRanking.setUserId(Long.valueOf(resultMap.get("user_id").toString()));
            userComputeRanking.setComputeValue(Integer.parseInt(resultMap.get("compute_value").toString()));
            userComputeRanking.setRanking(Double.valueOf(resultMap.get("ranking").toString()).intValue());
            return userComputeRanking;
        }
        return null;
    }

    @Override
    public Page<MineComputeProfit> findByPageable(Pageable pageable) {
        PageRequest pageRequest = getPageRequest(pageable);
        org.springframework.data.domain.Page<MineComputeProfit> page = mineComputeProfitDao.findAll(
            getSpecification(pageable), pageRequest);

        List<MineComputeProfit> result = Lists.newArrayList();
        page.getContent().stream().forEach(mineComputeProfit -> result.add(mineComputeProfit));
        return new Page<>(result, page.getTotalElements(), pageable);
    }

    @Override
    public List<MineComputeProfit> getUserComputeDetail(Long userId) {
        return mineComputeProfitDao.findByUserId(userId);
    }

    @Override
    public void updateComputeProfit(String updateDate) {
        Integer totalActiveCompute = mineComputeProfitDao.getTotalActiveCompute(updateDate);
        Integer estimatedAmountOfAidoc = mineComputeProfitDao.getEstimatedAmountOfAidoc(updateDate);
        logger.info("日期：{}，活跃总算力：{}，预计发币量：{}", updateDate, totalActiveCompute,
            estimatedAmountOfAidoc);

        mineComputeProfitDao.updateComputeProfit(totalActiveCompute, estimatedAmountOfAidoc, updateDate);
    }

    @Override
    public MineComputeProfit getUserMineProfit(Long userId, Long id) {
        Optional<MineComputeProfit> computeProfitOptional = mineComputeProfitDao.findById(id);
        return computeProfitOptional.filter(profit -> profit.getUserId().equals(userId)).get();
    }

    @Override
    public boolean addSportStepsComputePower(String date) {
        List<Object[]> userDayDataList = userDayDataService.getUserDayDatasDay(DateTime.parse(date,
            DateTimeFormat.forPattern(Constants.DATE_PATTERN)).toDate());
        for (Object[] objects : userDayDataList) {
            if (objects[0] == null || objects[1] == null) {
                continue;
            }
            BigInteger userId = (BigInteger) objects[0];
            BigDecimal totalSteps = (BigDecimal) objects[1];
            addComputePower(userId.longValue(), ComputeItemType.SPORT_STEPS, totalSteps.toString(),
                totalSteps.toString());
        }

        return true;
    }

    @Override
    public MineComputeProfit getUserSportStepsProfit(Long userId, String date) {
        return mineComputeProfitDao.findOne((Specification<MineComputeProfit>) (root, criteriaQuery, criteriaBuilder) -> {
            Predicate userIdEq = null;
            if (null != userId) {
                userIdEq = criteriaBuilder.equal(root.<Long>get("userId"), userId);
            }
            Predicate computeTypeEq = criteriaBuilder.equal(root.get("computeType"), ComputeItemType.SPORT_STEPS.getCode());

            Predicate createDateBt = criteriaBuilder
                .between(root.get("createDate"), new DateTime(date).toDate(), new DateTime(date).secondOfDay().withMaximumValue().toDate());

            if (null != userIdEq) {
                criteriaQuery.where(userIdEq);
            }
            if (null != computeTypeEq) {
                criteriaQuery.where(computeTypeEq);
            }
            if (createDateBt != null) {
                criteriaQuery.where(createDateBt);
            }
            return null;
        }).orElse(null);
    }

    @Override
    public boolean addComputePower(Long userId, ComputeItemType computeItemType, String businessId,
        String... params) {

        logger.info("增加算力接口调用开始,用户ID:{},算力类型：{}，业务ID：{}", userId, computeItemType.getDescription(), businessId);
        ComputeRuleConfig computeRuleConfig = computeRuleConfigDao
            .findByType(computeItemType.getCode());
        if (computeRuleConfig != null) {
            MineComputeProfit mineComputeProfit = new MineComputeProfit();
            mineComputeProfit.setUserId(userId);
            mineComputeProfit.setBusinessId(businessId);
            mineComputeProfit.setComputeType(computeItemType.getCode());
            mineComputeProfit.setRemark(computeItemType.getDescription());
            mineComputeProfit.setCreateDate(DateTime.now().toDate());
            mineComputeProfit.setModifyDate(DateTime.now().toDate());

            if (computeItemType == ComputeItemType.USER_LOGON) {
                Integer logonProfit = mineComputeProfitDao.getUserLogonProfitCount(userId, DateTime.now().toString(Constants.DATE_PATTERN));

                if (logonProfit <= 0) {
                    mineComputeProfit.setComputeProfit(BigDecimal.valueOf(0.1));
                    mineComputeProfit.setProfitValidDate(DateTime.now().toDate());
                    mineComputeProfit.setProfitInvalidDate(DateTime.now().plusHours(computeRuleConfig.getProfitInvalidInterval()).toDate());
                    mineComputeProfit.setProfitCollect(false);
                } else {
                    return false;
                }
            } else {
                mineComputeProfit.setComputeProfit(BigDecimal.ZERO);
                mineComputeProfit.setProfitValidDate(DateTime.now().plusHours(computeRuleConfig.getProfitValidInterval()).toDate());
                mineComputeProfit.setProfitInvalidDate(DateTime.now().plusHours(computeRuleConfig.getProfitInvalidInterval()).toDate());
                mineComputeProfit.setProfitCollect(false);
            }

            if (computeRuleConfig.getComputeValidType() == ComputeValidType.FOREVER.ordinal()) {
                mineComputeProfit.setComputeInvalidDate(DateTime.now().plusDays(365 * 100).toDate());
                if (computeItemType == ComputeItemType.BIND_DEVICE) {
                    MineComputeProfit existDevice = mineComputeProfitDao.getValidCompute(userId, businessId, computeItemType.getCode());
                    if (existDevice != null) {
                        return false;
                    }
                }
            } else {
                mineComputeProfit.setComputeInvalidDate(DateTime.now().plusDays(computeRuleConfig.getComputeValidDay()).toDate());
                DateTime dateTimeFrom = DateTime.now().minusDays(computeRuleConfig.getComputeValidDay());
                Integer count = mineComputeProfitDao.getCountOfSpan(userId, computeItemType.getCode(),
                    dateTimeFrom.toString(Constants.DATETIME_PATTERN), DateTime.now().toString(Constants.DATETIME_PATTERN));
                if (count != null && count > 0) {
                    logger.error(computeItemType.getDescription() + "compute valid day is:{},frequency type is:{}",
                        computeRuleConfig.getComputeValidDay(), computeRuleConfig.getFrequencyType());
                    return false;
                }
            }

            if (computeItemType == ComputeItemType.SPORT_STEPS) {
                if (params != null && params.length > 0) {
                    Integer userSteps = Integer.parseInt(params[0]);
                    BigDecimal userStepsCompute = computeRuleConfig.getUnitComputeValue().multiply(BigDecimal.valueOf(userSteps));
                    if (userStepsCompute.compareTo(BigDecimal.valueOf(computeRuleConfig.getComputeValueUpper())) > 0) {
                        userStepsCompute = BigDecimal.valueOf(computeRuleConfig.getComputeValueUpper());
                    }
                    mineComputeProfit.setComputeValue(userStepsCompute.intValue());
                    mineComputeProfit.setComputeEnable(true);
                    //创建时间更新为前一天
                    mineComputeProfit.setCreateDate(DateTime.now().minusDays(1).minuteOfDay().withMaximumValue().toDate());
                    mineComputeProfitDao.save(mineComputeProfit);
                }
            } else {
                mineComputeProfit.setComputeValue(computeRuleConfig.getUnitComputeValue().intValue());
                mineComputeProfit.setComputeEnable(true);
                mineComputeProfitDao.save(mineComputeProfit);
            }
        }
        logger.info("增加算力成功!");
        return true;
    }

    @Override
    public void removeComputePower(Long userId, String businessId, ComputeItemType computeType) {
        try {
            MineComputeProfit mineComputeProfit = mineComputeProfitDao.getValidCompute(userId, businessId, computeType.getCode());
            if (mineComputeProfit != null) {
                mineComputeProfit.setComputeInvalidDate(DateTime.now().toDate());
                mineComputeProfit.setComputeEnable(false);
                mineComputeProfit.setModifyDate(DateTime.now().toDate());
                mineComputeProfitDao.saveAndFlush(mineComputeProfit);
            }
        } catch (Exception e) {
            logger.error("移除算力异常，用户ID:{},业务ID:{},业务类型：{}", userId, businessId, computeType.getDescription());
        }

    }

    @Override
    public boolean addLogonReward(Long userId) {
        boolean success = addComputePower(userId, ComputeItemType.USER_LOGON, userId + "");
     /*   if (success) {
            accountService.coin(userId, ComputeItemType.USER_LOGON.name(), BigDecimal.valueOf(0.1).multiply(BigDecimal.valueOf(1000)).longValue());
        }*/
        return success;
    }

    @Override
    public void updateMineProfit(Long userId, Long id) {
        MineComputeProfit mineComputeProfit = getUserMineProfit(userId, id);
        BigDecimal profits = mineComputeProfit.getComputeProfit();
        String businessId = StringUtils.defaultString(mineComputeProfit.getBusinessId());
        String accountChannel = getByComputeType(ComputeItemType.getByCode(mineComputeProfit.getComputeType()), businessId, userId).name();
        //增加账户收益
        accountService.consumeCoin(userId, accountChannel, profits.multiply(BigDecimal.valueOf(1000)).longValue(), mineComputeProfit.getCreateDate());
        //更新矿池收益状态
        mineComputeProfitDao.updateMineProfit(id);
    }

    public AccountChannelEnum getByComputeType(ComputeItemType computeItemType, String deviceId, Long userId) {
        if (computeItemType == ComputeItemType.BIND_DEVICE && StringUtils.isNotEmpty(deviceId)) {
            UserDevice device = userDeviceService.getUserDeviceByDeviceId(deviceId, userId);
            if (device != null && StringUtils.isNotEmpty(device.getTid())) {
                AccountChannelEnum channelEnum;
                switch (device.getTid()) {
                    case "10097":
                        channelEnum = AccountChannelEnum.BIND_BRACELET;
                        break;
                    case "10098":
                        channelEnum = AccountChannelEnum.BIND_BLOOD_GLUCOSE_METER;
                        break;
                    case "10099":
                        channelEnum = AccountChannelEnum.BIND_BLOOD_PRESSURE_METER;
                        break;
                    case "10101":
                        channelEnum = AccountChannelEnum.BIND_THERMOMETER;
                        break;
                    case "10102":
                        channelEnum = AccountChannelEnum.BIND_WEIGHING_SCALE;
                        break;
                    default:
                        channelEnum = AccountChannelEnum.UNKNOWN;
                        break;
                }
                return channelEnum;
            }
        }
        if (computeItemType == ComputeItemType.SPORT_STEPS) {
            return AccountChannelEnum.GIVE;
        }
        return AccountChannelEnum.getByName(computeItemType.name());
    }
}
