package com.tx.demo;

import org.junit.Test;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.KeyMatcher;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * Created by peter.
 */
public class HelloJobTest {

    /*
     * 简单作业调度
     */
    @Test
    public void execute() throws Exception {
        SchedulerFactory schedFact = new StdSchedulerFactory();
        Scheduler sched = schedFact.getScheduler();
        sched.start();

        // define the job and tie it to HelloJob class
        JobDetail job = newJob(HelloJob.class)
                .withIdentity("myJob", "group1")
                .usingJobData("jobSays", "Hello World!")
                .usingJobData("myFloatValue", 3.141f)
                .build();

        // Trigger the job to run now, and then every 40 seconds
        Trigger trigger = newTrigger()
                .withIdentity("myTrigger", "group1")
                .startNow()
                .withSchedule(simpleSchedule()
                        .withIntervalInSeconds(2)
                        .repeatForever())
                .build();

        //Tell quartz to schedule the job using our trigger
        sched.scheduleJob(job, trigger);

        Thread.currentThread().join(10 * 1000);
    }

    /*
     * 带监听器的作业调度
     */
    @Test
    public void execute2() throws Exception {
        SchedulerFactory schedFact = new StdSchedulerFactory();
        Scheduler sched = schedFact.getScheduler();


        JobKey jobKey = new JobKey("myJob", "group1");

        // define the job and tie it to HelloJob class
        JobDetail job = newJob(HelloJob.class)
                .withIdentity(jobKey)
                .usingJobData("jobSays", "Hello World!")
                .usingJobData("myFloatValue", 3.141f)
                .build();


        // Trigger the job to run now, and then every 40 seconds
        Trigger trigger = newTrigger()
                .withIdentity("myTrigger", "group1")
                .startNow()
                .withSchedule(
                        cronSchedule("0/5 * * * * ?"))
                .build();

        //Listener attached to jobKey
        sched.getListenerManager().addJobListener(new HelloJobListener(), KeyMatcher.keyEquals(jobKey));

        //Tell quartz to schedule the job using our trigger
        sched.start();
        sched.scheduleJob(job, trigger);

        Thread.currentThread().join(10 * 1000);
    }

}