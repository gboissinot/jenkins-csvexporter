package com.boissinot.jenkins.csvexporter.service.extractor.jenkins.command;

import com.boissinot.jenkins.csvexporter.exception.ExportException;
import com.boissinot.jenkins.csvexporter.service.extractor.jenkins.CommonElementRetriever;
import org.w3c.dom.Node;

/**
 * @author Gregory Boissinot
 */
public class ShellElementRetriever extends CommonElementRetriever {

    public String buildShellCommand(Node builderNode) {

        Node commandNode = getChildNodeWithLabel(builderNode, "command");
        if (commandNode == null) {
            throw new ExportException("A command section must be set.");
        }

        return getContent(commandNode);
    }
}
