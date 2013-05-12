package com.boissinot.jenkins.csvexporter.batch;

import com.boissinot.jenkins.csvexporter.batch.delegator.JobItemReaderDelegator;
import com.boissinot.jenkins.csvexporter.batch.delegator.JobItemReaderFolderDelegator;
import com.boissinot.jenkins.csvexporter.batch.delegator.JobItemReaderRemoteInstanceDelegator;
import com.boissinot.jenkins.csvexporter.domain.InputSBJobObj;
import com.boissinot.jenkins.csvexporter.exception.ExportException;
import com.boissinot.jenkins.csvexporter.service.FunctionalJobTypeRetriever;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Gregory Boissinot
 */
public class JobItemReader implements ItemReader<InputSBJobObj> {


    /* Computed */
    private List<String> urls = new ArrayList<String>();
    private JobItemReaderDelegator delegator;

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
    private void beforeAnyRead() {
        if (onFolder) {
            delegator = new JobItemReaderFolderDelegator(folderPath);
        } else {
            delegator = new JobItemReaderRemoteInstanceDelegator(jenkinsURL);
        }

        urls = delegator.buildURLs();
    }

    public InputSBJobObj read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {

        try {
            if (urls.size() == 0) {
                return null;
            }
            String url = urls.remove(0);
            System.out.println("Read Job" + url);
            return readJob(url);

        } catch (Exception e) {
            throw new ExportException(e);
        }
    }

    private InputSBJobObj readJob(String jobURL) throws IOException {

        String jobName = delegator.getJobName(jobURL);
        FunctionalJobTypeRetriever jobTypeRetriever = new FunctionalJobTypeRetriever();
        FunctionalJobTypeRetriever.JOB_TYPE jobType = jobTypeRetriever.getJobType(jobName);

        InputSBJobObj inputSBJobObj =
                new InputSBJobObj(
                        jobName,
                        jobType.getType(),
                        jobType.getLanguage(),
                        delegator.getConfigXML(jobURL));

        return inputSBJobObj;
    }

}
