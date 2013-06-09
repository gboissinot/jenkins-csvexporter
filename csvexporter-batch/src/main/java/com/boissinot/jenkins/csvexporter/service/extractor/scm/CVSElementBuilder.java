package com.boissinot.jenkins.csvexporter.service.extractor.scm;

import com.boissinot.jenkins.csvexporter.exception.ExportException;
import org.w3c.dom.Node;

/**
 * @author Gregory Boissinot
 */
public class CVSElementBuilder extends SCMBuilder {

    private String cvsRoot;

    private String cvsModule;

    public String getCvsRoot() {
        return cvsRoot;
    }

    public String getCvsModule() {
        return cvsModule;
    }

    public void buildSCMElement(Node scmNode) {

        Node cvsRootNode = getChildNodeWithLabel(scmNode, "cvsroot");
        if (cvsRootNode == null) {
            throw new ExportException("A location section must be set in the SCM CVS section.");
        }

        Node moduleNode = getChildNodeWithLabel(scmNode, "module");
        if (moduleNode == null) {
            throw new ExportException("A module section must be set in the SCM CVS section.");
        }

        this.cvsRoot = getContent(cvsRootNode);
        this.cvsModule = getContent(moduleNode);
    }
}
