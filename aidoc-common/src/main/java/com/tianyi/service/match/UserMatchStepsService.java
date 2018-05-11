/*
 * Copyright (c) 2018. Tianyi AIDOC Company.Inc. All Rights Reserved.
 */

package com.tianyi.service.match;

import com.tianyi.bo.match.UserMatchSteps;
import com.tianyi.framework.service.IGenericService;
import java.util.List;

/**
 * 用户赛事步数
 *
 * @author Gray.Z
 * @date 2018/5/9 11:26.
 */
public interface UserMatchStepsService extends IGenericService<UserMatchSteps, Long> {

    /**
     * 用户赛事步数
     *
     * @param userId 用户ID
     * @param matchId 赛事ID
     * @return 用户赛事步数结果
     */
    List<UserMatchSteps> getUserMatchSteps(Long userId, Long matchId);
}
