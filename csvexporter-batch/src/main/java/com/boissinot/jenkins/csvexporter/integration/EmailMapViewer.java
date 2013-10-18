package com.boissinot.jenkins.csvexporter.integration;

import com.boissinot.jenkins.csvexporter.domain.OutputCSVJobObj;
import org.springframework.integration.Message;
import org.springframework.integration.MessageChannel;
import org.springframework.integration.channel.ChannelInterceptor;
import org.springframework.integration.channel.interceptor.ChannelInterceptorAdapter;

/**
 * @author Gregory Boissinot
 */
public class EmailMapViewer extends ChannelInterceptorAdapter{

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        return super.preSend(message, channel);    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public void postSend(Message<?> message, MessageChannel channel, boolean sent) {
        super.postSend(message, channel, sent);    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public Message<?> postReceive(Message<?> message, MessageChannel channel) {
        return super.postReceive(message, channel);    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public boolean preReceive(MessageChannel channel) {
        System.out.println("TOTO");
        return true;
    }

    public void display(OutputCSVJobObj outputCSVJobObj) {
        System.out.println(outputCSVJobObj.getName() + "-->" + outputCSVJobObj.getDevelopers());
    }
}
