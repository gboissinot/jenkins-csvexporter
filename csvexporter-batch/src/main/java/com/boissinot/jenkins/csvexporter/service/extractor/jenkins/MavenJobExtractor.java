package com.boissinot.jenkins.csvexporter.service.extractor.jenkins;

import com.boissinot.jenkins.csvexporter.domain.OutputCSVJobObj;
import com.boissinot.jenkins.csvexporter.service.http.HttpResourceContentFetcher;
import com.boissinot.jenkins.csvexporter.service.extractor.maven.pom.POMDeveloperSectionExtractor;
import org.springframework.xml.transform.StringSource;
import org.springframework.xml.xpath.Jaxp13XPathTemplate;

import javax.xml.transform.Source;

/**
 * @author Gregory Boissinot
 */
public class MavenJobExtractor extends JobExtractorSupport {

    public MavenJobExtractor(HttpResourceContentFetcher contentFetcher, POMDeveloperSectionExtractor pomDeveloperSectionExtractor, String csvViewerRootUrl) {
        super(contentFetcher, pomDeveloperSectionExtractor, csvViewerRootUrl);
    }

    public void buildCVSObj(OutputCSVJobObj.Builder builder, String configXML) {
        Jaxp13XPathTemplate template = new Jaxp13XPathTemplate();
        Source configXMLSource = new StringSource(configXML);
        String goals = template.evaluateAsString("//goals", configXMLSource);
        builder.buildSteps(goals);
    }

}
