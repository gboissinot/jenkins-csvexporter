package com.boissinot.jenkins.csvexporter.batch;

import com.boissinot.jenkins.csvexporter.service.http.HttpResourceContentFetcher;
import org.springframework.xml.transform.StringSource;
import org.springframework.xml.xpath.Jaxp13XPathTemplate;
import org.w3c.dom.Node;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Gregory Boissinot
 */
public class RemoteJenkinsReader implements JenkinsReaderExtensionPoint {

    private final String jenkinsURL;

    private final HttpResourceContentFetcher httpResourceContentFetcher;

    public RemoteJenkinsReader(String jenkinsURL, HttpResourceContentFetcher httpResourceContentFetcher) {
        if (jenkinsURL.endsWith("/")) {
            this.jenkinsURL = jenkinsURL;
        } else {
            this.jenkinsURL = jenkinsURL + "/";
        }
        this.httpResourceContentFetcher = httpResourceContentFetcher;
    }

    @Override
    public List<String> buildURLs() {
        List<String> urls = new ArrayList<String>();
        String str = httpResourceContentFetcher.getContent(jenkinsURL + "api/xml");

        Jaxp13XPathTemplate template = new Jaxp13XPathTemplate();
        List<Node> nodes = template.evaluateAsNodeList("//job/url", new StringSource(str));
        for (Node node : nodes) {
            String textContent = node.getTextContent();
            if (textContent.contains("fr.soleil.passerelle.SoleilActors_JAR")) {
                urls.add(textContent);
            }
        }

        return urls;
    }

    @Override
    public String getJobName(String jobURL) {
        return jobURL.substring(jobURL.lastIndexOf("/job/") + 5, jobURL.length() - 1);
    }

    @Override
    public String getConfigXML(String jobURL) {
        return httpResourceContentFetcher.getContent(jobURL);
    }
}
