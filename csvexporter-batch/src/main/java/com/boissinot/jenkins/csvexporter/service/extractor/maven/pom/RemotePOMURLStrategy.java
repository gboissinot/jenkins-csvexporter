package com.boissinot.jenkins.csvexporter.service.extractor.maven.pom;

import com.boissinot.jenkins.csvexporter.domain.jenkins.job.ConfigJob;

/**
 * @author Gregory Boissinot
 */
public interface RemotePOMURLStrategy {

    public String getRemotePomURL(ConfigJob configJob, Object... contextObjects);

    public StrategyType getType();
}
