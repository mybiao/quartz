package com.example.quartz.controller;

import com.example.quartz.config.JobManager;
import org.springframework.web.bind.annotation.*;

@RestController
public class JobController {

    private final JobManager jobManager;

    public JobController(JobManager jobManager) {
        this.jobManager = jobManager;
    }

    @DeleteMapping("/job/{name}/{group}")
    public boolean deleteJob(@PathVariable String name,@PathVariable String group){
        return jobManager.deleteJob(name, group);
    }

    @PostMapping("/job/{name}/{group}")
    public boolean addJob(@PathVariable String name,@PathVariable String group){
        return jobManager.addJob(name, group);
    }

    @PutMapping("/job/{name}/{group}/pause")
    public boolean pauseJob(@PathVariable String name,@PathVariable String group){
        return jobManager.pauseJob(name, group);
    }

    @PutMapping("/job/{name}/{group}/resume")
    public boolean resumeJob(@PathVariable String name,@PathVariable String group){
        return jobManager.resumeJob(name, group);
    }

    @PutMapping("/job/{name}/{group}")
    public Object updateJob(@PathVariable String name,@PathVariable String group,String cron){
        return jobManager.updateJob(name, group, cron);
    }
}
