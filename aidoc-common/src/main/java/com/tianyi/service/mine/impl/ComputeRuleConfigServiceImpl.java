/*
 * Copyright (c) 2018. Tianyi AIDOC Company.Inc. All Rights Reserved.
 */

package com.tianyi.service.mine.impl;

import com.tianyi.bo.mine.ComputeRuleConfig;
import com.tianyi.dao.mine.ComputeRuleConfigDao;
import com.tianyi.framework.service.impl.GenericServiceImpl;
import com.tianyi.service.mine.ComputeRuleConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * server
 *
 * @author Gray.Z
 * @date 2018/4/22 15:37.
 */
@Service
@Transactional(value = "jpaTransactionManager")
public class ComputeRuleConfigServiceImpl extends GenericServiceImpl<ComputeRuleConfig, Long> implements
    ComputeRuleConfigService {

    @Autowired
    private ComputeRuleConfigDao computeRuleConfigDao;

    public ComputeRuleConfig save(ComputeRuleConfig computeRuleConfig) {
        return computeRuleConfigDao.save(computeRuleConfig);
    }

}
