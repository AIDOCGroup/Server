/*
 * Copyright (c) 2018. Tianyi AIDOC Company.Inc. All Rights Reserved.
 */

package com.tianyi.service.mine;

import com.tianyi.bo.mine.ComputeRuleConfig;
import com.tianyi.framework.service.IGenericService;

/**
 * server
 *
 * @author Gray.Z
 * @date 2018/4/22 15:36.
 */
public interface ComputeRuleConfigService extends IGenericService<ComputeRuleConfig,Long> {

    ComputeRuleConfig save(ComputeRuleConfig computeRuleConfig);

}
