package com.boissinot.jenkins.csvexporter.batch.delegator;

import java.util.List;

/**
 * @author Gregory Boissinot
 */
public interface JobItemReaderDelegator {

    public abstract List<String> buildURLs();

    public abstract String getJobName(String jobURL);

    public abstract String getConfigXML(String jobURL);
}
