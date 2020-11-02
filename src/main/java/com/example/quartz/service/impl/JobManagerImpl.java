package com.example.quartz.service.impl;

import com.example.quartz.dto.ApiResult;
import com.example.quartz.service.JobManager;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Slf4j
public class JobManagerImpl implements JobManager {

    private final Scheduler scheduler;

    public JobManagerImpl(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    @Override
    public ApiResult pauseJob(String name, String group) {
        try {
            scheduler.pauseJob(JobKey.jobKey(name, group));
        } catch (SchedulerException e) {
            e.printStackTrace();
            log.error("pause job error");
            return ApiResult.prompt(String.format("pause job name=%s,group=%s fail",name,group));
        }
        return ApiResult.ok();
    }

    @Override
    public ApiResult resumeJob(String name, String group) {
        try {
            scheduler.resumeJob(JobKey.jobKey(name, group));
        } catch (SchedulerException e) {
            e.printStackTrace();
            log.error("resume job error");
            return ApiResult.prompt(String.format("resume job name=%s group=%s fail",name,group));
        }
        return ApiResult.ok();
    }

    @Override
    public ApiResult deleteJob(String name, String group) {
        try {
            scheduler.deleteJob(JobKey.jobKey(name, group));
        } catch (SchedulerException e) {
            e.printStackTrace();
            log.error("delete job error");
            return ApiResult.prompt(String.format("delete job name=%s group=%s fail",name,group));
        }
        return ApiResult.ok();
    }

    @Override
    public ApiResult addJob(String name, String group, String jobClassName, String triggerName, String triggerGroup, String cron) {
        Date date=null;
        try {
            Class cls = Class.forName(jobClassName);
            var jd = JobBuilder.newJob(cls)
                    .withIdentity(JobKey.jobKey(name, group))
                    .storeDurably()
                    .build();
            var trigger = TriggerBuilder.newTrigger()
                    .forJob(jd)
                    .withIdentity(TriggerKey.triggerKey(triggerName, triggerGroup))
                    .startNow()
                    .withSchedule(CronScheduleBuilder.cronSchedule(cron))
                    .build();
            date = scheduler.scheduleJob(jd,trigger);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("add job {} error",jobClassName);
            return ApiResult.prompt(String.format("add job name=%s group=%s jobClassName=%s fail",name,group,jobClassName));
        }
        return ApiResult.ok(date);
    }

    @Override
    public ApiResult updateJob(String name, String group, String cron) {
        Date date = null;
        try {
            var jobKey = scheduler.getTrigger(TriggerKey.triggerKey(name, group)).getJobKey();
            var tr = TriggerBuilder.newTrigger()
                    .withIdentity(name, group)
                    .forJob(jobKey)
                    .withSchedule(CronScheduleBuilder.cronSchedule(cron))
                    .startNow()
                    .build();
            date = scheduler.rescheduleJob(TriggerKey.triggerKey(name, group), tr);
        } catch (SchedulerException e) {
            e.printStackTrace();
            log.error("update error");
            return ApiResult.prompt("update job name="+name+", group="+group+",fail");
        }
        return ApiResult.ok(date);
    }
}
