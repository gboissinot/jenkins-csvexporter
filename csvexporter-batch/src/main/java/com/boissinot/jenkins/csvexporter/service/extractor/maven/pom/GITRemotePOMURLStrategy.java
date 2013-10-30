package com.boissinot.jenkins.csvexporter.service.extractor.maven.pom;

import com.boissinot.jenkins.csvexporter.domain.jenkins.job.ConfigJob;

/**
 * @author Gregory Boissinot
 */
public class GITRemotePOMURLStrategy implements RemotePOMURLStrategy {

    @Override
    public StrategyType getType() {
        return StrategyType.GIT;
    }

    @Override
    public String getRemotePomURL(ConfigJob configJob, Object... contextObjects) {
        return null;
    }
}
