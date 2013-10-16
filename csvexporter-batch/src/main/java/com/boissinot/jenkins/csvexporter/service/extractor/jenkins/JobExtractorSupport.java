package com.boissinot.jenkins.csvexporter.service.extractor.jenkins;

import com.boissinot.jenkins.csvexporter.domain.OutputCSVJobObj;
import com.boissinot.jenkins.csvexporter.domain.jenkins.job.ConfigJob;
import com.boissinot.jenkins.csvexporter.domain.maven.pom.PomFile;
import com.boissinot.jenkins.csvexporter.exception.ExportException;
import com.boissinot.jenkins.csvexporter.maven.extractor.POMEmailExtractorTask;
import com.boissinot.jenkins.csvexporter.maven.extractor.POMRemoteObj;
import com.boissinot.jenkins.csvexporter.service.extractor.jenkins.scm.CVSElementBuilder;
import com.boissinot.jenkins.csvexporter.service.extractor.jenkins.scm.GITElementBuilder;
import com.boissinot.jenkins.csvexporter.service.extractor.jenkins.scm.SVNElementBuilder;
import com.boissinot.jenkins.csvexporter.service.extractor.maven.pom.POMDeveloperSectionExtractor;
import com.boissinot.jenkins.csvexporter.service.extractor.maven.pom.POMFileInfoExtractor;
import com.boissinot.jenkins.csvexporter.service.http.HttpResourceContentFetcher;
import com.sun.org.apache.xerces.internal.dom.DeferredElementNSImpl;
import org.springframework.integration.Message;
import org.springframework.integration.MessageHeaders;
import org.springframework.xml.transform.StringSource;
import org.springframework.xml.xpath.Jaxp13XPathTemplate;
import org.w3c.dom.Node;

import javax.xml.transform.Source;
import java.util.Map;

import static com.boissinot.jenkins.csvexporter.domain.JobMessageHeaders.*;

/**
 * @author Gregory Boissinot
 */
public abstract class JobExtractorSupport extends CommonElementRetriever implements JobExtractor {

    private HttpResourceContentFetcher contentFetcher;
    private POMDeveloperSectionExtractor pomDeveloperSectionExtractor;
    private String csvViewerRootUrl;

    protected JobExtractorSupport(HttpResourceContentFetcher contentFetcher, POMDeveloperSectionExtractor pomDeveloperSectionExtractor, String csvViewerRootUrl) {
        this.contentFetcher = contentFetcher;
        this.pomDeveloperSectionExtractor = pomDeveloperSectionExtractor;
        this.csvViewerRootUrl = csvViewerRootUrl;
    }

    @SuppressWarnings("unchecked")
    public OutputCSVJobObj getCVSObj(Message jobMessage) {

//        MessageHeaders headers = jobMessage.getHeaders();
//        String jobName = headers.get(HEADER_JOB_NAME, String.class);
//        String functionalJobType = headers.get(HEADER_FUNCTIONAL_JOB_TYPE, String.class);
//        String jenkinsJobType = headers.get(HEADER_JENKINS_JOB_TYPE, String.class);
//        String functionalJobLanguage = headers.get(HEADER_FUNCTIONAL_JOB_LANGUAGE, String.class);
//        Map<String, Map<String, String>> contextMap = headers.get(HEADER_FUNCTIONAL_MODULE_MAP, Map.class);
//
//        OutputCSVJobObj.Builder builder = new OutputCSVJobObj.Builder();
//
//        String configXML = (String) jobMessage.getPayload();
//        if (configXML == null) {
//            throw new ExportException(String.format("'%s' job name must be set.", jobName));
//        }
//        if (configXML.trim().length() == 0) {
//            throw new ExportException(String.format("'%s' job name is empty.", jobName));
//        }
//
//        Jaxp13XPathTemplate template = new Jaxp13XPathTemplate();
//        Source configXMLSource = new StringSource(configXML);
//
//        String description = template.evaluateAsString("//description", configXMLSource);
//        String spec = template.evaluateAsString("//spec", configXMLSource);
//
//        Node disabledNode = template.evaluateAsNode("//disabled", configXMLSource);
//        boolean disabled = Boolean.valueOf(getContent(disabledNode));
//
//        Node scmNode = template.evaluateAsNode("//scm ", configXMLSource);
//
//        String cvsRoot = null;
//        String cvsModule = null;
//        String cvsBranche = null;
//        String svnURL = null;
//        String gitURL = null;
//
//        String scmClassElement = ((DeferredElementNSImpl) scmNode).getAttribute("class");
//        if ("hudson.scm.CVSSCM".equals(scmClassElement)) {
//            CVSElementBuilder cvsElementBuilder = new CVSElementBuilder();
//            cvsElementBuilder.buildSCMElement(scmNode);
//            cvsRoot = cvsElementBuilder.getCvsRoot();
//            cvsModule = cvsElementBuilder.getCvsModule();
//            cvsBranche = cvsElementBuilder.getCvsBranche();
//        } else if ("hudson.scm.SubversionSCM".equals(scmClassElement)) {
//            SVNElementBuilder svnElementBuilder = new SVNElementBuilder();
//            svnElementBuilder.buildSCMElement(scmNode);
//            svnURL = svnElementBuilder.getSvnURL();
//        } else if ("hudson.plugins.git.GitSCM".equals(scmClassElement)) {
//            GITElementBuilder gitElementBuilder = new GITElementBuilder();
//            gitElementBuilder.buildSCMElement(scmNode);
//            gitURL = gitElementBuilder.getGitURL();
//        }


//        builder.name(jobName)
//                .jenkinsType(jenkinsJobType)
//                .functionalJobType(functionalJobType)
//                .functionalJobLanguage(functionalJobLanguage)
//                .disabled(disabled)
//                .desc(description)
//                .trigger(spec)
//                .cvsModule(cvsModule)
//                .cvsRoot(cvsRoot)
//                .svnURL(svnURL)
//                .gitURL(gitURL);


//        buildCVSObj(builder, configXML);


//        POMFileInfoExtractor pomFileInfoExtractor = new POMFileInfoExtractor(contextMap, csvViewerRootUrl);
//        ConfigJob configJob = new ConfigJob();
//        configJob.setCvsBranche(cvsBranche);
//        configJob.setCvsModule(cvsModule);
//        configJob.setGitURL(gitURL);
//        configJob.setSvnURL(svnURL);
//        configJob.setJobName(jobName);

//        POMRemoteObj pomObj = pomFileInfoExtractor.getPomUrl(configJob);

//        PomFile pomFile = null;
//        try {
//            if (pomObj != null) {
//                POMEmailExtractorTask pomEmailExtractorTask = new POMEmailExtractorTask(pomObj.getHttpURL(),
//                        contentFetcher,
//                        pomDeveloperSectionExtractor);
//                Thread.sleep(500);
//                pomFile = pomEmailExtractorTask.call();
//            }
//        } catch (Exception e) {
//            throw new ExportException(e);
//        }
//
//        if (pomFile != null) {
//            builder.developers(pomFile.getEmailContent());
//        }

//        return new OutputCSVJobObj();
//        return new OutputCSVJobObj(builder);
        return null;
    }

    protected abstract void buildCVSObj(OutputCSVJobObj.Builder builder, String configXML);

}
