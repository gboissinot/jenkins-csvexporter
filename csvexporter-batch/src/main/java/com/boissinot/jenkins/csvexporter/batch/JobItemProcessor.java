package com.boissinot.jenkins.csvexporter.batch;

import com.boissinot.jenkins.csvexporter.domain.InputSBJobObj;
import com.boissinot.jenkins.csvexporter.domain.JobMessageHeaders;
import com.boissinot.jenkins.csvexporter.domain.OutputCSVJobObj;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.integration.Message;
import org.springframework.integration.MessageChannel;
import org.springframework.integration.MessageHeaders;
import org.springframework.integration.core.MessagePostProcessor;
import org.springframework.integration.core.MessagingTemplate;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.util.Assert;


/**
 * @author Gregory Boissinot
 */
public class JobItemProcessor implements ItemProcessor<InputSBJobObj, OutputCSVJobObj>, InitializingBean {

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
        MessagingTemplate messagingTemplate = new MessagingTemplate();
        return (OutputCSVJobObj) messagingTemplate.convertSendAndReceive(inputChannel, inputSBJobObj, new MessagePostProcessor() {
            @Override
            public Message<?> postProcessMessage(Message<?> message) {
                return MessageBuilder.fromMessage(message)
                        .setHeader(MessageHeaders.REPLY_CHANNEL, "inputChannel")
                        .setHeader(MessageHeaders.EXPIRATION_DATE, 5000l)
                        .setHeader(JobMessageHeaders.HEADER_EMAIL_FILE_PATH, updateEmailFilePath)
                        .build();
            }
        });
    }

}
