package com.boissinot.jenkins.csvexporter.service.extractor.jenkins.command;

import com.boissinot.jenkins.csvexporter.exception.ExportException;
import com.boissinot.jenkins.csvexporter.service.extractor.jenkins.CommonElementRetriever;
import org.w3c.dom.Node;

/**
 * @author Gregory Boissinot
 */
public class MavenElementRetriever extends CommonElementRetriever {

    public String buildMavenCommand(Node builderNode) {

        Node targetNode = getChildNodeWithLabel(builderNode, "targets");
        if (targetNode == null) {
            throw new ExportException("A Maven command must contain a targets section.");
        }

        return getContent(targetNode);
    }

}
