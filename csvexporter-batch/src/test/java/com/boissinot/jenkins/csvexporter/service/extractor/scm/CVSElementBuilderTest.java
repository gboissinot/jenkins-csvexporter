package com.boissinot.jenkins.csvexporter.service.extractor.scm;


import com.boissinot.jenkins.csvexporter.exception.ExportException;
import com.boissinot.jenkins.csvexporter.service.extractor.jenkins.scm.CVSElementBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.xml.transform.StringSource;
import org.springframework.xml.xpath.Jaxp13XPathTemplate;
import org.w3c.dom.Node;

import javax.xml.transform.Source;

import static org.hamcrest.CoreMatchers.is;

/**
 * @author Gregory Boissinot
 */
public class CVSElementBuilderTest {

    private static final String CVS_SECTION_NO_CVSROOT =
            "<scm class=\"hudson.scm.CVSSCM\">\n" +
                    "        <module>MY_MODULE</module>\n" +
                    "        <canUseUpdate>false</canUseUpdate>\n" +
                    "        <useHeadIfNotFound>false</useHeadIfNotFound>\n" +
                    "        <flatten>false</flatten>\n" +
                    "        <isTag>false</isTag>\n" +
                    "    </scm>";

    private static final String CVS_SECTION_NO_MODULE =
            "<scm class=\"hudson.scm.CVSSCM\">\n" +
                    "        <cvsroot>MY_CVS_ROOT</cvsroot>\n" +
                    "        <canUseUpdate>false</canUseUpdate>\n" +
                    "        <useHeadIfNotFound>false</useHeadIfNotFound>\n" +
                    "        <flatten>false</flatten>\n" +
                    "        <isTag>false</isTag>\n" +
                    "    </scm>";


    private static final String CVS_SECTION_WELL_FORMED =
            "<scm class=\"hudson.scm.CVSSCM\">\n" +
                    "        <cvsroot>MY_CVS_ROOT</cvsroot>\n" +
                    "        <module>MY_MODULE</module>\n" +
                    "        <canUseUpdate>false</canUseUpdate>\n" +
                    "        <useHeadIfNotFound>false</useHeadIfNotFound>\n" +
                    "        <flatten>false</flatten>\n" +
                    "        <isTag>false</isTag>\n" +
                    "    </scm>";


    private Jaxp13XPathTemplate template;
    private CVSElementBuilder cvsElementBuilder;

    @Before
    public void initCVSElementBuilder() {
        cvsElementBuilder = new CVSElementBuilder();
        template = new Jaxp13XPathTemplate();
    }

    @Test(expected = ExportException.class)
    public void testNoCVSRoot() {
        Source scmSource = new StringSource(CVS_SECTION_NO_CVSROOT);
        Node scmNode = template.evaluateAsNode("//scm ", scmSource);
        cvsElementBuilder.buildSCMElement(scmNode);
    }

    @Test(expected = ExportException.class)
    public void testNoModule() {
        Source scmSource = new StringSource(CVS_SECTION_NO_MODULE);
        Node scmNode = template.evaluateAsNode("//scm ", scmSource);
        cvsElementBuilder.buildSCMElement(scmNode);
    }

    @Test
    public void testGoodSection() {
        Source scmSource = new StringSource(CVS_SECTION_WELL_FORMED);
        Node scmNode = template.evaluateAsNode("//scm ", scmSource);
        cvsElementBuilder.buildSCMElement(scmNode);
        Assert.assertThat(cvsElementBuilder.getCvsRoot(), is("MY_CVS_ROOT"));
        Assert.assertThat(cvsElementBuilder.getCvsModule(), is("MY_MODULE"));
    }

}
