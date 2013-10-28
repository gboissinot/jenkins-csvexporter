package com.boissinot.jenkins.csvexporter.batch;

import com.boissinot.jenkins.csvexporter.domain.InputSBJobObj;
import com.boissinot.jenkins.csvexporter.domain.JobMessageHeaders;
import com.boissinot.jenkins.csvexporter.domain.OutputCSVJobObj;
import com.boissinot.jenkins.csvexporter.exception.ExportException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.integration.Message;
import org.springframework.integration.MessageChannel;
import org.springframework.integration.core.MessagePostProcessor;
import org.springframework.integration.core.MessagingTemplate;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.util.Assert;


/**
 * @author Gregory Boissinot
 */
public class JobItemProcessor implements ItemProcessor<InputSBJobObj, OutputCSVJobObj>, InitializingBean {

    private final Logger logger = LoggerFactory.getLogger(JobItemProcessor.class);
    @Autowired
    private MessageChannel inputChannel;
    @Autowired
    @Value("#{jobParameters['update.email.filepath']}")
    private String updateEmailFilePath;

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(inputChannel, "The inputChannel must be set");
        Assert.notNull(updateEmailFilePath, "The update email file path must be set");
    }

    @Override
    public OutputCSVJobObj process(InputSBJobObj inputSBJobObj) throws Exception {
        logger.info(String.format("Processing '%s'", inputSBJobObj.getJobName()));
        MessagingTemplate messagingTemplate = new MessagingTemplate();
        messagingTemplate.setReceiveTimeout(5000l);
        Object result = messagingTemplate.convertSendAndReceive(inputChannel, inputSBJobObj, new MessagePostProcessor() {
            @Override
            public Message<?> postProcessMessage(Message<?> message) {
                return MessageBuilder.fromMessage(message)
                        .setHeader(JobMessageHeaders.HEADER_EMAIL_FILE_PATH, updateEmailFilePath)
                        .build();
            }
        });

        //Case of message filter or timeout
        if (result == null) {
            throw new ExportException("Item was filtered or time out occurred.");
        }

        if (result instanceof OutputCSVJobObj) {
            return (OutputCSVJobObj) result;
        }

        if (result instanceof Throwable) {
            throw new ExportException("Unexpected exception on business flow", (Throwable) result);
        }

        throw new ExportException("Unexpected use case.");
    }

}
