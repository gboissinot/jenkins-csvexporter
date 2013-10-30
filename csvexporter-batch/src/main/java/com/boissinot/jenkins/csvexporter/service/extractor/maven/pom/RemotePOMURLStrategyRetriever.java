package com.boissinot.jenkins.csvexporter.service.extractor.maven.pom;

import com.boissinot.jenkins.csvexporter.domain.jenkins.job.ConfigJob;

import java.util.List;

/**
 * @author Gregory Boissinot
 */
public class RemotePOMURLStrategyRetriever {

    public RemotePOMURLStrategy getStrategyObject(StrategyType strategyType, List<RemotePOMURLStrategy> strategies) {
        for (RemotePOMURLStrategy remotePOMURLStrategy : strategies) {
            if (remotePOMURLStrategy.getType() == strategyType) {
                return remotePOMURLStrategy;
            }
        }
        return null;
    }

    public StrategyType getStrategyType(ConfigJob configJob) {

        if (configJob.getCvsModule() != null) {
            return StrategyType.CVS;
        }

        if (configJob.getSvnURL() != null) {
            return StrategyType.SVN;
        }

        if (configJob.getGitURL() != null) {
            return StrategyType.GIT;
        }

        return StrategyType.UNKNOWN;
    }
}
