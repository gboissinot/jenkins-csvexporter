package com.boissinot.jenkins.csvexporter.service.extractor.scm;

import com.boissinot.jenkins.csvexporter.domain.OutputCSVJobObj;
import com.sun.org.apache.xerces.internal.dom.DeferredElementNSImpl;
import org.w3c.dom.Node;

/**
 * @author Gregory Boissinot
 */
public class SCMElementRetriever {

    public void setSCMSection(OutputCSVJobObj.Builder builder, Node scmNode) {

        if (scmNode == null) {
            throw new NullPointerException("A scm node must be set");
        }

        String scmClassElement = ((DeferredElementNSImpl) scmNode).getAttribute("class");
        if ("hudson.scm.CVSSCM".equals(scmClassElement)) {
            CVSElementRetriever retriever = new CVSElementRetriever();
            retriever.setSCMSection(builder, scmNode);
        } else if ("hudson.scm.SubversionSCM".equals(scmClassElement)) {
            SVNElementRetriever retriever = new SVNElementRetriever();
            retriever.setSCMSection(builder, scmNode);
        } else if ("hudson.plugins.git.GitSCM".equals(scmClassElement)) {
            GITElementRetriever retriever = new GITElementRetriever();
            retriever.setSCMSection(builder, scmNode);
        } else if ("hudson.scm.NullSCM".equals(scmClassElement)) {
            //Ignore it
        } else {
            throw new IllegalArgumentException("Only CVS and SVN are supported as SCMs");
        }

    }


}
