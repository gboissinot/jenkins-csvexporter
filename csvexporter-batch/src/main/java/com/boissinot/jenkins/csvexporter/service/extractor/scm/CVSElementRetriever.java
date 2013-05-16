package com.boissinot.jenkins.csvexporter.service.extractor.scm;

import com.boissinot.jenkins.csvexporter.domain.OutputCSVJobObj;
import com.boissinot.jenkins.csvexporter.exception.ExportException;
import com.boissinot.jenkins.csvexporter.service.extractor.CommonElementRetriever;
import org.w3c.dom.Node;

/**
 * @author Gregory Boissinot
 */
public class CVSElementRetriever extends CommonElementRetriever {

    public void setSCMSection(OutputCSVJobObj.Builder builder, Node scmNode) {

        Node cvsrootNode = getLabelNode(scmNode, "cvsroot");
        if (cvsrootNode == null) {
            throw new ExportException("A location section must be set in the SCM CVS section.");
        }
        Node moduleNode = getLabelNode(scmNode, "module");
        if (moduleNode == null) {
            throw new ExportException("A module section must be set in the SCM CVS section.");
        }

        builder.cvsRoot(cvsrootNode.getTextContent());
        builder.cvsModule(moduleNode.getTextContent());
    }

}
