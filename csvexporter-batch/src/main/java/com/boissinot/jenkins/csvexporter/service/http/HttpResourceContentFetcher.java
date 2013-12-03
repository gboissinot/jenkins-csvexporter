package com.boissinot.jenkins.csvexporter.service.http;

import com.boissinot.jenkins.csvexporter.exception.ExportException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

/**
 * @author Gregory Boissinot
 */
public class HttpResourceContentFetcher {

    private final HttpConnectionRetriever httpConnectionRetriever;

    public HttpResourceContentFetcher(HttpConnectionRetriever httpConnectionRetriever) {
        this.httpConnectionRetriever = httpConnectionRetriever;
    }

    public String getContent(String httpURL) throws ExportException {

        StringBuilder builder = new StringBuilder();
        HttpURLConnection conn = null;
        InputStream inputStream = null;
        InputStreamReader in = null;
        BufferedReader br = null;
        try {
            conn = httpConnectionRetriever.getConnection(httpURL);

            if (httpURL.startsWith("https://riouxsvn.com/svn/fusion-soleil/")) {
                String username = "qa-soleil";
                String password = "impega";
                String userpass = username + ":" + password;
                String basicAuth = "Basic " + javax.xml.bind.DatatypeConverter.printBase64Binary(userpass.getBytes());
                conn.setRequestProperty("Authorization", basicAuth);
            }

            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.setDoOutput(false);
            conn.connect();
            inputStream = conn.getInputStream();

            in = new InputStreamReader(inputStream);
            br = new BufferedReader(in);
            int c;
            while ((c = br.read()) != -1) {
                builder.append((char) c);
            }
        } catch (IOException ioe) {
            throw new ExportException(ioe);
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException ioe) {
                    throw new ExportException(ioe);
                }
            }
            if (in != null) {
                try {
                    in.close();
                } catch (IOException ioe) {
                    throw new ExportException(ioe);
                }
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException ioe) {
                    throw new ExportException(ioe);
                }
            }
            if (conn != null) {
                conn.disconnect();
            }
        }

        return builder.toString();
    }
}
