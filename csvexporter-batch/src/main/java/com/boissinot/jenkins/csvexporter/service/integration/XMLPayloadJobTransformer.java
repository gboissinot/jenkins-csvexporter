package com.boissinot.jenkins.csvexporter.service.integration;

import com.boissinot.jenkins.csvexporter.domain.InputSBJobObj;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.Message;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.support.MessageBuilder;

import static com.boissinot.jenkins.csvexporter.domain.JobMessageHeaders.*;

/**
 * @author Gregory Boissinot
 */
public class XMLPayloadJobTransformer {

    private final Logger logger = LoggerFactory.getLogger(XMLPayloadJobTransformer.class);

    @ServiceActivator
    @SuppressWarnings("unused")
    public Message<String> buildJobMessage(Message<InputSBJobObj> message) {
        InputSBJobObj jobObj = message.getPayload();
        logger.info(String.format("Processing '%s'", jobObj.getJobName()));
        return MessageBuilder
                .withPayload(jobObj.getConfigXML())
                .copyHeaders(message.getHeaders())
                .setHeader(HEADER_JOB_NAME, jobObj.getJobName())
                .setHeader(HEADER_FUNCTIONAL_JOB_TYPE, jobObj.getFunctionalJobType())
                .setHeader(HEADER_FUNCTIONAL_JOB_LANGUAGE, jobObj.getFunctionalJobLanguage())
                .setHeader(HEADER_FUNCTIONAL_MODULE_MAP, jobObj.getContextMap())
                .build();
    }
}
