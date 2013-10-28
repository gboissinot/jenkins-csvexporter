package com.boissinot.jenkins.csvexporter.launcher;

import com.boissinot.jenkins.csvexporter.exception.ExportException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.util.Calendar;
import java.util.Map;
import java.util.Properties;

/**
 * @author Gregory Boissinot
 */
public class StartGenerator {

    private final Logger logger = LoggerFactory.getLogger(StartGenerator.class);

    public void generate() throws ExportException {
        try {
            long starttime = System.currentTimeMillis();

            logger.info("Generating a CSV reporting file from a Jenkins instance.");
            logger.info("Starting...");

            ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext();
            applicationContext.getEnvironment().setActiveProfiles("remote");
            applicationContext.setConfigLocation("META-INF/spring/application-config.xml");
            applicationContext.refresh();

            JobLauncher jobLauncher = applicationContext.getBean("jobLauncher", JobLauncher.class);
            Job job = applicationContext.getBean("extract-jenkins-job", Job.class);

            JobExecution jobExecution = jobLauncher.run(job, getBatchJobParameters());
            logger.info(String.valueOf(jobExecution.getExitStatus()));
            logger.info(String.valueOf(jobExecution.getFailureExceptions()));
            logger.info("Stopping...");
            applicationContext.close();

            long endtime = System.currentTimeMillis();
            final Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(endtime - starttime);
            logger.info(String.format("Took %s m and %s s.", calendar.get(Calendar.MINUTE), calendar.get(Calendar.SECOND)));

        } catch (IOException ioe) {
            throw new ExportException(ioe);
        } catch (JobParametersInvalidException e) {
            throw new ExportException(e);
        } catch (JobExecutionAlreadyRunningException e) {
            throw new ExportException(e);
        } catch (JobRestartException e) {
            throw new ExportException(e);
        } catch (JobInstanceAlreadyCompleteException e) {
            throw new ExportException(e);
        }
    }

    private JobParameters getBatchJobParameters() throws IOException {
        JobParametersBuilder parametersBuilder = new JobParametersBuilder();

        final Properties properties = PropertiesLoaderUtils.loadAllProperties("default-batch-params.properties");
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
