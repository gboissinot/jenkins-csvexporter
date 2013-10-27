package com.boissinot.jenkins.csvexporter.launcher;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;

/**
 * @author Gregory Boissinot
 */
public class Main {

    public static void main(String[] args) throws Exception {

        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext();
        applicationContext.getEnvironment().setActiveProfiles("remote");
        applicationContext.setConfigLocation("META-INF/spring/application-config.xml");
        applicationContext.refresh();

        JobLauncher jobLauncher = applicationContext.getBean("jobLauncher", JobLauncher.class);
        Job job = applicationContext.getBean("extract-jenkins-job", Job.class);

        JobExecution jobExecution = jobLauncher.run(job, getBatchJobParameters());
        System.out.println(jobExecution.getExitStatus());
        System.out.println(jobExecution.getFailureExceptions());
        applicationContext.close();
    }

    private static JobParameters getBatchJobParameters() throws IOException {
        JobParametersBuilder parametersBuilder = new JobParametersBuilder();

        final Properties properties = PropertiesLoaderUtils.loadAllProperties("default-batch-param.properties");
        for (Map.Entry<Object, Object> entry : properties.entrySet()) {
            String paramKey = (String) entry.getKey();
            final String systemPropertyValue = System.getProperty(paramKey);
            if (systemPropertyValue == null) {

                String paramValue = (String) entry.getValue();
                parametersBuilder.addString(paramKey, paramValue);
            } else {
                parametersBuilder.addString(paramKey, systemPropertyValue);
            }
        }
        return parametersBuilder.toJobParameters();
    }

}
