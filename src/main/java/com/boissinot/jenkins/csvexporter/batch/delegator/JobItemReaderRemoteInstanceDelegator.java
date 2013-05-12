package com.boissinot.jenkins.csvexporter.batch.delegator;

import com.boissinot.jenkins.csvexporter.exception.ExportException;
import org.springframework.xml.transform.StringSource;
import org.springframework.xml.xpath.Jaxp13XPathTemplate;
import org.w3c.dom.Node;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Gregory Boissinot
 */
public class JobItemReaderRemoteInstanceDelegator implements JobItemReaderDelegator {

    private String jenkinsURL;

    public JobItemReaderRemoteInstanceDelegator(String jenkinsURL) {
        if (jenkinsURL == null) {
            throw new ExportException("A jenkins URL must be provided");
        }
        if (jenkinsURL.endsWith("/")) {
            this.jenkinsURL = jenkinsURL;
        } else {
            this.jenkinsURL = jenkinsURL + "/";
        }
    }

    public List<String> buildURLs() {
        List<String> urls = new ArrayList<String>();
        try {
            URL url = new URL(jenkinsURL + "api/xml");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.setDoOutput(false);

            StringBuilder builder = new StringBuilder();
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = br.readLine()) != null) {
                builder.append(line);
            }
            String str = builder.toString();
            System.out.println(str);

            Jaxp13XPathTemplate template = new Jaxp13XPathTemplate();
            List<Node> nodes = template.evaluateAsNodeList("//job/url", new StringSource(str));
            for (Node node : nodes) {
                urls.add(node.getTextContent());
            }

        } catch (MalformedURLException e) {
            throw new ExportException(e);
        } catch (ProtocolException e) {
            throw new ExportException(e);
        } catch (IOException e) {
            throw new ExportException(e);
        }

        return urls;
    }

    public String getJobName(String jobURL) {
        return jobURL.substring(jobURL.lastIndexOf("/job/") + 5, jobURL.length() - 1);
    }

    public String getConfigXML(String jobURL) {
        try {
            URL url = new URL(jobURL + "/config.xml");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.setDoOutput(false);
            StringBuilder builder = new StringBuilder();
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            int c;
            while ((c = br.read()) != -1) {
                builder.append((char) c);
            }
            return builder.toString();

        } catch (MalformedURLException e) {
            throw new ExportException(e);
        } catch (ProtocolException e) {
            throw new ExportException(e);
        } catch (IOException e) {
            throw new ExportException(e);
        }
    }
}
