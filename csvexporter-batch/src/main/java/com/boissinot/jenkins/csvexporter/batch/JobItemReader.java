package com.boissinot.jenkins.csvexporter.batch;

import com.boissinot.jenkins.csvexporter.domain.InputSBJobObj;
import com.boissinot.jenkins.csvexporter.service.extractor.jenkins.FunctionalJobTypeRetriever;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemReader;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Gregory Boissinot
 */
public class JobItemReader implements ItemReader<InputSBJobObj> {

    private final JenkinsReaderExtensionPoint jenkinsReader;

    /* Computed */
    private List<String> urls = new ArrayList<String>();
    private Map<String, Map<String, String>> contextMap;

    public JobItemReader(JenkinsReaderExtensionPoint jenkinsReader) {
        this.jenkinsReader = jenkinsReader;
    }

    @BeforeStep
    @SuppressWarnings({"unused", "unchecked"})
    private void beforeAnyRead(StepExecution stepExecution) {

        JobExecution jobExecution = stepExecution.getJobExecution();
        ExecutionContext executionContext = jobExecution.getExecutionContext();
        contextMap = (Map<String, Map<String, String>>) executionContext.get("mapContext");

        urls = jenkinsReader.buildURLs();
    }

    @Override
    public InputSBJobObj read() throws Exception {
        if (urls.size() == 0) {
            return null;
        }
        String url = urls.remove(0);
        return readJob(url);
    }

    private InputSBJobObj readJob(String jobURL) {
        String jobName = jenkinsReader.getJobName(jobURL);
        FunctionalJobTypeRetriever jobTypeRetriever = new FunctionalJobTypeRetriever();
        FunctionalJobTypeRetriever.JOB_TYPE jobType = jobTypeRetriever.getJobType(jobName);
        String configXmlContent = jenkinsReader.getConfigXML(jobURL + "/config.xml");
        return new InputSBJobObj(
                jobName,
                jobType.getType(),
                jobType.getLanguage(),
                configXmlContent, contextMap);
    }

}
