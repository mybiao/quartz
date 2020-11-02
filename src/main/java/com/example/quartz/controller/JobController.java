package com.example.quartz.controller;

import com.example.quartz.dto.ApiResult;
import com.example.quartz.service.JobManager;
import org.springframework.web.bind.annotation.*;

@RestController
public class JobController {

    private final JobManager jobManager;

    public JobController(JobManager jobManager) {
        this.jobManager = jobManager;
    }

    @DeleteMapping("/job/{name}/{group}")
    public ApiResult deleteJob(@PathVariable String name, @PathVariable String group){
        return jobManager.deleteJob(name, group);
    }

    @PostMapping("/job/{name}/{group}")
    public ApiResult addJob(@PathVariable String name,@PathVariable String group, String jobClassName,
                          String triggerName,String triggerGroup,String cron){
        return jobManager.addJob(name, group,jobClassName,triggerName,triggerGroup,cron);
    }

    @PutMapping("/job/{name}/{group}/pause")
    public ApiResult pauseJob(@PathVariable String name,@PathVariable String group){
        return jobManager.pauseJob(name, group);
    }

    @PutMapping("/job/{name}/{group}/resume")
    public ApiResult resumeJob(@PathVariable String name,@PathVariable String group){
        return jobManager.resumeJob(name, group);
    }

    @PutMapping("/job/{name}/{group}")
    public ApiResult updateJob(@PathVariable String name,@PathVariable String group,String cron){
        return jobManager.updateJob(name, group, cron);
    }
}
