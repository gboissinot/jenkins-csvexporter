package com.boissinot.jenkins.csvexporter.service.extractor.maven.pom;

import com.boissinot.jenkins.csvexporter.domain.jenkins.job.ConfigJob;
import com.boissinot.jenkins.csvexporter.domain.maven.pom.Developer;
import com.boissinot.jenkins.csvexporter.service.extractor.jenkins.pom.DeveloperElementRetriever;
import com.boissinot.jenkins.csvexporter.service.http.HttpResourceContentFetcher;
import org.springframework.beans.factory.annotation.Required;

import java.util.List;

/**
 * @author Gregory Boissinot
 */
public class DeveloperInfoRetriever {

    private HttpResourceContentFetcher httpResourceContentFetcher;

    private List<RemotePOMURLStrategy> strategies;

    @Required
    public void setHttpResourceContentFetcher(HttpResourceContentFetcher httpResourceContentFetcher) {
        this.httpResourceContentFetcher = httpResourceContentFetcher;
    }

    @Required
    public void setStrategies(List<RemotePOMURLStrategy> strategies) {
        this.strategies = strategies;
    }

    public String getDevelopers(ConfigJob configJob, Object... objects) {

        RemotePOMURLStrategyRetriever strategyRetriever = new RemotePOMURLStrategyRetriever();
        final StrategyType strategyType = strategyRetriever.getStrategyType(configJob);

        final RemotePOMURLStrategy strategyObject = strategyRetriever.getStrategyObject(strategyType, strategies);
        if (strategyObject == null) {
            return null;
        }

        final String remotePomUrl = strategyObject.getRemotePomURL(configJob, objects);
        if (remotePomUrl != null) {
            String pomContent = httpResourceContentFetcher.getContent(remotePomUrl);
            POMDeveloperSectionExtractor sectionExtractor = new POMDeveloperSectionExtractor();
            final List<Developer> developers = sectionExtractor.extract(pomContent);
            DeveloperElementRetriever retriever = new DeveloperElementRetriever();
            return retriever.buildDeveloperSection(developers);
        }

        return null;
    }

}
