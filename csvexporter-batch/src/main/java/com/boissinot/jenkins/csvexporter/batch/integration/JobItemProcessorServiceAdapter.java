package com.boissinot.jenkins.csvexporter.batch.integration;

import com.boissinot.jenkins.csvexporter.domain.InputSBJobObj;
import com.boissinot.jenkins.csvexporter.domain.OutputCSVJobObj;
import com.boissinot.jenkins.csvexporter.exception.ExportException;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.integration.Message;
import org.springframework.integration.MessagingException;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.support.MessageBuilder;


/**
 * @author Gregory Boissinot
 */
public class JobItemProcessorServiceAdapter {

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
                throw new ExportException("Item was filtered or time out occurred.");
            }

            Message<OutputCSVJobObj> returnMessage =
                    MessageBuilder
                            .withPayload(output)
                            .copyHeaders(inputSBJobObjMessage.getHeaders())
                            .build();
            return returnMessage;
        } catch (MessagingException me) {
            throw new ExportException("Unexpected exception on business flow", me.getCause());
        }
    }

}
