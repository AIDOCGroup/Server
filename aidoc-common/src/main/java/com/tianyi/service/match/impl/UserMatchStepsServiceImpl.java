/*
 * Copyright (c) 2018. Tianyi AIDOC Company.Inc. All Rights Reserved.
 */

package com.tianyi.service.match.impl;

import com.google.common.collect.Lists;
import com.tianyi.bo.match.UserMatchSteps;
import com.tianyi.dao.match.UserMatchStepsDao;
import com.tianyi.framework.service.impl.GenericServiceImpl;
import com.tianyi.service.match.UserMatchStepsService;
import java.util.List;
import javax.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

/**
 * server
 *
 * @author Gray.Z
 * @date 2018/5/9 11:29.
 */
@Service
public class UserMatchStepsServiceImpl extends GenericServiceImpl<UserMatchSteps, Long> implements
    UserMatchStepsService {

    @Autowired
    private UserMatchStepsDao userMatchStepsDao;


    @Override
    public List<UserMatchSteps> getUserMatchSteps(Long userId, Long matchId) {
        return userMatchStepsDao.findAll((Specification<UserMatchSteps>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = Lists.newArrayList();
            Predicate userIdEq = criteriaBuilder.equal(root.<Long>get("userId"), userId);
            Predicate matchIdEq = criteriaBuilder.equal(root.<Long>get("matchId"), matchId);
            if (null != userIdEq) {
                predicates.add(userIdEq);
            }
            if (null != matchIdEq) {
                predicates.add(matchIdEq);
            }
            return criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]))
                .getRestriction();
        });
    }
}
