package com.boissinot.jenkins.csvexporter.service.extractor.maven.pom;

import com.boissinot.jenkins.csvexporter.domain.jenkins.job.ConfigJob;

import java.util.Map;

/**
 * @author Gregory Boisisnot
 */
public class POMFileInfoExtractor {

    private String csvViewerRootUrl;

    public void setCsvViewerRootUrl(String csvViewerRootUrl) {
        this.csvViewerRootUrl = csvViewerRootUrl;
    }

    public String getPomUrl(ConfigJob configJob, Map<String, Map<String, String>> contextModuleMap) {

        //Remove Template JOBs
        if (configJob.getName().contains("template")) {
            System.out.println("Exclude Template JOB");
            return null;
        }

        if (configJob.getSvnURL() != null) {
            return configJob.getSvnURL() + "/pom.xml";
        }

        if (configJob.getGitURL() != null) {
            System.out.println("GIT URL with pom.xml " + configJob.getGitURL() + "/pom.xml");
            return null;
        }

        if (configJob.getCvsModule() != null) {
            String cvsModule = configJob.getCvsModule();
            final Map<String, String> cvsMap = contextModuleMap.get("CVS");
            if (cvsMap != null) {
                String modulePath = cvsMap.get(cvsModule) == null ? cvsModule : cvsMap.get(cvsModule);
                if (configJob.getCvsBranche() != null) {
                    return csvViewerRootUrl + modulePath + "/pom.xml?revision=" + configJob.getCvsBranche();
                } else {
                    return csvViewerRootUrl + modulePath + "/pom.xml?view=co";
                }
            }
        }

        return null;
    }
}
