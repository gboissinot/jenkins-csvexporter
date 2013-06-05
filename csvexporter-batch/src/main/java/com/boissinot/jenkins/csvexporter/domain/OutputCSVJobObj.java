package com.boissinot.jenkins.csvexporter.domain;

import com.boissinot.jenkins.csvexporter.apt.ExportElement;
import com.boissinot.jenkins.csvexporter.apt.ExportElementType;

/**
 * @author Gregory Boissinot
 */
@ExportElementType("OutputCSVJobObj")
public class OutputCSVJobObj {

    @ExportElement(order = 1, label = "JOB_NAME")
    private String name;

    @ExportElement(order = 2, label = "DESCRIPTION")
    private String desc;

    @ExportElement(order = 3, label = "DISABLED")
    private boolean disabled;

    @ExportElement(order = 4, label = "JENKINS_TYPE")
    private String jenkinsType;

    @ExportElement(order = 5, label = "FUNCTIONAL_JOB_TYPE")
    private String functionalJobType;

    @ExportElement(order = 6, label = "FUNCTIONAL_JOB_LANGUAGE")
    private String functionalJobLanguage;

    @ExportElement(order = 7, label = "CVS_ROOT")
    private String cvsRoot;

    @ExportElement(order = 8, label = "CVS_MODULE")
    private String cvsModule;

    @ExportElement(order = 9, label = "SVN_URL")
    private String svnURL;

    @ExportElement(order = 10, label = "GIT_URL")
    private String gitURL;

    @ExportElement(order = 11, label = "TRIGGER")
    private String trigger;

    @ExportElement(order = 12, label = "COMMANDS")
    private String buildSteps;

    public OutputCSVJobObj() {
    }

    public OutputCSVJobObj(Builder builder) {
        this.name = builder.name;
        this.desc = builder.desc;
        this.disabled = builder.disabled;
        this.jenkinsType = builder.jenkinsType;
        this.functionalJobType = builder.functionalJobType;
        this.functionalJobLanguage = builder.functionalJobLanguage;
        this.cvsRoot = builder.cvsRoot;
        this.cvsModule = builder.cvsModule;
        this.svnURL = builder.svnURL;
        this.gitURL = builder.gitURL;
        this.trigger = builder.trigger;
        this.buildSteps = builder.buildSteps;
    }

    public static class Builder {

        private String name;
        private String desc;
        private boolean disabled;
        private String jenkinsType;
        private String functionalJobType;
        private String functionalJobLanguage;
        private String cvsRoot;
        private String cvsModule;
        private String svnURL;
        private String gitURL;
        private String trigger;
        private String buildSteps;

        public Builder() {
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder desc(String desc) {
            this.desc = desc;
            return this;
        }

        public Builder disabled(boolean disabled) {
            this.disabled = disabled;
            return this;
        }

        public Builder jenkinsType(String jenkinsType) {
            this.jenkinsType = jenkinsType;
            return this;
        }

        public Builder functionalJobType(String functionalJobType) {
            this.functionalJobType = functionalJobType;
            return this;
        }

        public Builder functionalJobLanguage(String functionalJobLanguage) {
            this.functionalJobLanguage = functionalJobLanguage;
            return this;
        }

        public Builder cvsRoot(String cvsRoot) {
            this.cvsRoot = cvsRoot;
            return this;
        }

        public Builder cvsModule(String cvsModule) {
            this.cvsModule = cvsModule;
            return this;
        }

        public Builder svnURL(String svnURL) {
            this.svnURL = svnURL;
            return this;
        }

        public Builder gitURL(String gitURL) {
            this.gitURL = gitURL;
            return this;
        }

        public Builder trigger(String trigger) {
            this.trigger = trigger;
            return this;
        }

        public Builder buildSteps(String buildSteps) {
            this.buildSteps = buildSteps;
            return this;
        }

        public OutputCSVJobObj build() {
            return new OutputCSVJobObj(this);
        }

    }


    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public String getJenkinsType() {
        return jenkinsType;
    }

    public String getFunctionalJobType() {
        return functionalJobType;
    }

    public String getFunctionalJobLanguage() {
        return functionalJobLanguage;
    }

    public String getCvsRoot() {
        return cvsRoot;
    }

    public String getCvsModule() {
        return cvsModule;
    }

    public String getSvnURL() {
        return svnURL;
    }

    public String getGitURL() {
        return gitURL;
    }

    public String getTrigger() {
        return trigger;
    }

    public String getBuildSteps() {
        return buildSteps;
    }


}
