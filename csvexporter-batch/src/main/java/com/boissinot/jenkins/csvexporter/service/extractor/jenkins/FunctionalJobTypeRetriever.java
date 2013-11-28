package com.boissinot.jenkins.csvexporter.service.extractor.jenkins;

import com.boissinot.jenkins.csvexporter.exception.ExportException;


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
        WAR("WAR", "BUILD"),
        WAR_RELEASE("WAR", "RELEASE"),
        TEMPLATE("GENERIC", "TEMPLATE"),
        PKG("ANY", "PACKAGING"),
        PLUGIN("JAVA", "PLUGIN"),
        OTHER("GENERIC", "NOT DETERMINED");

        private final String language;

        private final String type;

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
            throw new ExportException("You must give a non empty job name.");
        }

        if (jobName.isEmpty()) {
            throw new ExportException("You must give a non empty job name.");
        }

        if (jobName.contains("template") || jobName.contains("TEMPLATE")) {
            return JOB_TYPE.TEMPLATE;
        }

        if (jobName.contains("PKG") || jobName.contains("packaging")) {
            return JOB_TYPE.PKG;
        }

        if (jobName.contains("plugin") || jobName.contains("PLUGIN")) {
            return JOB_TYPE.PLUGIN;
        }

        if (jobName.contains("NAR_REPORT")) {
            return JOB_TYPE.CPP_REPORT;
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

        if (jobName.contains("WAR_RELEASE")) {
            return JOB_TYPE.WAR_RELEASE;
        }

        if (jobName.contains("WAR")) {
            return JOB_TYPE.WAR;
        }

        return JOB_TYPE.OTHER;
    }


}
