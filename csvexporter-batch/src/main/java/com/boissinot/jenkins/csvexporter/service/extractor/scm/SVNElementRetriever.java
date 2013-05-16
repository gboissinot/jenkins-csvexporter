package com.boissinot.jenkins.csvexporter.service.extractor.scm;

import com.boissinot.jenkins.csvexporter.domain.OutputCSVJobObj;
import com.boissinot.jenkins.csvexporter.exception.ExportException;
import com.boissinot.jenkins.csvexporter.service.extractor.CommonElementRetriever;
import org.w3c.dom.Node;

/**
 * @author Gregory Boissinot
 */
public class SVNElementRetriever extends CommonElementRetriever {

    public void setSCMSection(OutputCSVJobObj.Builder builder, Node scmNode) {

        Node locationNode = getLabelNode(scmNode, "locations");
        if (locationNode == null) {
            throw new ExportException("A location section must be set in the SCM SVM section.");
        }
        Node moduleNode = getLabelNode(locationNode, "hudson.scm.SubversionSCM_-ModuleLocation");
        if (moduleNode == null) {
            throw new ExportException("A module section must be set in the SCM SVM section.");
        }
        Node remoteNode = getLabelNode(moduleNode, "remote");
        if (remoteNode == null) {
            throw new ExportException("A remote section must be set in the SCM SVM section.");
        }

        builder.svnURL(getContent(remoteNode));
    }

}
