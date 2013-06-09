package com.boissinot.jenkins.csvexporter.service.extractor.scm;

import com.boissinot.jenkins.csvexporter.exception.ExportException;
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
public class SVNElementBuilderTest {

    private Jaxp13XPathTemplate template;
    private SVNElementBuilder svnElementBuilder;

    private static final String SVN_SECTION_WELL_FORMED =
            "    <scm class=\"hudson.scm.SubversionSCM\">\n" +
                    "        <locations>\n" +
                    "            <hudson.scm.SubversionSCM_-ModuleLocation>\n" +
                    "                <remote>\n" +
                    "                    MY_SVN_URL" +
                    "                </remote>\n" +
                    "            </hudson.scm.SubversionSCM_-ModuleLocation>\n" +
                    "        </locations>\n" +
                    "    </scm>";

    private static final String SVN_SECTION_WITHOUT_LOCATION1 =
            "    <scm class=\"hudson.scm.SubversionSCM\">\n" +
                    "    </scm>";

    private static final String SVN_SECTION_WITHOUT_LOCATION2 =
            "    <scm class=\"hudson.scm.SubversionSCM\">\n" +
                    "        <locations>\n" +
                    "        </locations>\n" +
                    "    </scm>";

    private static final String SVN_SECTION_WITHOUT_REMOTE =
            "    <scm class=\"hudson.scm.SubversionSCM\">\n" +
                    "        <locations>\n" +
                    "            <hudson.scm.SubversionSCM_-ModuleLocation>\n" +
                    "            </hudson.scm.SubversionSCM_-ModuleLocation>\n" +
                    "        </locations>\n" +
                    "    </scm>";

    @Before
    public void initCVSElementBuilder() {
        svnElementBuilder = new SVNElementBuilder();
        template = new Jaxp13XPathTemplate();
    }

    @Test(expected = ExportException.class)
    public void testWithoutLocation1() {
        Source scmSource = new StringSource(SVN_SECTION_WITHOUT_LOCATION1);
        Node scmNode = template.evaluateAsNode("//scm ", scmSource);
        svnElementBuilder.buildSCMElement(scmNode);
    }

    @Test(expected = ExportException.class)
    public void testWithoutLocation2() {
        Source scmSource = new StringSource(SVN_SECTION_WITHOUT_LOCATION2);
        Node scmNode = template.evaluateAsNode("//scm ", scmSource);
        svnElementBuilder.buildSCMElement(scmNode);
    }

    @Test(expected = ExportException.class)
    public void testWithoutRemote() {
        Source scmSource = new StringSource(SVN_SECTION_WITHOUT_REMOTE);
        Node scmNode = template.evaluateAsNode("//scm ", scmSource);
        svnElementBuilder.buildSCMElement(scmNode);
    }

    @Test
    public void testExtract() {
        Source scmSource = new StringSource(SVN_SECTION_WELL_FORMED);
        Node scmNode = template.evaluateAsNode("//scm ", scmSource);
        svnElementBuilder.buildSCMElement(scmNode);
        Assert.assertThat(svnElementBuilder.getSvnURL(), is("MY_SVN_URL"));
    }

}
