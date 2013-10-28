package com.boissinot.jenkins.csvexporter.batch.aspect;

import com.boissinot.jenkins.csvexporter.batch.JobItemProcessor;
import com.boissinot.jenkins.csvexporter.domain.InputSBJobObj;
import org.aspectj.lang.JoinPoint;
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

    @Pointcut("within(com.boissinot.jenkins.csvexporter.batch.JobItemProcessor) && args(com.boissinot.jenkins.csvexporter.domain.InputSBJobObj)")
    private void logger() {
    }

    @Before(value = "logger()")
    private void logCurrentJob(JoinPoint joinPoint) {
        InputSBJobObj inputSBJobObj = (InputSBJobObj) joinPoint.getArgs()[0];
        logger.info(String.format("Processing '%s'", inputSBJobObj.getJobName()));
    }
}
