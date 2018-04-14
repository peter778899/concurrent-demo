package com.tx.demo;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * Created by peter.
 */
public class Main {
    public static void main(String[] args) throws SchedulerException {
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
                        .withIntervalInSeconds(40)
                        .repeatForever())
                .build();

        //Tell quartz to schedule the job using our trigger
        sched.scheduleJob(job, trigger);
    }
}
