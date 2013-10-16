package com.boissinot.jenkins.csvexporter.service.extractor.jenkins.pom;

import com.boissinot.jenkins.csvexporter.domain.maven.pom.Developer;

import java.util.List;

/**
 * @author Gregory Boissinot
 */
public class DeveloperElementRetriever {

    public String buildDeveloperSection(List<Developer> developerList) {
        StringBuilder emailContent = new StringBuilder();
        for (Developer developer : developerList) {
            emailContent.append("\n");
            StringBuilder developerInfo = new StringBuilder();

            String id = developer.getId();
            if (id != null) {
                developerInfo.append("/");
                developerInfo.append(id);
            }

            String email = developer.getEmail();
            if (email != null) {
                developerInfo.append("/");
                developerInfo.append(email);
            }

            developerInfo.delete(0, 1);
            emailContent.append(developerInfo);
        }
        emailContent.delete(0, 1);

        return emailContent.toString();
    }
}
