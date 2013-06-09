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
public class GITElementBuilderTest {

    private static final String GIT_SECTION_WELL_FORMED = "" +
            "    <scm class=\"hudson.plugins.git.GitSCM\">\n" +
            "        <configVersion>2</configVersion>\n" +
            "        <userRemoteConfigs>\n" +
            "            <hudson.plugins.git.UserRemoteConfig>\n" +
            "                <name>origin</name>\n" +
            "                <refspec>+refs/heads/*:refs/remotes/origin/*</refspec>\n" +
            "                <url>git://MY_GIT_PROJECT</url>\n" +
            "            </hudson.plugins.git.UserRemoteConfig>\n" +
            "        </userRemoteConfigs>\n" +
            "        <branches>\n" +
            "            <hudson.plugins.git.BranchSpec>\n" +
            "                <name>master</name>\n" +
            "            </hudson.plugins.git.BranchSpec>\n" +
            "        </branches>\n" +
            "        <disableSubmodules>false</disableSubmodules>\n" +
            "        <recursiveSubmodules>false</recursiveSubmodules>\n" +
            "        <doGenerateSubmoduleConfigurations>false</doGenerateSubmoduleConfigurations>\n" +
            "        <authorOrCommitter>false</authorOrCommitter>\n" +
            "        <clean>false</clean>\n" +
            "        <wipeOutWorkspace>false</wipeOutWorkspace>\n" +
            "        <pruneBranches>false</pruneBranches>\n" +
            "        <remotePoll>false</remotePoll>\n" +
            "        <ignoreNotifyCommit>false</ignoreNotifyCommit>\n" +
            "        <buildChooser class=\"hudson.plugins.git.util.DefaultBuildChooser\"/>\n" +
            "        <gitTool>Default</gitTool>\n" +
            "        <submoduleCfg class=\"list\"/>\n" +
            "        <relativeTargetDir></relativeTargetDir>\n" +
            "        <reference></reference>\n" +
            "        <excludedRegions></excludedRegions>\n" +
            "        <excludedUsers></excludedUsers>\n" +
            "        <gitConfigName></gitConfigName>\n" +
            "        <gitConfigEmail></gitConfigEmail>\n" +
            "        <skipTag>false</skipTag>\n" +
            "        <includedRegions></includedRegions>\n" +
            "        <scmName></scmName>\n" +
            "    </scm>";

    private static final String GIT_SECTION_WITHOUT_USERREMOTECONFIGS_1 = "" +
            "    <scm class=\"hudson.plugins.git.GitSCM\">\n" +
            "        <configVersion>2</configVersion>\n" +
            "        <branches>\n" +
            "            <hudson.plugins.git.BranchSpec>\n" +
            "                <name>master</name>\n" +
            "            </hudson.plugins.git.BranchSpec>\n" +
            "        </branches>\n" +
            "        <disableSubmodules>false</disableSubmodules>\n" +
            "        <recursiveSubmodules>false</recursiveSubmodules>\n" +
            "        <doGenerateSubmoduleConfigurations>false</doGenerateSubmoduleConfigurations>\n" +
            "        <authorOrCommitter>false</authorOrCommitter>\n" +
            "        <clean>false</clean>\n" +
            "        <wipeOutWorkspace>false</wipeOutWorkspace>\n" +
            "        <pruneBranches>false</pruneBranches>\n" +
            "        <remotePoll>false</remotePoll>\n" +
            "        <ignoreNotifyCommit>false</ignoreNotifyCommit>\n" +
            "        <buildChooser class=\"hudson.plugins.git.util.DefaultBuildChooser\"/>\n" +
            "        <gitTool>Default</gitTool>\n" +
            "        <submoduleCfg class=\"list\"/>\n" +
            "        <relativeTargetDir></relativeTargetDir>\n" +
            "        <reference></reference>\n" +
            "        <excludedRegions></excludedRegions>\n" +
            "        <excludedUsers></excludedUsers>\n" +
            "        <gitConfigName></gitConfigName>\n" +
            "        <gitConfigEmail></gitConfigEmail>\n" +
            "        <skipTag>false</skipTag>\n" +
            "        <includedRegions></includedRegions>\n" +
            "        <scmName></scmName>\n" +
            "    </scm>";

