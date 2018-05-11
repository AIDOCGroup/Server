/*
 * Copyright (c) 2018. Tianyi AIDOC Company.Inc. All Rights Reserved.
 */

package com.tianyi.service.mine.impl;

import com.google.common.collect.Lists;
import com.tianyi.bo.MiningSecretCard;
import com.tianyi.bo.MiningSecretCard.SecretCardStatus;
import com.tianyi.dao.mine.MiningSecretCardDao;
import com.tianyi.framework.bean.Page;
import com.tianyi.framework.bean.Pageable;
import com.tianyi.framework.service.impl.GenericServiceImpl;
import com.tianyi.service.mine.MiningSecretCardService;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.criteria.Predicate;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.Hours;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * server
 *
 * @author Gray.Z
 * @date 2018/4/11 16:51.
 */
@Service
@Transactional(value = "jpaTransactionManager")
public class MiningSecretCardServiceImpl extends GenericServiceImpl<MiningSecretCard, Long> implements
    MiningSecretCardService {

    @Autowired
    private MiningSecretCardDao miningSecretCardDao;

    @Override
    public MiningSecretCard getMiningSecretCard(Long id) {
        return miningSecretCardDao.findById(id).get();
    }

    @Override
    public List<MiningSecretCard> getUserMiningSecretCards(Long userId) {
        return miningSecretCardDao.findByUserIdAndStatusAndInvalidDateGreaterThan(userId, SecretCardStatus.ACTIVE.ordinal(), DateTime.now().toDate());
    }

    @Override
    public List<MiningSecretCard> getAllActiveCards() {
        return miningSecretCardDao.findAll((Specification<MiningSecretCard>) (root, criteriaQuery, criteriaBuilder) -> {
            Predicate statusPredicate = criteriaBuilder.equal(root.get("status"), SecretCardStatus.ACTIVE.ordinal());
            Predicate activeDatePredicate = criteriaBuilder.lessThanOrEqualTo(root.get("activeDate"), DateTime.now().toDate());
            Predicate invalidPredicate = criteriaBuilder.greaterThanOrEqualTo(root.get("invalidDate"), DateTime.now().toDate());
            criteriaQuery.where(criteriaBuilder.and(statusPredicate, activeDatePredicate, invalidPredicate));
            return criteriaQuery.getRestriction();
        });
    }

    @Override
    public Page<MiningSecretCard> findAll(Pageable pageable) {
        PageRequest pageRequest = getPageRequest(pageable);
        org.springframework.data.domain.Page<MiningSecretCard> page = miningSecretCardDao.findAll(
            getSpecification(pageable), pageRequest);

        List<MiningSecretCard> result = Lists.newArrayList();
        page.getContent().stream().forEach(miningSecretCard -> {
            miningSecretCard.setLeftTime(
                new BigDecimal(Hours.hoursBetween(DateTime.now(), new DateTime(miningSecretCard.getActiveDate())
                    .plusDays(miningSecretCard.getValidDay())).getHours())
            );
            result.add(miningSecretCard);
        });
        return new Page<>(result, page.getTotalElements(), pageable);
    }

    @Override
    public MiningSecretCard save(MiningSecretCard miningSecretCard) {
        miningSecretCard.setActiveDate(DateTime.now().plusDays(1).withTimeAtStartOfDay().toDate());
        miningSecretCard.setInvalidDate(
            DateTime.now().plusDays(1).withTimeAtStartOfDay().plusDays(miningSecretCard.getValidDay()).toDate());

        if (StringUtils.isEmpty(miningSecretCard.getSerialNo())) {
            miningSecretCard.setSerialNo(String.format("%s-%s-%s-%s",
                RandomStringUtils.randomAlphanumeric(4).toUpperCase(),
                RandomStringUtils.randomAlphanumeric(4).toUpperCase(),
                RandomStringUtils.randomAlphanumeric(4).toUpperCase(),
                RandomStringUtils.randomAlphanumeric(4).toUpperCase()));
        }

        miningSecretCard.setCreateDate(DateTime.now().toDate());
        miningSecretCard.setModifyDate(DateTime.now().toDate());
        return miningSecretCardDao.save(miningSecretCard);
    }

    @Override
    public MiningSecretCard update(MiningSecretCard miningSecretCard) {
        miningSecretCard.setModifyDate(DateTime.now().toDate());
        return miningSecretCardDao.saveAndFlush(miningSecretCard);
    }

    @Override
    public void remove(Long id) {
        miningSecretCardDao.deleteById(id);
    }
}
