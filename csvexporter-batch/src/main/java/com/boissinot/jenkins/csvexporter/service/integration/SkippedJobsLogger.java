package com.boissinot.jenkins.csvexporter.service.integration;

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
    public void log(@Header("JOB_NAME") String skippedJobName) {
        logger.info(String.format("Skipping '%s'", skippedJobName));
    }
}
