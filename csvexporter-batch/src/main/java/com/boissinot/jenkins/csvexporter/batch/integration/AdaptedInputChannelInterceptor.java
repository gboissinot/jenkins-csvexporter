package com.boissinot.jenkins.csvexporter.batch.integration;

import com.boissinot.jenkins.csvexporter.domain.JobMessageHeaders;
import org.springframework.integration.Message;
import org.springframework.integration.MessageChannel;
import org.springframework.integration.channel.interceptor.ChannelInterceptorAdapter;
import org.springframework.integration.support.MessageBuilder;

/**
 * @author Gregory Boissinot
 */
public class AdaptedInputChannelInterceptor extends ChannelInterceptorAdapter {

    private String emailFilePath;

    public AdaptedInputChannelInterceptor(String emailFilePath) {
        this.emailFilePath = emailFilePath;
    }

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        Message targetMessageFLow = MessageBuilder
                .fromMessage(message)
                .setHeader(JobMessageHeaders.HEADER_EMAIL_FILE_PATH, emailFilePath)
                .build();
        return targetMessageFLow;
    }


}
