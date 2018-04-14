package com.tx.demo;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * Created by peter.
 */
public class HelloJob implements Job {
    public HelloJob() {
    }

    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.err.println("Hello!  HelloJob is executing.");
    }
}
