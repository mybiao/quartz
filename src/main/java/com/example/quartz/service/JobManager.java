package com.example.quartz.service;

import com.example.quartz.dto.ApiResult;

public interface JobManager {

    /**
     * 暂停任务的执行
     * @param name job的名称
     * @param group job的组名称
     * @return res
     */
    ApiResult pauseJob(String name, String group);

    /**
     * 暂停后恢复任务的执行
     * @param name job name
     * @param group job group name
     * @return res
     */
    ApiResult resumeJob(String name, String group);

    /**
     * 删除任务
     * @param name
     * @param group
     * @return
     */
    ApiResult deleteJob(String name, String group);

    /**
     * 添加新的任务，job必须是已经定义的
     * @param name job的名称
     * @param group job的组名称
     * @param jobClassName job的类全称
     * @param triggerName 触发器的名称
     * @param triggerGroup 触发器的组名称
     * @param cron cron表达式
     * @return res
     */
    ApiResult addJob(String name, String group, String jobClassName,
                     String triggerName, String triggerGroup, String cron);

    /**
     * 更新任务
     * @param name
     * @param group
     * @param cron 新的cron表达式
     * @return
     */
    ApiResult updateJob(String name, String group, String cron);
}
