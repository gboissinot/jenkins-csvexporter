package com.boissinot.jenkins.csvexporter.service.extractor.maven.pom;

/**
 * @author Gregory Boissinot
 */
public enum StrategyType {

    CVS("cvs"), SVN("svn"), GIT("git"), UNKNOWN("UNKNOWN");

    private final  String key;

    StrategyType(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
