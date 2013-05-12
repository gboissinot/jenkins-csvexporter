package com.boissinot.jenkins.csvexporter.batch;

import com.boissinot.jenkins.csvexporter.domain.InputSBJobObj;
import com.boissinot.jenkins.csvexporter.service.FunctionalJobTypeRetriever;
import org.apache.commons.io.IOUtils;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Gregory Boissinot
 */
public class JobItemReader implements ItemReader<InputSBJobObj> {


    private List<String> urls = new ArrayList<String>();

    @BeforeStep
    private void beforeStepLocal() {
        File configDir = new File("/Users/gregory/Dev/configs/");
        File[] files = configDir.listFiles();
        for (File file : files) {
            urls.add(file.getAbsolutePath());
        }
    }

    public InputSBJobObj read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {

        try {
            if (urls.size() == 0) {
                return null;
            }
            String url = urls.remove(0);
            //System.out.println("Read " + url);
            return readJob(url);
        } catch (Exception e) {
            //TODO
            e.printStackTrace();
        }

        return null;
    }

    private InputSBJobObj readJob(String url) throws IOException {

        File configFile = new File(url);
        String jobName = configFile.getName().substring(configFile.getName().indexOf("config-") + 7);

        FunctionalJobTypeRetriever jobTypeRetriever = new FunctionalJobTypeRetriever();
        FunctionalJobTypeRetriever.JOB_TYPE jobType = jobTypeRetriever.getJobType(jobName);

        InputSBJobObj inputSBJobObj =
                new InputSBJobObj(
                        jobName,
                        jobType.getType(),
                        jobType.getLanguage(),
                        testGetConfigXMLContent(jobName, url));

        return inputSBJobObj;
    }

    private String testGetConfigXMLContent(String jobName, String jobURL) throws IOException {
        return IOUtils.toString(new InputStreamReader(new FileInputStream(jobURL)));
    }

}
