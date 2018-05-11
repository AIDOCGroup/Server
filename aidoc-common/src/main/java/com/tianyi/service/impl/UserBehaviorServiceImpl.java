/*
 * Copyright (c) 2018. Tianyi AIDOC Company.Inc. All Rights Reserved.
 */

package com.tianyi.service.impl;

import com.tianyi.bo.UserBehaviorLog;
import com.tianyi.dao.UserBehaviorLogDao;
import com.tianyi.framework.service.impl.GenericServiceImpl;
import com.tianyi.service.UserBehaviorLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户行为日志
 *
 * @author Gray.Z
 * @date 2018/4/11 16:51.
 */
@Service
public class UserBehaviorServiceImpl extends GenericServiceImpl<UserBehaviorLog,Long> implements
    UserBehaviorLogService {

    @Autowired
    private UserBehaviorLogDao userBehaviorLogDao;

    @Override
    public Long getActiveUserCount(String date) {
        return userBehaviorLogDao.getActiveUserCount(date);
    }

    @Override
    public UserBehaviorLog save(UserBehaviorLog userBehaviorLog) {
       return userBehaviorLogDao.save(userBehaviorLog);
    }
}
