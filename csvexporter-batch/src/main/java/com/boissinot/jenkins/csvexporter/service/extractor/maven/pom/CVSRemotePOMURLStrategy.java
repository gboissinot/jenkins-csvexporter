package com.boissinot.jenkins.csvexporter.service.extractor.maven.pom;

import com.boissinot.jenkins.csvexporter.domain.jenkins.job.ConfigJob;
import org.springframework.beans.factory.annotation.Required;

import java.util.Map;

/**
 * @author Gregory Boissinot
 */
public class CVSRemotePOMURLStrategy extends RemotePOMURLStrategyAdapter {

    private String csvViewerRootUrl;

    @Required
    public void setCsvViewerRootUrl(String csvViewerRootUrl) {
        this.csvViewerRootUrl = csvViewerRootUrl;
    }

    @Override
    public StrategyType getType() {
        return StrategyType.CVS;
    }

    @Override
    public String getRemotePomURL(ConfigJob configJob, Object... contextObjects) {

        if (contextObjects.length <= 0) {
            throw new IllegalArgumentException("For CVS, a CVS Map context must be given.");
        }

        if (!(contextObjects[0] instanceof Map)) {
            throw new IllegalArgumentException("For CVS, a CVS Map context must be given.");
        }

        @SuppressWarnings("unchecked")
        Map<String, Map<String, String>> contextModuleMap = (Map<String, Map<String, String>>) contextObjects[0];

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

        return null;
    }

}
