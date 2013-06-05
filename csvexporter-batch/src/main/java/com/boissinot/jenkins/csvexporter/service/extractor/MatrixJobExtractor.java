package com.boissinot.jenkins.csvexporter.service.extractor;

import com.boissinot.jenkins.csvexporter.domain.OutputCSVJobObj;
import com.boissinot.jenkins.csvexporter.service.extractor.command.BuildersElementRetriever;
import org.springframework.xml.transform.StringSource;
import org.springframework.xml.xpath.Jaxp13XPathTemplate;
import org.w3c.dom.Node;

import javax.xml.transform.Source;

/**
 * @author Gregory Boissinot
 */
public class MatrixJobExtractor extends JobExtractorSupport {

    public void buildCVSObj(OutputCSVJobObj.Builder builder, String configXML) {
        Jaxp13XPathTemplate template = new Jaxp13XPathTemplate();
        Source configXMLSource = new StringSource(configXML);

        buildSCMSection(builder, configXML);

        Node buildersNode = template.evaluateAsNode("//builders", configXMLSource);
        builder.buildSteps("\""
                + buildCommandSection(buildersNode)
                + "\"");
    }

    private String buildCommandSection(Node buildersNode) {
        BuildersElementRetriever buildersElementRetriever = new BuildersElementRetriever();
        return buildersElementRetriever.buildCommandSection(buildersNode);
    }

}
