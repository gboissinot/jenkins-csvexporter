package com.boissinot.jenkins.csvexporter.domain.jenkins.job;

/**
 * @author Gregory Boisisnot
 */
public class ConfigJob {

    private String name;
    private String desc;
    private boolean disabled;
    private String jenkinsType;
    private String functionalJobType;
    private String functionalJobLanguage;
    private String cvsRoot;
    private String cvsModule;
    private String cvsBranche;
    private String svnURL;
    private String gitURL;
    private String trigger;
    private String buildSteps;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public String getJenkinsType() {
        return jenkinsType;
    }

    public void setJenkinsType(String jenkinsType) {
        this.jenkinsType = jenkinsType;
    }

    public String getFunctionalJobType() {
        return functionalJobType;
    }

    public void setFunctionalJobType(String functionalJobType) {
        this.functionalJobType = functionalJobType;
    }

    public String getFunctionalJobLanguage() {
        return functionalJobLanguage;
    }

    public void setFunctionalJobLanguage(String functionalJobLanguage) {
        this.functionalJobLanguage = functionalJobLanguage;
    }

    public String getCvsRoot() {
        return cvsRoot;
    }

    public void setCvsRoot(String cvsRoot) {
        this.cvsRoot = cvsRoot;
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

    public String getTrigger() {
        return trigger;
    }

    public void setTrigger(String trigger) {
        this.trigger = trigger;
    }

    public String getBuildSteps() {
        return buildSteps;
    }

    public void setBuildSteps(String buildSteps) {
        this.buildSteps = buildSteps;
    }
}
