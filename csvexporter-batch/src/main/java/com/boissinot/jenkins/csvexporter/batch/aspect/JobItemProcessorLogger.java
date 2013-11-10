package com.boissinot.jenkins.csvexporter.batch.aspect;

import com.boissinot.jenkins.csvexporter.batch.integration.JobItemProcessorServiceAdapter;
import com.boissinot.jenkins.csvexporter.domain.InputSBJobObj;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.Message;
import org.springframework.stereotype.Component;

/**
 * @author Gregory Boissinot
 */
@Component
@Aspect
public class JobItemProcessorLogger {

    private final Logger logger = LoggerFactory.getLogger(JobItemProcessorServiceAdapter.class);

    @Pointcut("within(com.boissinot.jenkins.csvexporter.batch.integration.JobItemProcessorServiceAdapter) && args(inputObjMessage)")
    private void loggerPointcut(Message<InputSBJobObj> inputObjMessage) {
    }

    @Before(value = "loggerPointcut(inputObjMessage)")
    private void logCurrentJob(Message<InputSBJobObj> inputObjMessage) {
        InputSBJobObj inputSBJobObj = inputObjMessage.getPayload();
        logger.info(String.format("Processing '%s'", inputSBJobObj.getJobName()));
    }
}
