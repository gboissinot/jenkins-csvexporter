package com.boissinot.jenkins.csvexporter.service.extractor.maven.pom;

import com.boissinot.jenkins.csvexporter.domain.jenkins.job.ConfigJob;
import com.boissinot.jenkins.csvexporter.maven.extractor.POMRemoteObj;

import java.util.Map;

/**
 * @author Gregory Boisisnot
 */
public class POMFileInfoExtractor {

    private String csvViewerRootUrl;

    private Map<String, String> cvsModuleMap;

    public POMFileInfoExtractor(Map<String, String> cvsModuleMap, String csvViewerRootUrl) {
        if (cvsModuleMap == null) {
            throw new NullPointerException("A CVS Module map is required.");
        }
        this.cvsModuleMap = cvsModuleMap;
        this.csvViewerRootUrl = csvViewerRootUrl;
    }

    public POMRemoteObj getPOMUrls(ConfigJob configJob) {

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
            String modulePath = cvsModuleMap.get(cvsModule) == null ? cvsModule : cvsModuleMap.get(cvsModule);
            if (configJob.getCvsBranche() != null) {
                return new POMRemoteObj(configJob.getJobName(), csvViewerRootUrl + modulePath + "/pom.xml?revision=" + configJob.getCvsBranche());
            } else {
                return new POMRemoteObj(configJob.getJobName(), csvViewerRootUrl + modulePath + "/pom.xml?view=co");
            }
        }

        return null;
    }
}
