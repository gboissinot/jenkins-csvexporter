package com.boissinot.jenkins.csvexporter.service.integration;

import com.boissinot.jenkins.csvexporter.domain.jenkins.job.ConfigJob;
import com.boissinot.jenkins.csvexporter.service.extractor.jenkins.command.BuildersElementRetriever;
import com.boissinot.jenkins.csvexporter.service.extractor.jenkins.scm.SCMElementBuilder;
import org.springframework.integration.MessageHeaders;
import org.springframework.integration.annotation.ServiceActivator;
import org.w3c.dom.Node;

import static com.boissinot.jenkins.csvexporter.domain.JobMessageHeaders.*;

/**
 * @author Gregory Boissinot
 */
public class ConfigObjPayloadJobTransformer {

    @ServiceActivator
    @SuppressWarnings("unused")
    public ConfigJob buildConfigObj(MessageHeaders headers) {

        ConfigJob configJob = new ConfigJob();

        //jobName
        String jobName = headers.get(HEADER_JOB_NAME, String.class);
        configJob.setName(jobName);
        String functionalJobType = headers.get(HEADER_FUNCTIONAL_JOB_TYPE, String.class);
        configJob.setFunctionalJobType(functionalJobType);

        //jenkinsJobType
        String jenkinsJobType = headers.get(HEADER_JENKINS_JOB_TYPE, String.class);
        configJob.setJenkinsType(jenkinsJobType);
        //functionalJobLanguage
        String functionalJobLanguage = headers.get(HEADER_FUNCTIONAL_JOB_LANGUAGE, String.class);
        configJob.setFunctionalJobType(functionalJobLanguage);
        //spec
        final String spec = (String) headers.get("spec");
        configJob.setTrigger(spec);
        //description
        final String description = (String) headers.get("description");
        configJob.setDesc(description);
        //disabled
        final Boolean disabled = (Boolean) headers.get("disabled");
        configJob.setDisabled(disabled);

        //scm
        final Node scmNode = (Node) headers.get("scm");
        SCMElementBuilder scmElementBuilder = new SCMElementBuilder();
        scmElementBuilder.build(scmNode);
        configJob.setSvnURL(scmElementBuilder.getSvnURL());
        configJob.setGitURL(scmElementBuilder.getGitURL());
        configJob.setCvsRoot(scmElementBuilder.getCvsRoot());
        configJob.setCvsModule(scmElementBuilder.getCvsModule());
        configJob.setCvsBranche(scmElementBuilder.getCvsBranche());

        //builders
        final Node buildersNode = (Node) headers.get("builders");
        if (buildersNode != null) {
            BuildersElementRetriever buildersElementRetriever = new BuildersElementRetriever();
            final String buildSteps = buildersElementRetriever.buildCommandSection(buildersNode);
            configJob.setBuildSteps(buildSteps);
        }

        return configJob;
    }
}
