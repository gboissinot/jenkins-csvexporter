package com.boissinot.jenkins.csvexporter.service.http;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;

/**
 * @author Gregory Boissinot
 */
public class HttpConnectionRetriever {

    private final String nonProxyHosts;
    private final String httpProxyHost;
    private final int httpProxyPort;

    public HttpConnectionRetriever(String nonProxyHosts, String httpProxyHost, int httpProxyPort) {
        this.nonProxyHosts = nonProxyHosts;
        this.httpProxyHost = httpProxyHost;
        this.httpProxyPort = httpProxyPort;
    }

    public HttpURLConnection getConnection(String httpURL) throws IOException {
        URL url = new URL(httpURL);
        HttpURLConnection httpURLConnection;
        if (isShouldUseProxy(httpURL)) {
            Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(httpProxyHost, httpProxyPort));
            httpURLConnection = (HttpURLConnection) url.openConnection(proxy);
        } else {
            httpURLConnection = (HttpURLConnection) url.openConnection();
        }
        return httpURLConnection;
    }

    private boolean isShouldUseProxy(String httpURL) {
        if (nonProxyHosts == null) {
            return true;
        }
        final String[] excludedProxies = nonProxyHosts.split(",");
        for (String excludedProxy : excludedProxies) {
            if (httpURL.contains(excludedProxy)) {
                return false;
            }
        }
        return true;
    }

}
