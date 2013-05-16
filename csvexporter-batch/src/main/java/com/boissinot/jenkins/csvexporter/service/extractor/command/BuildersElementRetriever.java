package com.boissinot.jenkins.csvexporter.service.extractor.command;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * @author Gregory Boissinot
 */
public class BuildersElementRetriever {

    public String buildCommandSection(Node buildersNode) {

        StringBuilder builder = new StringBuilder();


        NodeList childNodes = buildersNode.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            builder.append("\n");
            Node builderNode = childNodes.item(i);
            String localName = builderNode.getLocalName();

            if ("hudson.tasks.Maven".equals(localName)) {
                MavenElementRetriever mavenElementRetriever = new MavenElementRetriever();
                builder.append(mavenElementRetriever.buildMavenCommand(builderNode));
            } else if ("hudson.tasks.Shell".equals(localName)) {
                ShellElementRetriever shellElementRetriever = new ShellElementRetriever();
                builder.append(shellElementRetriever.buildShellCommand(builderNode));
            } else {
                // throw new UnsupportedOperationException("Only Shell and Maven command supported.");
            }
        }

        builder.delete(0, 1);
        return builder.toString();
    }
}
