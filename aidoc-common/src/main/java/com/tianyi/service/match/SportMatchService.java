/*
 * Copyright (c) 2018. Tianyi AIDOC Company.Inc. All Rights Reserved.
 */

package com.tianyi.service.match;

import com.tianyi.bo.match.SportMatch;
import com.tianyi.framework.bean.Page;
import com.tianyi.framework.bean.Pageable;
import com.tianyi.framework.service.IGenericService;

/**
 * 赛事
 *
 * @author Gray.Z
 * @date 2018/5/7 12:00.
 */
public interface SportMatchService extends IGenericService<SportMatch, Long> {

    /**
     * 查询当前正在进行的赛事-已经开始的赛事
     *
     * @param pageable 分页信息
     * @return 分页结果
     */
    Page<SportMatch> findInProgressByPage(Pageable pageable);

    /**
     * 查询所哟赛事-未开始和已经开始、已结束
     *
     * @param pageable 分页信息
     * @return 分页结果
     */
    Page<SportMatch> findAllByPage(Pageable pageable);

    /**
     * 保存赛事
     *
     * @param sportMatch 赛事对象
     * @return 赛事保存结果
     */
    SportMatch saveSportMatch(SportMatch sportMatch);

}