    private static final String GIT_SECTION_WITHOUT_USERREMOTECONFIGS_2 = "" +
            "    <scm class=\"hudson.plugins.git.GitSCM\">\n" +
            "        <configVersion>2</configVersion>\n" +
            "        <userRemoteConfigs>\n" +
            "        </userRemoteConfigs>\n" +
            "        <branches>\n" +
            "            <hudson.plugins.git.BranchSpec>\n" +
            "                <name>master</name>\n" +
            "            </hudson.plugins.git.BranchSpec>\n" +
            "        </branches>\n" +
            "        <disableSubmodules>false</disableSubmodules>\n" +
            "        <recursiveSubmodules>false</recursiveSubmodules>\n" +
            "        <doGenerateSubmoduleConfigurations>false</doGenerateSubmoduleConfigurations>\n" +
            "        <authorOrCommitter>false</authorOrCommitter>\n" +
            "        <clean>false</clean>\n" +
            "        <wipeOutWorkspace>false</wipeOutWorkspace>\n" +
            "        <pruneBranches>false</pruneBranches>\n" +
            "        <remotePoll>false</remotePoll>\n" +
            "        <ignoreNotifyCommit>false</ignoreNotifyCommit>\n" +
            "        <buildChooser class=\"hudson.plugins.git.util.DefaultBuildChooser\"/>\n" +
            "        <gitTool>Default</gitTool>\n" +
            "        <submoduleCfg class=\"list\"/>\n" +
            "        <relativeTargetDir></relativeTargetDir>\n" +
            "        <reference></reference>\n" +
            "        <excludedRegions></excludedRegions>\n" +
            "        <excludedUsers></excludedUsers>\n" +
            "        <gitConfigName></gitConfigName>\n" +
            "        <gitConfigEmail></gitConfigEmail>\n" +
            "        <skipTag>false</skipTag>\n" +
            "        <includedRegions></includedRegions>\n" +
            "        <scmName></scmName>\n" +
            "    </scm>";


    private static final String GIT_SECTION_WITHOUT_URL = "" +
            "    <scm class=\"hudson.plugins.git.GitSCM\">\n" +
            "        <configVersion>2</configVersion>\n" +
            "        <userRemoteConfigs>\n" +
            "            <hudson.plugins.git.UserRemoteConfig>\n" +
            "                <name>origin</name>\n" +
            "                <refspec>+refs/heads/*:refs/remotes/origin/*</refspec>\n" +
            "            </hudson.plugins.git.UserRemoteConfig>\n" +
            "        </userRemoteConfigs>\n" +
            "        <branches>\n" +
            "            <hudson.plugins.git.BranchSpec>\n" +
            "                <name>master</name>\n" +
            "            </hudson.plugins.git.BranchSpec>\n" +
            "        </branches>\n" +
            "        <disableSubmodules>false</disableSubmodules>\n" +
            "        <recursiveSubmodules>false</recursiveSubmodules>\n" +
            "        <doGenerateSubmoduleConfigurations>false</doGenerateSubmoduleConfigurations>\n" +
            "        <authorOrCommitter>false</authorOrCommitter>\n" +
            "        <clean>false</clean>\n" +
            "        <wipeOutWorkspace>false</wipeOutWorkspace>\n" +
            "        <pruneBranches>false</pruneBranches>\n" +
            "        <remotePoll>false</remotePoll>\n" +
            "        <ignoreNotifyCommit>false</ignoreNotifyCommit>\n" +
            "        <buildChooser class=\"hudson.plugins.git.util.DefaultBuildChooser\"/>\n" +
            "        <gitTool>Default</gitTool>\n" +
            "        <submoduleCfg class=\"list\"/>\n" +
            "        <relativeTargetDir></relativeTargetDir>\n" +
            "        <reference></reference>\n" +
            "        <excludedRegions></excludedRegions>\n" +
            "        <excludedUsers></excludedUsers>\n" +
            "        <gitConfigName></gitConfigName>\n" +
            "        <gitConfigEmail></gitConfigEmail>\n" +
            "        <skipTag>false</skipTag>\n" +
            "        <includedRegions></includedRegions>\n" +
            "        <scmName></scmName>\n" +
            "    </scm>";

    private Jaxp13XPathTemplate template;
    private GITElementBuilder gitElementBuilder;

    @Before
    public void initCVSElementBuilder() {
        gitElementBuilder = new GITElementBuilder();
        template = new Jaxp13XPathTemplate();
    }

    @Test(expected = ExportException.class)
    public void testWithoutUserRemoteConfigs1() {
        Source scmSource = new StringSource(GIT_SECTION_WITHOUT_USERREMOTECONFIGS_1);
        Node scmNode = template.evaluateAsNode("//scm ", scmSource);
        gitElementBuilder.buildSCMElement(scmNode);
    }

    @Test(expected = ExportException.class)
    public void testWithoutUserRemoteConfigs2() {
        Source scmSource = new StringSource(GIT_SECTION_WITHOUT_USERREMOTECONFIGS_2);
        Node scmNode = template.evaluateAsNode("//scm ", scmSource);
        gitElementBuilder.buildSCMElement(scmNode);
    }

    @Test(expected = ExportException.class)
    public void testWithoutURL() {
        Source scmSource = new StringSource(GIT_SECTION_WITHOUT_URL);
        Node scmNode = template.evaluateAsNode("//scm ", scmSource);
        gitElementBuilder.buildSCMElement(scmNode);
    }

    @Test
    public void testWellGitSection() {
        Source scmSource = new StringSource(GIT_SECTION_WELL_FORMED);
        Node scmNode = template.evaluateAsNode("//scm ", scmSource);
        gitElementBuilder.buildSCMElement(scmNode);
        Assert.assertThat(gitElementBuilder.getGitURL(), is("git://MY_GIT_PROJECT"));
    }

}
