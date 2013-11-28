package com.boissinot.jenkins.csvexporter.service.extractor.jenkins;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Gregory Boissinot
 */
public class CommonElementRetriever {

    protected Node getChildNodeWithLabel(Node rootNode, String nodeLabel) {
        final NodeList childNodes = rootNode.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            final Node childNode = childNodes.item(i);
            if (childNode != null) {
                if (nodeLabel.equals(childNode.getLocalName())) {
                    return childNode;
                }
            }
        }
        return null;
    }


    public List<Node> getAllChildNodeWithLabel(Node rootNode, String nodeLabel) {

        List<Node> result = new ArrayList<Node>();
        final NodeList childNodes = rootNode.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            final Node childNode = childNodes.item(i);
            if (childNode != null) {
                if (nodeLabel.equals(childNode.getLocalName())) {
                    result.add(childNode);
                }
            }
        }
        return result;
    }

    protected String getContent(Node node) {
        return node.getTextContent().trim();
    }
}
