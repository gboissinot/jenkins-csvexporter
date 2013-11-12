package com.boissinot.jenkins.csvexporter.batch.integration;

import com.boissinot.jenkins.csvexporter.domain.InputSBJobObj;
import com.boissinot.jenkins.csvexporter.domain.OutputCSVJobObj;
import com.boissinot.jenkins.csvexporter.exception.ExportException;
import com.boissinot.jenkins.csvexporter.service.exception.ItemFilteredException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.integration.Message;
import org.springframework.integration.MessageChannel;
import org.springframework.integration.MessageHeaders;
import org.springframework.integration.MessagingException;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.support.MessageBuilder;


/**
 * @author Gregory Boissinot
 */
public class JobItemProcessorServiceAdapter {

    private Logger logger = LoggerFactory.getLogger(JobItemProcessorServiceAdapter.class.getName());

    private ItemProcessor<InputSBJobObj, OutputCSVJobObj> itemProcessor;

    @Required
    public void setItemProcessor(ItemProcessor<InputSBJobObj, OutputCSVJobObj> itemProcessor) {
        this.itemProcessor = itemProcessor;
    }

    @ServiceActivator
    @SuppressWarnings("unused")
    public Message<OutputCSVJobObj> process(Message<InputSBJobObj> inputSBJobObjMessage) throws Exception {
        OutputCSVJobObj output;
        try {
            InputSBJobObj inputSBJobObj = inputSBJobObjMessage.getPayload();
            output = itemProcessor.process(inputSBJobObj);

            //Case of message filter or timeout
            if (output == null) {
                throw new ExportException("Item technically(not managed) filtered or Time out occurred.");
            }

            return MessageBuilder
                    .withPayload(output)
                    .copyHeaders(inputSBJobObjMessage.getHeaders())
                    .build();

        } catch (MessagingException me) {
            Throwable rootCauseException = me.getCause();
            if (rootCauseException != null && rootCauseException instanceof ItemFilteredException) {
                ItemFilteredException itemFilteredException = (ItemFilteredException) rootCauseException;
                logger.info(itemFilteredException.getMessage());
                final MessageHeaders messageHeaders = inputSBJobObjMessage.getHeaders();
                final MessageChannel replyChannel = (MessageChannel) messageHeaders.get(MessageHeaders.REPLY_CHANNEL);
                //The input message from the initial gateway expect a response from its dedicated reply channel
                replyChannel.send(null);
                return null;
            }

            throw new ExportException("Unexpected exception on business flow", me.getCause());
        }
    }

}
