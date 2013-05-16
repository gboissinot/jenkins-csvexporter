package com.boissinot.jenkins.csvexporter.service.extractor.scm;

import com.boissinot.jenkins.csvexporter.domain.OutputCSVJobObj;
import com.boissinot.jenkins.csvexporter.exception.ExportException;
import com.boissinot.jenkins.csvexporter.service.extractor.CommonElementRetriever;
import org.w3c.dom.Node;

/**
 * @author Gregory Boissinot
 */
public class GITElementRetriever extends CommonElementRetriever {

    public void setSCMSection(OutputCSVJobObj.Builder builder, Node scmNode) {

        Node userRemoteConfigsNode = getLabelNode(scmNode, "userRemoteConfigs");
        if (userRemoteConfigsNode == null) {
            throw new ExportException("A userRemoteConfigs section must be set in the SCM SVM section.");
        }

        Node userRemoteConfigNode = getLabelNode(userRemoteConfigsNode, "hudson.plugins.git.UserRemoteConfig");
        if (userRemoteConfigNode == null) {
            throw new ExportException("A userRemoteConfig sub-section must be set in the SCM SVM section.");
        }

        Node urlNode = getLabelNode(userRemoteConfigNode, "url");
        if (urlNode == null) {
            throw new ExportException("A url section must be set in the SCM SVM section.");
        }

        builder.gitURL(getContent(urlNode));
    }
}
