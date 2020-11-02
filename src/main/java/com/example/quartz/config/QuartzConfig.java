package com.example.quartz.config;

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



    static class StartDay implements Job{

        @Override
        public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
            log.info("job start_of_day run");
        }
    }

    static class StatisticJob implements Job{

        @Override
        public void execute(JobExecutionContext context) throws JobExecutionException {
            try {
                log.info("start execute task");
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("job2 run complete");
        }
    }
}
