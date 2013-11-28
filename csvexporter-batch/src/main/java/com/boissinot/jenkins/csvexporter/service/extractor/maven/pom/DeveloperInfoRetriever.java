package com.boissinot.jenkins.csvexporter.service.extractor.maven.pom;

import com.boissinot.jenkins.csvexporter.domain.maven.pom.Developer;
import com.boissinot.jenkins.csvexporter.service.extractor.jenkins.pom.DeveloperElementRetriever;

import java.util.List;

/**
 * @author Gregory Boissinot
 */
public class DeveloperInfoRetriever {

    public String getDevelopers(String pomContent) {

        if (pomContent == null) {
            throw new NullPointerException("A pom content is required.");
        }

        POMDeveloperSectionExtractor sectionExtractor = new POMDeveloperSectionExtractor();
        final List<Developer> developers = sectionExtractor.extract(pomContent);
        DeveloperElementRetriever retriever = new DeveloperElementRetriever();
        return retriever.buildDeveloperSection(developers);
    }

}
