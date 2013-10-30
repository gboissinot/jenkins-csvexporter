package com.boissinot.jenkins.csvexporter.service.extractor.maven.pom;

import com.boissinot.jenkins.csvexporter.domain.jenkins.job.ConfigJob;

/**
 * @author Gregory Boissinot
 */
public class SVNRemotePOMURLStrategy implements RemotePOMURLStrategy {

    @Override
    public StrategyType getType() {
        return StrategyType.SVN;
    }

    @Override
    public String getRemotePomURL(ConfigJob configJob, Object... contextObjects) {
        return configJob.getSvnURL() + "/pom.xml";
    }
}
