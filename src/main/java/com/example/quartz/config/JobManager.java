package com.example.quartz.config;

import org.quartz.*;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JobManager {

    private final Scheduler scheduler;

    public JobManager(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    public boolean pauseJob(String name,String group){
        try {
            scheduler.pauseJob(JobKey.jobKey(name,group));
            return true;
        } catch (SchedulerException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean resumeJob(String name,String group){
        try {
            scheduler.resumeJob(JobKey.jobKey(name, group));
        } catch (SchedulerException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean deleteJob(String name,String group){
        try {
            scheduler.deleteJob(JobKey.jobKey(name, group));
            return true;
        } catch (SchedulerException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean addJob(String name,String group){
        try {
            var jd = scheduler.getJobDetail(JobKey.jobKey(name, group));
            if (jd==null) return false;
            scheduler.addJob(jd, true);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 更新job
     * @param name trigger名称
     * @param group trigger组名称
     */
    public Object updateJob(String name,String group,String cron){
        Date date = null;
        try {
            var jobKey = scheduler.getTrigger(TriggerKey.triggerKey(name, group)).getJobKey();
            var tr = TriggerBuilder.newTrigger()
                    .withIdentity(name, group)
                    .forJob(jobKey)
                    .withSchedule(CronScheduleBuilder.cronSchedule(cron))
                    .startNow()
                    .build();
            date = scheduler.rescheduleJob(TriggerKey.triggerKey(name,group),tr);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return date;
    }
}
