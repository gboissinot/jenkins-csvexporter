package com.boissinot.jenkins.csvexporter.batch;

import com.boissinot.jenkins.csvexporter.batch.delegator.JobItemReaderDelegator;
import com.boissinot.jenkins.csvexporter.batch.delegator.JobItemReaderFolderDelegator;
import com.boissinot.jenkins.csvexporter.batch.delegator.JobItemReaderRemoteInstanceDelegator;
import com.boissinot.jenkins.csvexporter.domain.InputSBJobObj;
import com.boissinot.jenkins.csvexporter.exception.ExportException;
import com.boissinot.jenkins.csvexporter.service.extractor.jenkins.FunctionalJobTypeRetriever;
import com.boissinot.jenkins.csvexporter.service.http.HttpConnectionRetriever;
import com.boissinot.jenkins.csvexporter.service.http.ResourceContentFetcher;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Gregory Boissinot
 */
public class JobItemReader implements ItemReader<InputSBJobObj> {


    private ResourceContentFetcher  resourceContentFetcher;

    public JobItemReader(ResourceContentFetcher resourceContentFetcher) {
        this.resourceContentFetcher = resourceContentFetcher;
    }

    /* Computed */
    private List<String> urls = new ArrayList<String>();
    private JobItemReaderDelegator delegator;
    private Map<String, String> moduleMap;
    /* Managed */
    private boolean onFolder;
    private String folderPath;
    private String jenkinsURL;

    public void setOnFolder(String onFolder) {
        if (onFolder == null) {
            this.onFolder = false;
        }
        this.onFolder = Boolean.valueOf(onFolder);
    }

    public void setFolderPath(String folderPath) {
        this.folderPath = folderPath;
    }

    public void setJenkinsURL(String jenkinsURL) {
        this.jenkinsURL = jenkinsURL;
    }

    @BeforeStep
    private void beforeAnyRead(StepExecution stepExecution) {

        JobExecution jobExecution = stepExecution.getJobExecution();
        ExecutionContext executionContext = jobExecution.getExecutionContext();
        moduleMap = (Map<String, String>) executionContext.get("moduleMap");

        if (onFolder) {
            delegator = new JobItemReaderFolderDelegator(folderPath);
        } else {
            delegator = new JobItemReaderRemoteInstanceDelegator(jenkinsURL);
        }

        urls = delegator.buildURLs();
    }

    @Override
    public InputSBJobObj read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {

        try {
            if (urls.size() == 0) {
                return null;
            }
            String url = urls.remove(0);

            //TODO PUT IN A DEDICATED FILE
            //System.out.println("Read Job " + url);
            return readJob(url);

        } catch (Exception e) {
            throw new ExportException(e);
        }
    }

    private InputSBJobObj readJob(String jobURL) throws IOException {

        String jobName = delegator.getJobName(jobURL);
        FunctionalJobTypeRetriever jobTypeRetriever = new FunctionalJobTypeRetriever();
        FunctionalJobTypeRetriever.JOB_TYPE jobType = jobTypeRetriever.getJobType(jobName);


        String configXmlContent = resourceContentFetcher.getContent(jobURL+"/config.xml");

        InputSBJobObj inputSBJobObj =
                new InputSBJobObj(
                        jobName,
                        jobType.getType(),
                        jobType.getLanguage(),
                        configXmlContent, moduleMap);
                        //delegator.getConfigXML(jobURL), moduleMap);

        return inputSBJobObj;
    }

}
