/*
 * Copyright (c) 2018. Tianyi AIDOC Company.Inc. All Rights Reserved.
 */

package com.tianyi.service.match.impl;

import com.tianyi.bo.match.SportMatch;
import com.tianyi.dao.match.SportMatchDao;
import com.tianyi.framework.bean.Page;
import com.tianyi.framework.bean.Pageable;
import com.tianyi.framework.service.impl.GenericServiceImpl;
import com.tianyi.framework.util.BeanPropertyMapMapper;
import com.tianyi.job.SportMatchJob;
import com.tianyi.service.match.SportMatchService;
import com.tianyi.utils.QuartzUtils;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.joda.time.DateTime;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * server
 *
 * @author Gray.Z
 * @date 2018/5/7 12:02.
 */
@Service
public class SportMatchServiceImpl extends GenericServiceImpl<SportMatch, Long> implements
    SportMatchService {

    @Autowired
    private SportMatchDao sportMatchDao;
    @Autowired
    private Scheduler scheduler;

    @Override
    public Page<SportMatch> findInProgressByPage(Pageable pageable) {
        org.springframework.data.domain.Page<Map<String, Object>> page = sportMatchDao.findInProgressByPage(getPageRequest(pageable));
        BeanPropertyMapMapper<SportMatch> mapper = new BeanPropertyMapMapper<>();
        List<SportMatch> result = page.getContent().stream().map(map ->
            mapper.map(SportMatch.class, map))
            .collect(Collectors.toList());
        return new Page<>(result, page.getTotalElements(), pageable);
    }


    @Override
    public Page<SportMatch> findAllByPage(Pageable pageable) {
        org.springframework.data.domain.Page<Map<String, Object>> page = sportMatchDao.findAllByPage(getPageRequest(pageable));
        BeanPropertyMapMapper<SportMatch> mapper = new BeanPropertyMapMapper<>();
        List<SportMatch> result = page.getContent().stream().map(map -> mapper.map(SportMatch.class, map)).collect(Collectors.toList());
        return new Page<>(result, page.getTotalElements(), pageable);
    }

    @Override
    public SportMatch saveSportMatch(SportMatch sportMatch) {
        SportMatch match = save(sportMatch);

        if (match != null) {
            //比赛结束下一天10点开始执行
            String cron = QuartzUtils.getCron(new DateTime(sportMatch.getEndDate()).withTimeAtStartOfDay().plusDays(1).withHourOfDay(10).toDate());
            QuartzUtils.addJob(scheduler, String.format("%s_%s", "sportMatchJob", sportMatch.getPeriod()), "sportMatchJob",
                String.format("%s_%s", "sportMatchJob", sportMatch.getPeriod()), "sportMatchJob", SportMatchJob.class, cron, sportMatch.getId());
        }
        return match;
    }
}
