package com.boissinot.jenkins.csvexporter.service.extractor;

import com.boissinot.jenkins.csvexporter.domain.OutputCSVJobObj;
import com.boissinot.jenkins.csvexporter.exception.ExportException;
import org.springframework.integration.Message;
import org.springframework.integration.MessageHeaders;
import org.springframework.xml.transform.StringSource;
import org.springframework.xml.xpath.Jaxp13XPathTemplate;
import org.w3c.dom.Node;

import javax.xml.transform.Source;

import static com.boissinot.jenkins.csvexporter.domain.JobMessageHeaders.*;

/**
 * @author Gregory Boissinot
 */
public abstract class JobExtractorSupport extends CommonElementRetriever implements JobExtractor {


    public OutputCSVJobObj getCVSObj(Message jobMessage) {

        MessageHeaders headers = jobMessage.getHeaders();
        String jobName = headers.get(HEADER_JOB_NAME, String.class);
        String functionalJobType = headers.get(HEADER_FUNCTIONAL_JOB_TYPE, String.class);
        String jenkinsJobType = headers.get(HEADER_JENKINS_JOB_TYPE, String.class);
        String functionalJobLanguage = headers.get(HEADER_FUNCTIONAL_JOB_LANGUAGE, String.class);

        OutputCSVJobObj.Builder builder = new OutputCSVJobObj.Builder();

        String configXML = (String) jobMessage.getPayload();
        if (configXML == null) {
            throw new ExportException(String.format("'%s' job name must be set.", jobName));
        }
        if (configXML.trim().length() == 0) {
            throw new ExportException(String.format("'%s' job name is empty.", jobName));
        }

        Jaxp13XPathTemplate template = new Jaxp13XPathTemplate();
        Source configXMLSource = new StringSource(configXML);

        String description = template.evaluateAsString("//description", configXMLSource);
        String spec = template.evaluateAsString("//spec", configXMLSource);

        Node disabledNode = template.evaluateAsNode("//disabled", configXMLSource);
        boolean disabled = Boolean.valueOf(getContent(disabledNode));

        Node scmNode = template.evaluateAsNode("//scm ", configXMLSource);

        builder.name(jobName)
                .jenkinsType(jenkinsJobType)
                .functionalJobType(functionalJobType)
                .functionalJobLanguage(functionalJobLanguage)
                .disabled(disabled)
                .desc(description)
                .trigger(spec)
                .scm(scmNode);


        buildCVSObj(builder, configXML);

        return new OutputCSVJobObj(builder);
    }

    protected abstract void buildCVSObj(OutputCSVJobObj.Builder builder, String configXML);

}
