package com.boissinot.jenkins.csvexporter.service.extractor.jenkins;

import com.boissinot.jenkins.csvexporter.domain.OutputCSVJobObj;
import com.boissinot.jenkins.csvexporter.domain.jenkins.job.ConfigJob;
import com.boissinot.jenkins.csvexporter.domain.maven.pom.Developer;
import com.boissinot.jenkins.csvexporter.maven.extractor.POMRemoteObj;
import com.boissinot.jenkins.csvexporter.service.extractor.jenkins.command.BuildersElementRetriever;
import com.boissinot.jenkins.csvexporter.service.extractor.jenkins.pom.DeveloperElementRetriever;
import com.boissinot.jenkins.csvexporter.service.extractor.jenkins.scm.SCMElementBuilder;
import com.boissinot.jenkins.csvexporter.service.extractor.maven.pom.POMDeveloperSectionExtractor;
import com.boissinot.jenkins.csvexporter.service.extractor.maven.pom.POMFileInfoExtractor;
import com.boissinot.jenkins.csvexporter.service.http.HttpResourceContentFetcher;
import org.springframework.integration.Message;
import org.springframework.integration.MessageHeaders;
import org.springframework.integration.annotation.ServiceActivator;
import org.w3c.dom.Node;

import java.io.*;
import java.util.List;
import java.util.Map;

import static com.boissinot.jenkins.csvexporter.domain.JobMessageHeaders.*;

/**
 * @author Gregory Boissinot
 */
public class OutputObjBuilder {

    private HttpResourceContentFetcher httpResourceContentFetcher;

    private POMFileInfoExtractor pomFileInfoExtractor;


    public void setHttpResourceContentFetcher(HttpResourceContentFetcher httpResourceContentFetcher) {
        this.httpResourceContentFetcher = httpResourceContentFetcher;
    }

    public void setPomFileInfoExtractor(POMFileInfoExtractor pomFileInfoExtractor) {
        this.pomFileInfoExtractor = pomFileInfoExtractor;
    }

    @ServiceActivator
    public OutputCSVJobObj buildObj(Message message) {
        OutputCSVJobObj.Builder builder = new OutputCSVJobObj.Builder();
        MessageHeaders headers = message.getHeaders();

        //jobName
        String jobName = headers.get(HEADER_JOB_NAME, String.class);
        builder.name(jobName);
        String functionalJobType = headers.get(HEADER_FUNCTIONAL_JOB_TYPE, String.class);
        builder.functionalJobType(functionalJobType);

        //jenkinsJobType
        String jenkinsJobType = headers.get(HEADER_JENKINS_JOB_TYPE, String.class);
        builder.jenkinsType(jenkinsJobType);
        //functionalJobLanguage
        String functionalJobLanguage = headers.get(HEADER_FUNCTIONAL_JOB_LANGUAGE, String.class);
        builder.functionalJobLanguage(functionalJobLanguage);
        //spec
        final String spec = (String) headers.get("spec");
        builder.trigger(spec);
        //description
        final String description = (String) headers.get("description");
        builder.desc(description);
        //disabled
        final Boolean disabled = (Boolean) headers.get("disabled");
        builder.disabled(disabled);

        //scm
        final Node scmNode = (Node) headers.get("scm");
        SCMElementBuilder scmElementBuilder = new SCMElementBuilder();
        scmElementBuilder.build(scmNode);
        builder.svnURL(scmElementBuilder.getSvnURL());
        builder.gitURL(scmElementBuilder.getGitURL());
        builder.cvsRoot(scmElementBuilder.getCvsRoot());
        builder.cvsModule(scmElementBuilder.getCvsModule());
        builder.cvsBranche(scmElementBuilder.getCvsBranche());

        //builders
        final Node buildersNode = (Node) headers.get("builders");
        BuildersElementRetriever buildersElementRetriever = new BuildersElementRetriever();
        final String buildSteps = buildersElementRetriever.buildCommandSection(buildersNode);
        builder.buildSteps(buildSteps);

        //developers
        ConfigJob configJob = new ConfigJob();
        configJob.setCvsBranche(scmElementBuilder.getCvsBranche());
        configJob.setCvsModule(scmElementBuilder.getCvsModule());
        configJob.setGitURL(scmElementBuilder.getGitURL());
        configJob.setSvnURL(scmElementBuilder.getSvnURL());
        configJob.setJobName(jobName);
        POMRemoteObj pomObj = pomFileInfoExtractor.getPomUrl(configJob, (Map) message.getHeaders().get("MODULE_MAP"));
        if (pomObj != null) {
            String pomContent = httpResourceContentFetcher.getContent(pomObj.getHttpURL());
            POMDeveloperSectionExtractor sectionExtractor = new POMDeveloperSectionExtractor();
            List<Developer> developers = sectionExtractor.extract(pomContent);
            DeveloperElementRetriever retriever = new DeveloperElementRetriever();
            builder.developers(retriever.buildDeveloperSection(developers));
        }

        final OutputCSVJobObj outputCSVJobObj = builder.build();

        //==============
        File jobEmailsFile = new File("jobEmails.txt");
        FileWriter fileWriter = null;
        BufferedWriter bufferedWriter = null;
        PrintWriter printWriter = null;
        try {
            fileWriter = new FileWriter(jobEmailsFile, true);
            bufferedWriter = new BufferedWriter(fileWriter);
            printWriter = new PrintWriter(bufferedWriter);
            printWriter
                    .append(outputCSVJobObj.getName())
                    .append(";")
                    .append(outputCSVJobObj.getDevelopers())
                    .append("\n");

        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } finally {
            if (printWriter != null)
                printWriter.close();
            if (bufferedWriter != null) {
                try {
                    bufferedWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
            }
            if (fileWriter != null) {
                try {
                    fileWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
            }
        }
        //==============

        return outputCSVJobObj;
    }
}
