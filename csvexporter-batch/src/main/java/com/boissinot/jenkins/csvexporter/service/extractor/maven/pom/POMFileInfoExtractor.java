package com.boissinot.jenkins.csvexporter.service.extractor.maven.pom;

import com.boissinot.jenkins.csvexporter.domain.jenkins.job.ConfigJob;
import com.boissinot.jenkins.csvexporter.maven.extractor.POMRemoteObj;

import java.util.Map;

/**
 * @author Gregory Boisisnot
 */
public class POMFileInfoExtractor {

    private String csvViewerRootUrl;

    private Map<String, Map<String, String>> contextModuleMap;

    public POMFileInfoExtractor(Map<String, Map<String, String>> contextMap, String csvViewerRootUrl) {
        if (contextMap == null) {
            throw new NullPointerException("A CVS Module map is required.");
        }
        this.contextModuleMap = contextMap;
        this.csvViewerRootUrl = csvViewerRootUrl;
    }

    public POMRemoteObj getPomUrl(ConfigJob configJob) {

        //Remove Template JOB
        if (configJob.getJobName().contains("template")) {
            System.out.println("Exclude Template JOB");
            return null;
        }

        if (configJob.getSvnURL() != null) {
            return new POMRemoteObj(configJob.getJobName(), configJob.getSvnURL() + "/pom.xml");
        }

        if (configJob.getGitURL() != null) {
            System.out.println("GIT URL with pom.xml " + configJob.getGitURL() + "/pom.xml");
            return null;
        }

        if (configJob.getCvsModule() != null) {
            String cvsModule = configJob.getCvsModule();
            final Map<String, String> cvsMap = contextModuleMap.get("cvs");
            String modulePath = cvsMap.get(cvsModule) == null ? cvsModule : cvsMap.get(cvsModule);
            if (configJob.getCvsBranche() != null) {
                return new POMRemoteObj(configJob.getJobName(), csvViewerRootUrl + modulePath + "/pom.xml?revision=" + configJob.getCvsBranche());
            } else {
                return new POMRemoteObj(configJob.getJobName(), csvViewerRootUrl + modulePath + "/pom.xml?view=co");
            }
        }

        return null;
    }
}
