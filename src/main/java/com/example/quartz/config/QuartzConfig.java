package com.example.quartz.config;

import com.example.quartz.schedule.jobs.StartDay;
import com.example.quartz.schedule.jobs.StatisticJob;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QuartzConfig {

    private static final Logger log= LoggerFactory.getLogger(QuartzConfig.class);

    @Bean
    public JobDetail jobDetail(){
        return JobBuilder.newJob(StartDay.class)
                .withIdentity("test1","test-group")
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger trigger(){
        return TriggerBuilder.newTrigger()
                .forJob(jobDetail())
                .withIdentity("test1","test-group")
                .startNow()
                .withSchedule(CronScheduleBuilder.cronSchedule("0/10 * * * * ? "))
                .build();
    }

    @Bean
    public JobDetail job2(){
        return JobBuilder.newJob(StatisticJob.class)
                .withIdentity("statistic_order","statistic")
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger trigger2(){
        return TriggerBuilder.newTrigger()
                .withIdentity("statistic_order","statistic")
                .forJob(job2())
                .startNow()
                .withSchedule(CronScheduleBuilder.cronSchedule("0/18 * * * * ? "))
                .build();
    }

}
