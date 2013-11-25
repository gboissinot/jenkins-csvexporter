package com.boissinot.jenkins.csvexporter.service.integration;

import com.boissinot.jenkins.csvexporter.service.exception.ItemFilteredException;
import org.springframework.integration.annotation.Filter;

/**
 * @author Gregory Boissinot
 */
public class JobNameFilter {

    @Filter
    @SuppressWarnings("unused")
    public boolean keepNonTemplateJob(String jobName) {
        boolean keepJob = jobName != null && !jobName.contains("template");
        if (keepJob) {
            return true;
        }

        throw new ItemFilteredException(String.format("Filtering '%s'. It is a template job.", jobName));
    }
}
