package com.boissinot.jenkins.csvexporter.domain.jenkins.job;

/**
 * @author Gregory Boisisnot
 */
public class ConfigJob {

    private String jobName;

    private String svnURL;

    private String gitURL;

    private String cvsModule;

    private String cvsBranche;

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getSvnURL() {
        return svnURL;
    }

    public void setSvnURL(String svnURL) {
        this.svnURL = svnURL;
    }

    public String getGitURL() {
        return gitURL;
    }

    public void setGitURL(String gitURL) {
        this.gitURL = gitURL;
    }

    public String getCvsModule() {
        return cvsModule;
    }

    public void setCvsModule(String cvsModule) {
        this.cvsModule = cvsModule;
    }

    public String getCvsBranche() {
        return cvsBranche;
    }

    public void setCvsBranche(String cvsBranche) {
        this.cvsBranche = cvsBranche;
    }
}
