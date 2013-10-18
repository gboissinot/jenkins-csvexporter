package com.boissinot.jenkins.csvexporter.integration;

/**
 * @author Gregory Boissinot
 */
public class JobMessageRouter {

    private static final String matrixHeader = "<matrix-project>";
    private static final String mavenHeader = "<maven2-moduleset>";

    @SuppressWarnings("unused")
    public String getNextChannel(String configXML) {

        if (configXML.contains(matrixHeader)) {
            return "matrixJobs";
        }

        if (configXML.contains(mavenHeader)) {
            return "mavenJobs";
        }

        return "freestyleJobs";
    }

}
