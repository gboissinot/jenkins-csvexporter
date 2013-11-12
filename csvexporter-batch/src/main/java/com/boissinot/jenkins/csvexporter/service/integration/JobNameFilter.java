package com.boissinot.jenkins.csvexporter.service.integration;

import org.springframework.integration.annotation.Filter;

/**
 * @author Gregory Boissinot
 */
public class JobNameFilter {

    @Filter
    @SuppressWarnings("unused")
    public boolean keepNonTemplateJob(String jobName) {
        return jobName != null && !jobName.contains("template");
    }
}
