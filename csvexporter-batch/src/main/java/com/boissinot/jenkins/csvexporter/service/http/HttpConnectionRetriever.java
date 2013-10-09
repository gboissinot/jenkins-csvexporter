package com.boissinot.jenkins.csvexporter.service.http;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;

/**
 * @author Gregory Boissinot
 *
 * Version Control System (VCS) Retriever for CVS
 */
public class HttpConnectionRetriever {

    private String nonProxyHost;
    private String httpProxyHost;
    private int httpProxyPort;

    public HttpConnectionRetriever(String nonProxyHost, String httpProxyHost, int httpProxyPort) {
        this.nonProxyHost = nonProxyHost;
        this.httpProxyHost = httpProxyHost;
        this.httpProxyPort = httpProxyPort;
    }

    public HttpURLConnection getConnection(String httpURL) throws IOException {
        URL url = new URL(httpURL);
        HttpURLConnection httpURLConnection;
        //TODO
//        if (httpURL.startsWith(nonProxyHost)) {
        if (httpURL.contains("http://controle/") ||  httpURL.contains("http://calypso/")){
            httpURLConnection = (HttpURLConnection) url.openConnection();
        } else {
            Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(httpProxyHost, httpProxyPort));
            httpURLConnection = (HttpURLConnection) url.openConnection(proxy);
        }
        return httpURLConnection;
    }
}
