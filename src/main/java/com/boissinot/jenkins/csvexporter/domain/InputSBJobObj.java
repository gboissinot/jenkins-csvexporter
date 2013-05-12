package com.boissinot.jenkins.csvexporter.domain;

/**
 * @author Gregory Boissinot
 */
public class InputSBJobObj {

    private String jobName;

    private String functionalJobType;

    private String functionalJobLanguage;

    private String configXML;

    public InputSBJobObj(String jobName, String functionalJobType, String functionalJobLanguage, String configXML) {
        this.jobName = jobName;
        this.functionalJobType = functionalJobType;
        this.functionalJobLanguage = functionalJobLanguage;
        this.configXML = configXML;
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
