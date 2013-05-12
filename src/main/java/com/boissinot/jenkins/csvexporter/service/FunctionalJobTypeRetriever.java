package com.boissinot.jenkins.csvexporter.service;

/**
 * @author Gregory Boissinot
 */
public class FunctionalJobTypeRetriever {

    public static enum JOB_TYPE {

        CPP("CPP", "BUILD"),
        CPP_RELEASE("CPP", "RELEASE"),
        CPP_REPORT("CPP", "REPORT"),
        JAR("JAR", "BUILD"),
        JAR_RELEASE("JAR", "RELEASE"),
        TEMPLATE("GENERIC", "TEMPLATE"),
        PKG("ANY", "PACKAGING"),
        MAVEN_PLUGIN("JAVA", "PLUGIN"),
        OTHER("GENERIC", "NOT DETERMINED");

        private String language;

        private String type;

        private JOB_TYPE(String language, String type) {
            this.language = language;
            this.type = type;
        }

        public String getLanguage() {
            return language;
        }

        public String getType() {
            return type;
        }
    }

    public JOB_TYPE getJobType(String jobName) {
        if (jobName == null) {
            return null;
        }

        if (jobName.contains("template")) {
            return JOB_TYPE.TEMPLATE;
        }

        if (jobName.contains("PKG") || jobName.contains("packaging")) {
            return JOB_TYPE.PKG;
        }

        if (jobName.contains("plugin")) {
            return JOB_TYPE.MAVEN_PLUGIN;
        }

        if (jobName.contains("NAR_RELEASE_REPORT")) {
            return JOB_TYPE.CPP_REPORT;
        }

        if (jobName.contains("NAR_RELEASE")) {
            return JOB_TYPE.CPP_RELEASE;
        }

        if (jobName.contains("NAR")) {
            return JOB_TYPE.CPP;
        }

        if (jobName.contains("JAR_RELEASE")) {
            return JOB_TYPE.JAR_RELEASE;
        }

        if (jobName.contains("JAR")) {
            return JOB_TYPE.JAR;
        }

        return JOB_TYPE.OTHER;
    }


}
