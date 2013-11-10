package com.boissinot.jenkins.csvexporter.service.extractor.jenkins;

import org.springframework.integration.annotation.Filter;

/**
 * @author Gregory Boissinot
 */
public class JobNameFilter {

    @Filter
    @SuppressWarnings("unused")
    public boolean removeTemplateJob(String jobName) {
        return jobName != null && !jobName.contains("template");
    }
}
