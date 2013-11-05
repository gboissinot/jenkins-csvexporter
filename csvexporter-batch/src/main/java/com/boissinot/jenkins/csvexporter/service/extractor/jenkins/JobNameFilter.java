package com.boissinot.jenkins.csvexporter.service.extractor.jenkins;

import org.springframework.integration.annotation.Filter;

/**
 * @author Gregory Boissinot
 */
public class JobNameFilter {

    @Filter
    @SuppressWarnings("unused")
    public boolean filterTemplateJob(String jobName) {
        if (jobName == null) {
            return false;
        }
        return jobName.contains("template") ? false : true;
    }
}
