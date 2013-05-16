package com.boissinot.jenkins.csvexporter.service.extractor.content;

import com.boissinot.jenkins.csvexporter.domain.OutputCSVJobObj;
import com.boissinot.jenkins.csvexporter.service.extractor.scm.SCMElementRetriever;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.springframework.xml.transform.StringSource;
import org.springframework.xml.xpath.Jaxp13XPathTemplate;
import org.w3c.dom.Node;

import java.io.IOException;

/**
 * @author Gregory Boissinot
 */
public class SCMElementRetrieverTest {

    @Test
    public void testCVS1() throws IOException {
        String jobName = "maven-job-1.xml";
        OutputCSVJobObj.Builder scmContentBuilder = getSCMContent(jobName);
        OutputCSVJobObj outputCSVJobObj = new OutputCSVJobObj(scmContentBuilder);
        String expectedCVSRoot = ":ext:maven@ganymede.synchrotron-soleil.fr:/usr/local/CVS";
        String expectedCVSModule = "ContinuousIntegration/maven/packaging/OmniRoot-WIN32";
        org.junit.Assert.assertEquals(expectedCVSRoot, outputCSVJobObj.getCvsRoot());
        org.junit.Assert.assertEquals(expectedCVSModule, outputCSVJobObj.getCvsModule());
    }

    @Test
    public void testSVN1() throws IOException {
        String jobName = "freestyle-job-1.xml";
        OutputCSVJobObj.Builder scmContentBuilder = getSCMContent(jobName);
        OutputCSVJobObj outputCSVJobObj = new OutputCSVJobObj(scmContentBuilder);
        String expectedScmURL = "https://svn.code.sf.net/p/cometeapps/code/TangoBeans/AbstractTangoBean/tags/AbstractTangoBean-0.0.4";
        org.junit.Assert.assertEquals(expectedScmURL, outputCSVJobObj.getSvnURL());
    }

    private OutputCSVJobObj.Builder getSCMContent(String jobName) throws IOException {
        Jaxp13XPathTemplate template = new Jaxp13XPathTemplate();
        String configXML = new String(IOUtils.toByteArray(this.getClass().getResourceAsStream(jobName)));
        StringSource configXMLSource = new StringSource(configXML);
        Node scmSectionNode = template.evaluateAsNode("//scm ", configXMLSource);
        SCMElementRetriever scmElementRetriever = new SCMElementRetriever();
        OutputCSVJobObj.Builder builder = new OutputCSVJobObj.Builder();
        scmElementRetriever.setSCMSection(builder, scmSectionNode);
        return builder;
    }
}
