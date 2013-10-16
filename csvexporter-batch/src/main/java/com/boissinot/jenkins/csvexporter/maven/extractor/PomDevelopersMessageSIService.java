package com.boissinot.jenkins.csvexporter.maven.extractor;

import com.boissinot.jenkins.csvexporter.domain.jenkins.job.ConfigJob;
import com.boissinot.jenkins.csvexporter.domain.maven.pom.Developer;
import com.boissinot.jenkins.csvexporter.service.extractor.maven.pom.POMDeveloperSectionExtractor;
import com.boissinot.jenkins.csvexporter.service.extractor.maven.pom.POMFileInfoExtractor;
import com.boissinot.jenkins.csvexporter.service.http.HttpResourceContentFetcher;
import org.springframework.integration.Message;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.support.MessageBuilder;

import java.util.List;
import java.util.Map;

/**
 * @author Gregory Boissinot
 */
public class PomDevelopersMessageSIService {

    private HttpResourceContentFetcher httpResourceContentFetcher;

    private POMFileInfoExtractor pomFileInfoExtractor;

    public void setPomFileInfoExtractor(POMFileInfoExtractor pomFileInfoExtractor) {
        this.pomFileInfoExtractor = pomFileInfoExtractor;
    }

    public void setHttpResourceContentFetcher(HttpResourceContentFetcher httpResourceContentFetcher) {
        this.httpResourceContentFetcher = httpResourceContentFetcher;
    }

    @ServiceActivator
    public Message buildMessage(Message message, ConfigJob configJob) {
        POMRemoteObj pomObj = pomFileInfoExtractor.getPomUrl(configJob, (Map) message.getHeaders().get("MODULE_MAP"));
        String pomContent = httpResourceContentFetcher.getContent(pomObj.getHttpURL());
        POMDeveloperSectionExtractor sectionExtractor = new POMDeveloperSectionExtractor();
        List<Developer> developers = sectionExtractor.extract(pomContent);
        return MessageBuilder.fromMessage(message).setHeader("developers", developers).build();

    }
}
