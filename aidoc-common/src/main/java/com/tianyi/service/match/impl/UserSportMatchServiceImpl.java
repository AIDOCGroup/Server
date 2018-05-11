/*
 * Copyright (c) 2018. Tianyi AIDOC Company.Inc. All Rights Reserved.
 */

package com.tianyi.service.match.impl;

import com.google.common.collect.Lists;
import com.tianyi.bo.enums.AccountChannelEnum;
import com.tianyi.bo.match.SportMatch;
import com.tianyi.bo.match.UserSportMatch;
import com.tianyi.dao.match.SportMatchDao;
import com.tianyi.dao.match.UserSportMatchDao;
import com.tianyi.framework.service.impl.GenericServiceImpl;
import com.tianyi.framework.util.BeanPropertyMapMapper;
import com.tianyi.service.AccountService;
import com.tianyi.service.match.UserSportMatchService;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.persistence.criteria.Predicate;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

/**
 * server
 *
 * @author Gray.Z
 * @date 2018/5/7 12:02.
 */
@Service
public class UserSportMatchServiceImpl extends GenericServiceImpl<UserSportMatch, Long> implements
    UserSportMatchService {

    @Autowired
    private AccountService accountService;
    @Autowired
    private UserSportMatchDao userSportMatchDao;
    @Autowired
    private SportMatchDao sportMatchDao;

    @Override
    public List<UserSportMatch> getUserEnteredMatches(Long userId) {

        return userSportMatchDao.findAll((Specification<UserSportMatch>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = Lists.newArrayList();
            Predicate userIdEq = criteriaBuilder.equal(root.<Long>get("userId"), userId);
            Predicate enterStatusEq = criteriaBuilder.equal(root.get("enterStatus"), 1);
            Predicate matchEndTimeGt = criteriaBuilder.greaterThan(root.get("sportMatch").get("endDate"), DateTime.now().toDate());

            if (null != userIdEq) {
                predicates.add(userIdEq);
            }
            if (null != enterStatusEq) {
                predicates.add(enterStatusEq);
            }
            if (matchEndTimeGt != null) {
                predicates.add(matchEndTimeGt);
            }

            return criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]))
                .getRestriction();
        });
    }

    @Override
    public List<UserSportMatch> getEnteredMatchUsers(Long matchId) {
        return userSportMatchDao.findAll((Specification<UserSportMatch>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = Lists.newArrayList();
            Predicate matchIdEq = criteriaBuilder.equal(root.get("sportMatch").get("id"), matchId);
            Predicate enterStatusEq = criteriaBuilder.equal(root.get("enterStatus"), 1);
            Predicate matchEndTimeGt = criteriaBuilder.greaterThan(root.get("sportMatch").get("endDate"), DateTime.now().toDate());

            if (null != matchIdEq) {
                predicates.add(matchIdEq);
            }
            if (null != enterStatusEq) {
                predicates.add(enterStatusEq);
            }
            if (matchEndTimeGt != null) {
                predicates.add(matchEndTimeGt);
            }

            return criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]))
                .getRestriction();
        });
    }

    @Override
    public UserSportMatch getUserSportMatch(Long userId, Long matchId) {
        return userSportMatchDao.findOne((Specification<UserSportMatch>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = Lists.newArrayList();
            Predicate matchIdEq = criteriaBuilder.equal(root.<Long>get("sportMatch").get("id"), matchId);
            Predicate userIdEq = criteriaBuilder.equal(root.<Long>get("userId"), userId);
            Predicate enterStatusEq = criteriaBuilder.equal(root.get("enterStatus"), 1);
            if (null != userIdEq) {
                predicates.add(userIdEq);
            }
            if (null != enterStatusEq) {
                predicates.add(enterStatusEq);
            }
            if (null != matchIdEq) {
                predicates.add(matchIdEq);
            }
            return criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]))
                .getRestriction();
        }).orElse(null);
    }

    @Override
    public List<UserSportMatch> getUserSportMatchTop10() {
        List<Map<String, Object>> list = userSportMatchDao.getUserSportMatchTop10();
        BeanPropertyMapMapper<UserSportMatch> mapper = new BeanPropertyMapMapper<>();
        List<UserSportMatch> result = list.stream().map(map -> mapper.map(UserSportMatch.class, map)).collect(Collectors.toList());
        return result;
    }

    @Override
    public Long getMatchEnterUserCount(Long matchId) {
        return userSportMatchDao.count((Specification<UserSportMatch>) (root, criteriaQuery, criteriaBuilder) -> {
            Predicate matchIdEq = criteriaBuilder.equal(root.get("sportMatch").get("id"), matchId);
            if (null != matchIdEq) {
                criteriaQuery.where(matchIdEq);
            }
            return null;
        });
    }

    @Override
    public boolean enterMatch(Long userId, SportMatch sportMatch) {
        try {
            accountService.consumeCoin(userId, AccountChannelEnum.SPORT_MATCH_ENTER.name(), sportMatch.getEnterCost().negate().longValue() * 1000,
                DateTime.now().toDate());
            UserSportMatch userSportMatch = new UserSportMatch();
            userSportMatch.setUserId(userId);
            userSportMatch.setCreateDate(DateTime.now().toDate());
            userSportMatch.setModifyDate(DateTime.now().toDate());
            userSportMatch.setSportMatch(sportMatch);
            userSportMatch.setEnterStatus(1);
            userSportMatchDao.save(userSportMatch);
        } catch (Exception e) {
            logger.error("user :{} enter match failed!");
            throw e;
        }
        return true;
    }

    @Override
    public List<UserSportMatch> getAll(Integer enterStatus) {
        return userSportMatchDao.findAll(
            (Specification<UserSportMatch>) (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("enterStatus"), enterStatus));
    }
}
