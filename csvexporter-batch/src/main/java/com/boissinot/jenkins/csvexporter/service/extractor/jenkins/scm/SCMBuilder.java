package com.boissinot.jenkins.csvexporter.service.extractor.jenkins.scm;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * @author Gregory Boissinot
 */
public abstract class SCMBuilder {

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

    protected String getContent(Node node) {
        return node.getTextContent().trim();
    }

    public abstract void buildSCMElement(Node scmNode);
}
