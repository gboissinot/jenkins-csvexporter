package com.boissinot.jenkins.csvexporter.service.extractor.jenkins.scm;

import com.sun.org.apache.xerces.internal.dom.DeferredElementNSImpl;
import org.w3c.dom.Node;

/**
 * @author Gregory Boissinot
 */
public class SCMElementBuilder {

    String cvsRoot = null;
    String cvsModule = null;
    String cvsBranche = null;
    String svnURL = null;
    String gitURL = null;

    public String getCvsRoot() {
        return cvsRoot;
    }

    public String getCvsModule() {
        return cvsModule;
    }

    public String getCvsBranche() {
        return cvsBranche;
    }

    public String getSvnURL() {
        return svnURL;
    }

    public String getGitURL() {
        return gitURL;
    }

    public void build(Node scmNode) {
        String scmClassElement = ((DeferredElementNSImpl) scmNode).getAttribute("class");
        if ("hudson.scm.CVSSCM".equals(scmClassElement)) {
            CVSElementBuilder cvsElementBuilder = new CVSElementBuilder();
            cvsElementBuilder.buildSCMElement(scmNode);
            cvsRoot = cvsElementBuilder.getCvsRoot();
            cvsModule = cvsElementBuilder.getCvsModule();
            cvsBranche = cvsElementBuilder.getCvsBranche();
        } else if ("hudson.scm.SubversionSCM".equals(scmClassElement)) {
            SVNElementBuilder svnElementBuilder = new SVNElementBuilder();
            svnElementBuilder.buildSCMElement(scmNode);
            svnURL = svnElementBuilder.getSvnURL();
        } else if ("hudson.plugins.git.GitSCM".equals(scmClassElement)) {
            GITElementBuilder gitElementBuilder = new GITElementBuilder();
            gitElementBuilder.buildSCMElement(scmNode);
            gitURL = gitElementBuilder.getGitURL();
        }
    }
}
