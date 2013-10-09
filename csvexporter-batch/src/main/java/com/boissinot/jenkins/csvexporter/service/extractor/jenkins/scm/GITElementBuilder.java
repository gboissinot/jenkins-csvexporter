package com.boissinot.jenkins.csvexporter.service.extractor.jenkins.scm;

import com.boissinot.jenkins.csvexporter.exception.ExportException;
import org.w3c.dom.Node;

/**
 * @author Gregory Boissinot
 */
public class GITElementBuilder extends SCMBuilder {

    private String gitURL;

    public String getGitURL() {
        return gitURL;
    }

    @Override
    public void buildSCMElement(Node scmNode) {

        Node userRemoteConfigsNode = getChildNodeWithLabel(scmNode, "userRemoteConfigs");
        if (userRemoteConfigsNode == null) {
            throw new ExportException("A userRemoteConfigs section must be set in the SCM SVM section.");
        }

        Node userRemoteConfigNode = getChildNodeWithLabel(userRemoteConfigsNode, "hudson.plugins.git.UserRemoteConfig");
        if (userRemoteConfigNode == null) {
            throw new ExportException("A userRemoteConfig sub-section must be set in the SCM SVM section.");
        }

        Node urlNode = getChildNodeWithLabel(userRemoteConfigNode, "url");
        if (urlNode == null) {
            throw new ExportException("A url section must be set in the SCM SVM section.");
        }

        this.gitURL = getContent(urlNode);
    }
}
