package com.boissinot.jenkins.csvexporter.batch.loader;

import java.util.Map;

/**
 * @author Gregory Boissinot
 */
public interface ContextExtractorExtensionPoint {

    public String getKey();

    public Map<String, String> getContextualInfo();

}
