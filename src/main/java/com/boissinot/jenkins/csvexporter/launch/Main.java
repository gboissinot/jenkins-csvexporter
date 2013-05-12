package com.boissinot.jenkins.csvexporter.launch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author Gregory Boissinot
 */
public class Main {

    public static void main(String[] args) throws JobInstanceAlreadyCompleteException, JobParametersInvalidException, JobRestartException, JobExecutionAlreadyRunningException {

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext-csv.xml");

        JobLauncher jobLauncher = applicationContext.getBean("jobLauncher", JobLauncher.class);
        Job job = applicationContext.getBean("extract-jenkins-job", Job.class);

        JobExecution jobExecution = jobLauncher.run(job, new JobParameters());
        System.out.println(jobExecution.getExitStatus());
        System.out.println(jobExecution.getFailureExceptions());
    }
}
