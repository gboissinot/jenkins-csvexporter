package com.boissinot.jenkins.csvexporter.service.extractor.maven.pom;

import com.boissinot.jenkins.csvexporter.domain.jenkins.job.ConfigJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * @author Gregory Boisisnot
 */
public class POMFileInfoExtractor {

    private final Logger logger = LoggerFactory.getLogger(POMFileInfoExtractor.class);

    private String csvViewerRootUrl;

    public void setCsvViewerRootUrl(String csvViewerRootUrl) {
        this.csvViewerRootUrl = csvViewerRootUrl;
    }

    public String getPomUrl(ConfigJob configJob, Map<String, Map<String, String>> contextModuleMap) {

        final String jobName = configJob.getName();

        //Remove Template JOBs
        if (jobName.contains("template")) {
            if (logger.isDebugEnabled()) {
                logger.debug(String.format("Exclude VCS for the template JOB '%s'", jobName));
            }
            return null;
        }

        if (configJob.getSvnURL() != null) {
            final String svnURL = configJob.getSvnURL() + "/pom.xml";
            if (logger.isDebugEnabled()) {
                logger.debug(String.format("Processing job '%s' with SVN URL '%s'", jobName, svnURL));
            }
            return svnURL;
        }

        if (configJob.getGitURL() != null) {
            if (logger.isDebugEnabled()) {
                logger.debug(String.format("Exclude processing job '%s' for GIT URL '%s'", jobName, configJob.getGitURL() + "/pom.xml"));
            }
            return null;
        }

        if (configJob.getCvsModule() != null) {
            String cvsModule = configJob.getCvsModule();
            final Map<String, String> cvsMap = contextModuleMap.get("CVS");
            if (cvsMap != null) {
                String modulePath = cvsMap.get(cvsModule) == null ? cvsModule : cvsMap.get(cvsModule);
                if (configJob.getCvsBranche() != null) {
                    final String cvsURL = csvViewerRootUrl + modulePath + "/pom.xml?revision=" + configJob.getCvsBranche();
                    if (logger.isDebugEnabled()) {
                        logger.debug(String.format("Processing job '%s' with CVS URL '%s'", jobName, cvsURL));
                    }
                    return cvsURL;
                } else {
                    final String cvsURL = csvViewerRootUrl + modulePath + "/pom.xml?view=co";
                    if (logger.isDebugEnabled()) {
                        logger.debug(String.format("Processing job '%s' with CVS URL '%s'", jobName, cvsURL));
                    }
                    return cvsURL;
                }
            }
        }

        return null;
    }
}
