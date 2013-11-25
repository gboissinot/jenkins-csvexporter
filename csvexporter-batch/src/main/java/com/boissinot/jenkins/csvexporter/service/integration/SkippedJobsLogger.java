package com.boissinot.jenkins.csvexporter.service.integration;

import com.boissinot.jenkins.csvexporter.domain.JobMessageHeaders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.annotation.Header;
import org.springframework.integration.annotation.ServiceActivator;

/**
 * @author Gregory Boissinot
 */
public class SkippedJobsLogger {

    private static Logger logger = LoggerFactory.getLogger(SkippedJobsLogger.class);

    @ServiceActivator
    public void log(@Header(JobMessageHeaders.HEADER_JOB_NAME) String skippedJobName) {
        logger.info(String.format("Skipping '%s'", skippedJobName));
    }
}
