package com.boissinot.jenkins.csvexporter.domain;

import java.util.Map;

/**
 * @author Gregory Boissinot
 */
public class InputSBJobObj {

    private final String jobName;
    private final String functionalJobType;
    private final String functionalJobLanguage;
    private final String configXML;

    /**
     * Technical information
     */
    private final Map<String, Map<String, String>> contextMap;

    public InputSBJobObj(String jobName, String functionalJobType, String functionalJobLanguage, String configXML, Map<String, Map<String, String>> contextMap) {
        this.jobName = jobName;
        this.functionalJobType = functionalJobType;
        this.functionalJobLanguage = functionalJobLanguage;
        this.configXML = configXML;
        this.contextMap = contextMap;
    }

    public String getJobName() {
        return jobName;
    }

    public String getFunctionalJobType() {
        return functionalJobType;
    }

    public String getFunctionalJobLanguage() {
        return functionalJobLanguage;
    }

    public String getConfigXML() {
        return configXML;
    }

    public Map<String, Map<String, String>> getContextMap() {
        return contextMap;
    }

    @Override
    public String toString() {
        return "InputSBJobObj{" +
                "jobName='" + jobName + '\'' +
                ", functionalJobType='" + functionalJobType + '\'' +
                ", functionalJobLanguage='" + functionalJobLanguage + '\'' +
                ", configXML='" + configXML + '\'' +
                '}';
    }
}
