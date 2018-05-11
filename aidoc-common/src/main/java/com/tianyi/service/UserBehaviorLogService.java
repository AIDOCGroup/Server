/*
 * Copyright (c) 2018. Tianyi AIDOC Company.Inc. All Rights Reserved.
 */

package com.tianyi.service;

import com.tianyi.bo.UserBehaviorLog;
import com.tianyi.framework.service.IGenericService;

/**
 * 用户行为日志Service
 *
 * @author Gray.Z
 * @date 2018/4/11 16:51.
 */
public interface UserBehaviorLogService extends IGenericService<UserBehaviorLog,Long> {

    /**
     * 获取指定日期的活跃用户数量
     * @param date 日期 YYYY-MM-DD
     * @return 活跃用户数量
     */
    Long getActiveUserCount(String date);
}
