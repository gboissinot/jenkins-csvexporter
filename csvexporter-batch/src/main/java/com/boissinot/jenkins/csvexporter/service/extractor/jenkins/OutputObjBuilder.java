package com.boissinot.jenkins.csvexporter.service.extractor.jenkins;

import com.boissinot.jenkins.csvexporter.domain.OutputCSVJobObj;
import com.boissinot.jenkins.csvexporter.domain.jenkins.job.ConfigJob;
import com.boissinot.jenkins.csvexporter.domain.maven.pom.Developer;
import com.boissinot.jenkins.csvexporter.service.extractor.jenkins.pom.DeveloperElementRetriever;
import com.boissinot.jenkins.csvexporter.service.extractor.maven.pom.POMDeveloperSectionExtractor;
import com.boissinot.jenkins.csvexporter.service.extractor.maven.pom.POMFileInfoExtractor;
import com.boissinot.jenkins.csvexporter.service.http.HttpResourceContentFetcher;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.integration.Message;
import org.springframework.integration.annotation.ServiceActivator;

import java.util.List;
import java.util.Map;

/**
 * @author Gregory Boissinot
 */
public class OutputObjBuilder {

    private HttpResourceContentFetcher httpResourceContentFetcher;
    private POMFileInfoExtractor pomFileInfoExtractor;

    @Required
    public void setHttpResourceContentFetcher(HttpResourceContentFetcher httpResourceContentFetcher) {
        this.httpResourceContentFetcher = httpResourceContentFetcher;
    }

    @Required
    public void setPomFileInfoExtractor(POMFileInfoExtractor pomFileInfoExtractor) {
        this.pomFileInfoExtractor = pomFileInfoExtractor;
    }

    @ServiceActivator
    @SuppressWarnings("unused")
    public OutputCSVJobObj buildObj(Message<ConfigJob> messageConfigJob) {

        ConfigJob configJob = messageConfigJob.getPayload();

        OutputCSVJobObj.Builder builder = new OutputCSVJobObj.Builder();
        builder.name(configJob.getName());
        builder.desc(configJob.getDesc());
        builder.disabled(configJob.isDisabled());
        builder.functionalJobLanguage(configJob.getFunctionalJobLanguage());
        builder.functionalJobType(configJob.getFunctionalJobType());
        builder.trigger(configJob.getTrigger());
        builder.buildSteps(configJob.getBuildSteps());
        builder.cvsRoot(configJob.getCvsRoot());
        builder.cvsModule(configJob.getCvsModule());
        builder.cvsBranche(configJob.getCvsBranche());
        builder.svnURL(configJob.getSvnURL());
        builder.gitURL(configJob.getGitURL());

        final Map module_map = (Map) messageConfigJob.getHeaders().get("MODULE_MAP");
        String remotePomUrl = pomFileInfoExtractor.getPomUrl(configJob, module_map);
        if (remotePomUrl != null) {
            String pomContent = httpResourceContentFetcher.getContent(remotePomUrl);
            POMDeveloperSectionExtractor sectionExtractor = new POMDeveloperSectionExtractor();
            List<Developer> developers = sectionExtractor.extract(pomContent);
            DeveloperElementRetriever retriever = new DeveloperElementRetriever();
            builder.developers(retriever.buildDeveloperSection(developers));
        }

        return builder.build();
    }
}
