package com.boissinot.jenkins.csvexporter.batch.aspect;

import com.boissinot.jenkins.csvexporter.batch.JobItemProcessor;
import com.boissinot.jenkins.csvexporter.domain.InputSBJobObj;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author Gregory Boissinot
 */
@Component
@Aspect
public class JobItemProcessorLogger {

    private final Logger logger = LoggerFactory.getLogger(JobItemProcessor.class);

    @Pointcut("within(com.boissinot.jenkins.csvexporter.batch.JobItemProcessor) && args(inputObj)")
    private void logger(InputSBJobObj inputObj) {
    }

    @Before(value = "logger(inputObj)")
    private void logCurrentJob(InputSBJobObj inputObj) {
        logger.info(String.format("Processing '%s'", inputObj.getJobName()));
    }
}
