package com.boissinot.jenkins.csvexporter.service.extractor.jenkins.matrix;

import com.boissinot.jenkins.csvexporter.service.extractor.jenkins.CommonElementRetriever;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.List;

/**
 * @author Gregory Boissinot
 */
public class PlatformElementRetriever {

    public String buildPlatformsSection(Node axeNode) {

        if (axeNode == null) {
            throw new NullPointerException("A axe Node not must be set.");
        }

        StringBuilder builder = new StringBuilder();
        NodeList childNodes = axeNode.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {

            Node builderNode = childNodes.item(i);
            String localName = builderNode.getLocalName();

            if ("hudson.matrix.LabelAxis".equals(localName)) {
                NodeList childNodes1 = builderNode.getChildNodes();
                for (int k = 0; k < childNodes1.getLength(); k++) {
                    Node builderNode1 = childNodes1.item(k);
                    String localName1 = builderNode1.getLocalName();
                    if ("values".equals(localName1)) {

                        CommonElementRetriever commonElementRetriever = new CommonElementRetriever();
                        List<Node> nodeWithLabel = commonElementRetriever.getAllChildNodeWithLabel(builderNode1, "string");
                        for (Node node : nodeWithLabel) {
                            builder.append("\n");
                            builder.append(node.getTextContent().trim());
                        }
                    }
                }
            }

        }

        if (builder.length() > 0) {
            builder.delete(0, 1);
        }

        return builder.toString();
    }
}
