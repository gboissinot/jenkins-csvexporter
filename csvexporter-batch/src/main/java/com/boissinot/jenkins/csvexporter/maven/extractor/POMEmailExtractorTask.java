package com.boissinot.jenkins.csvexporter.maven.extractor;

import com.boissinot.jenkins.csvexporter.domain.maven.pom.Developer;
import com.boissinot.jenkins.csvexporter.domain.maven.pom.PomFile;
import com.boissinot.jenkins.csvexporter.service.http.ResourceContentFetcher;
import com.boissinot.jenkins.csvexporter.service.extractor.maven.pom.POMDeveloperSectionExtractor;

import java.util.List;
import java.util.concurrent.Callable;

/**
 * @author Gregory Boissinot
 */
public class POMEmailExtractorTask implements Callable<PomFile> {

    private String httpURL;
    private ResourceContentFetcher contentFetcher;
    private POMDeveloperSectionExtractor pomDeveloperSectionExtractor;

    public POMEmailExtractorTask(String httpURL, ResourceContentFetcher contentFetcher, POMDeveloperSectionExtractor pomDeveloperSectionExtractor) {
        this.httpURL = httpURL;
        this.contentFetcher = contentFetcher;
        this.pomDeveloperSectionExtractor = pomDeveloperSectionExtractor;
    }

    @Override
    public PomFile call() throws Exception {

        PomFile pomFile = new PomFile();

        String pomContent = contentFetcher.getContent(httpURL);
        List<Developer> developers = pomDeveloperSectionExtractor.extract(pomContent);
        pomFile.setDeveloperList(developers);

        return pomFile;
    }

}
