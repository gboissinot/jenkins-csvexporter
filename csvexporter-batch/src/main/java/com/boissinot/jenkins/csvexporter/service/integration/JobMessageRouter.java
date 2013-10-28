package com.boissinot.jenkins.csvexporter.service.integration;

import org.springframework.integration.annotation.ServiceActivator;

/**
 * @author Gregory Boissinot
 */
public class JobMessageRouter {

    private static final String matrixHeader = "<matrix-project>";
    private static final String mavenHeader = "<maven2-moduleset>";

    @ServiceActivator
    @SuppressWarnings("unused")
    public String getNextChannel(String configXML) {

        if (configXML.contains(matrixHeader)) {
            return "matrixJobsChannel";
        }

        if (configXML.contains(mavenHeader)) {
            return "mavenJobsChannel";
        }

        return "freestyleJobsChannel";
    }

}
