package com.boissinot.jenkins.csvexporter.service.extractor.jenkins.scm;

import com.boissinot.jenkins.csvexporter.exception.ExportException;
import org.w3c.dom.Node;

/**
 * @author Gregory Boissinot
 */
public class SVNElementBuilder extends SCMBuilder {

    private String svnURL;

    public String getSvnURL() {
        return svnURL;
    }

    @Override
    public void buildSCMElement(Node scmNode) {

        Node locationNode = getChildNodeWithLabel(scmNode, "locations");
        if (locationNode == null) {
            throw new ExportException("A location section must be set in the SCM SVM section.");
        }

        Node moduleNode = getChildNodeWithLabel(locationNode, "hudson.scm.SubversionSCM_-ModuleLocation");
        if (moduleNode == null) {
            throw new ExportException("A module section must be set in the SCM SVM section.");
        }

        Node remoteNode = getChildNodeWithLabel(moduleNode, "remote");
        if (remoteNode == null) {
            throw new ExportException("A remote section must be set in the SCM SVM section.");
        }

        this.svnURL = getContent(remoteNode);
    }
}
