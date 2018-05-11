/*
 * Copyright (c) 2018. Tianyi AIDOC Company.Inc. All Rights Reserved.
 */

package com.tianyi.dao.mine;

import com.tianyi.bo.mine.ComputeRuleConfig;
import com.tianyi.framework.dao.IGenericRepository;
import org.springframework.stereotype.Repository;

/**
 * server
 *
 * @author Gray.Z
 * @date 2018/4/21 13:56.
 */
@Repository
public interface ComputeRuleConfigDao extends IGenericRepository<ComputeRuleConfig, Long> {

    ComputeRuleConfig findByType(Integer type);
}
