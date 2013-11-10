package com.boissinot.jenkins.csvexporter.domain;

import java.util.Map;

/**
 * @author Gregory Boissinot
 */
public class InputSBJobObj {

    private String jobName;
    private String functionalJobType;
    private String functionalJobLanguage;
    private String configXML;

    /**
     * Technical information
     */
    private Map<String, Map<String, String>> contextMap;
    private String emailFilePath;

    public InputSBJobObj(String jobName, String functionalJobType, String functionalJobLanguage, String configXML, Map<String, Map<String, String>> contextMap) {
        this.jobName = jobName;
        this.functionalJobType = functionalJobType;
        this.functionalJobLanguage = functionalJobLanguage;
        this.configXML = configXML;
        this.contextMap = contextMap;
    }

    public void setEmailFilePath(String emailFilePath) {
        this.emailFilePath = emailFilePath;
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

    public String getEmailFilePath() {
        return emailFilePath;
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
