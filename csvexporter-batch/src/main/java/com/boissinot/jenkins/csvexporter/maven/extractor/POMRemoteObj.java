package com.boissinot.jenkins.csvexporter.maven.extractor;

/**
 * @author Gregory Boissinot
 */
public class POMRemoteObj {

    private String originJobName;
    private String httpURL;

    public POMRemoteObj(String originJobName, String httpURL) {
        this.originJobName = originJobName;
        this.httpURL = httpURL;
    }

    public String getOriginJobName() {
        return originJobName;
    }

    public String getHttpURL() {
        return httpURL;
    }
}
