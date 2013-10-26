package com.boissinot.jenkins.csvexporter.batch;

import java.util.List;

/**
 * @author Gregory Boissinot
 */
public interface JenkinsReaderExtensionPoint {

    public abstract List<String> buildURLs();

    public abstract String getJobName(String jobURL);

    public abstract String getConfigXML(String jobURL);
}
