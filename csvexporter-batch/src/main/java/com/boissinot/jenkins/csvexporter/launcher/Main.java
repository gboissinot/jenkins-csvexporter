package com.boissinot.jenkins.csvexporter.launcher;

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

        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext();
        applicationContext.getEnvironment().setActiveProfiles("remote");
        applicationContext.setConfigLocation("applicationContext-csv.xml");
        applicationContext.refresh();

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
            parametersBuilder.addString("on.folder", "false");
            final String jenkinsURL = System.getProperty("jenkinsURL");
            if (jenkinsURL == null) {
                parametersBuilder.addString("jenkins.url", "http://calypso/jenkins/");
            } else {
                parametersBuilder.addString("jenkins.url", jenkinsURL);
            }
        } else {
            if (Boolean.valueOf(onFolder)) {
                parametersBuilder.addString("on.folder", onFolder);
                final String folderPath = System.getProperty("folderPath");
                parametersBuilder.addString("folder.path", folderPath);
            }
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
