package com.boissinot.jenkins.csvexporter.launch;

import org.springframework.batch.core.*;
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

        JobExecution jobExecution = jobLauncher.run(job, getJobParameters());
        System.out.println(jobExecution.getExitStatus());
        System.out.println(jobExecution.getFailureExceptions());
    }

    private static JobParameters getJobParameters() {
        JobParametersBuilder parametersBuilder = new JobParametersBuilder();

        final String onFolder = System.getProperty("onFolder");
        if (onFolder == null) {
            parametersBuilder.addString("on.folder", "true");
        } else {
            parametersBuilder.addString("on.folder", onFolder);
        }

        final String jenkinsURL = System.getProperty("jenkinsURL");
        if (jenkinsURL == null) {
            parametersBuilder.addString("jenkins.url", "http://calypso/jenkins/");
        } else {
            parametersBuilder.addString("jenkins.url", jenkinsURL);
        }

        final String folderPath = System.getProperty("folderPath");
        if (folderPath == null) {
            parametersBuilder.addString("folder.path", "/Users/gregory/Dev/configs/");
        } else {
            parametersBuilder.addString("folder.path", folderPath);
        }

        final String exportJobsPath = System.getProperty("exportJobsPath");
        if (exportJobsPath == null) {
            parametersBuilder.addString("exportcsv.filepath", "export-jobs.csv");
        } else {
            parametersBuilder.addString("exportcsv.filepath", exportJobsPath);
        }

        return parametersBuilder.toJobParameters();
    }
}
