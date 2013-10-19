package com.boissinot.jenkins.csvexporter.batch;

import com.boissinot.jenkins.csvexporter.exception.ExportException;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Gregory Boissinot
 */
public class LocalJenkinsReader implements JenkinsReader {

    private final String folderPath;

    public LocalJenkinsReader(String folderPath) {
        if (folderPath == null) {
            throw new NullPointerException("A folder path must be provided.");
        }
        this.folderPath = folderPath;
    }

    public List<String> buildURLs() {
        List<String> urls = new ArrayList<String>();
        File configDir = new File(folderPath);
        if (!configDir.exists()) {
            throw new ExportException(String.format("The folder %s must exist.", folderPath));
        }
        File[] files = configDir.listFiles();
        for (File file : files) {
            urls.add(file.getAbsolutePath());
        }
        return urls;
    }

    public String getJobName(String jobURL) {
        File configFile = new File(jobURL);
        String configFileName = configFile.getName();
        if (configFileName.startsWith("config-")) {
            configFileName = configFileName.substring(7);
        }
        return configFileName;
    }

    public String getConfigXML(String jobURL) {
        try {
            return IOUtils.toString(new InputStreamReader(new FileInputStream(jobURL)));
        } catch (IOException ioe) {
            throw new ExportException(ioe);
        }
    }

}

