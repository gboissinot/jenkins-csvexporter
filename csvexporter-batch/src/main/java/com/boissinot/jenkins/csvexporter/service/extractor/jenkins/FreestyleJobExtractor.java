package com.boissinot.jenkins.csvexporter.service.extractor.jenkins;

import com.boissinot.jenkins.csvexporter.domain.OutputCSVJobObj;
import com.boissinot.jenkins.csvexporter.service.extractor.jenkins.command.BuildersElementRetriever;
import com.boissinot.jenkins.csvexporter.service.http.HttpResourceContentFetcher;
import com.boissinot.jenkins.csvexporter.service.extractor.maven.pom.POMDeveloperSectionExtractor;
import org.springframework.xml.transform.StringSource;
import org.springframework.xml.xpath.Jaxp13XPathTemplate;
import org.w3c.dom.Node;

import javax.xml.transform.Source;

/**
 * @author Gregory Boissinot
 */
public class FreestyleJobExtractor extends JobExtractorSupport {

    public FreestyleJobExtractor(HttpResourceContentFetcher contentFetcher, POMDeveloperSectionExtractor pomDeveloperSectionExtractor, String csvViewerRootUrl) {
        super(contentFetcher, pomDeveloperSectionExtractor, csvViewerRootUrl);
    }

    public void buildCVSObj(OutputCSVJobObj.Builder builder, String configXML) {
        Jaxp13XPathTemplate template = new Jaxp13XPathTemplate();
        Source configXMLSource = new StringSource(configXML);
        Node buildersNode = template.evaluateAsNode("//builders", configXMLSource);
        builder.buildSteps(buildCommandSection(buildersNode));
    }

    private String buildCommandSection(Node buildersNode) {
        BuildersElementRetriever buildersElementRetriever = new BuildersElementRetriever();
        return buildersElementRetriever.buildCommandSection(buildersNode);
    }

}
