package com.boissinot.jenkins.csvexporter.service.extractor.jenkins;

import com.boissinot.jenkins.csvexporter.domain.OutputCSVJobObj;
import com.boissinot.jenkins.csvexporter.domain.jenkins.job.ConfigJob;
import com.boissinot.jenkins.csvexporter.service.extractor.maven.pom.DeveloperInfoRetriever;
import com.boissinot.jenkins.csvexporter.service.extractor.maven.pom.POMContentExtractor;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.integration.Message;
import org.springframework.integration.annotation.ServiceActivator;

import java.util.Map;

/**
 * @author Gregory Boissinot
 */
public class OutputObjBuilder {

    private POMContentExtractor pomContentExtractor;

    @Required
    public void setPomContentExtractor(POMContentExtractor pomContentExtractor) {
        this.pomContentExtractor = pomContentExtractor;
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
        builder.platforms(configJob.getPlatforms());

        final Map module_map = (Map) messageConfigJob.getHeaders().get("MODULE_MAP");
        String pomContent = pomContentExtractor.getPomContent(configJob, module_map);
        if (pomContent != null) {
            DeveloperInfoRetriever developerInfoRetriever = new DeveloperInfoRetriever();
            builder.developers(developerInfoRetriever.getDevelopers(pomContent));
        }

        return builder.build();
    }
}
