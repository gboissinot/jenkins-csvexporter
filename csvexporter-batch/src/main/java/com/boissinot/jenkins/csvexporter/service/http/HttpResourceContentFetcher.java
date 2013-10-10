package com.boissinot.jenkins.csvexporter.service.http;

import com.boissinot.jenkins.csvexporter.exception.ExportException;
import org.apache.commons.io.IOUtils;
import org.springframework.http.client.CommonsClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author Gregory Boissinot
 */
public class HttpResourceContentFetcher {

    private HttpConnectionRetriever httpConnectionRetriever;

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
            if (inputStream!=null){
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
