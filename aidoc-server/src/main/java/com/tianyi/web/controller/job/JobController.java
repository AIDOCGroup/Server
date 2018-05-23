/*
 * Copyright (c) 2018. Tianyi AIDOC Company.Inc. All Rights Reserved.
 */

package com.tianyi.web.controller.job;

import com.tianyi.entity.QuartzEntity;
import com.tianyi.framework.controller.BaseController;
import com.tianyi.service.JobService;
import com.tianyi.utils.QuartzUtils;
import com.tianyi.web.model.ResponseResult;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * server
 *
 * @author Gray.Z
 * @date 2018/5/5 15:30.
 */
@RestController
@RequestMapping("/job")
public class JobController extends BaseController {

    @Autowired
    private JobService jobService;

    @Autowired
    private Scheduler scheduler;

    @SuppressWarnings({"unchecked", "rawtypes"})
    @PostMapping("/add")
    public ResponseEntity save(QuartzEntity quartz) {
        logger.info("新增任务");
        try {
            //如果是修改  展示旧的 任务
            if (quartz.getOldJobGroup() != null) {
                QuartzUtils
                    .removeJob(scheduler, quartz.getOldJobName(), quartz.getOldJobGroup(), quartz.getTriggerName(), quartz.getTriggerGroupName());
            }
            Class cls = Class.forName(quartz.getJobClassName());
            cls.newInstance();
            QuartzUtils.addJob(scheduler, quartz.getOldJobName(), quartz.getOldJobGroup(), quartz.getTriggerName(), quartz.getTriggerGroupName(), cls,
                quartz.getCronExpression());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseResult.INTERNAL_SERVER_ERROR;
        }
        return ResponseResult.OK;
    }

    @PostMapping("/list")
    public ResponseEntity list(QuartzEntity quartz, Integer pageNo, Integer pageSize) {
        logger.info("任务列表");
        List<QuartzEntity> list = jobService.listQuartzEntity(quartz, pageNo, pageSize);
        return ResponseEntity.ok(list);
    }

    @PostMapping("/trigger")
    public ResponseEntity trigger(QuartzEntity quartz, HttpServletResponse response) {
        logger.info("触发任务");
        try {
            QuartzUtils.triggerJob(scheduler, quartz.getJobName(), quartz.getJobGroup());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseResult.INTERNAL_SERVER_ERROR;
        }
        return ResponseResult.OK;
    }

    @PostMapping("/pause")
    public ResponseEntity pause(QuartzEntity quartz, HttpServletResponse response) {
        logger.info("停止任务");
        try {
            QuartzUtils.pauseJob(scheduler, quartz.getJobName(), quartz.getJobGroup());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseResult.INTERNAL_SERVER_ERROR;
        }
        return ResponseResult.OK;
    }

    @PostMapping("/resume")
    public ResponseEntity resume(QuartzEntity quartz, HttpServletResponse response) {
        logger.info("恢复任务");
        try {
            QuartzUtils.resumeJob(scheduler, quartz.getJobName(), quartz.getJobGroup());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseResult.INTERNAL_SERVER_ERROR;
        }
        return ResponseResult.OK;
    }

    @PostMapping("/remove")
    public ResponseEntity remove(QuartzEntity quartz, HttpServletResponse response) {
        logger.info("移除任务");
        try {
            QuartzUtils.removeJob(scheduler, quartz.getJobName(), quartz.getJobGroup(), quartz.getTriggerName(), quartz.getTriggerGroupName());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseResult.INTERNAL_SERVER_ERROR;
        }
        return ResponseResult.OK;
    }
}
