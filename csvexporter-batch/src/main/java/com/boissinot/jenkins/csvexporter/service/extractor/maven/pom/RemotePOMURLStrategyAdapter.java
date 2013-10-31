package com.boissinot.jenkins.csvexporter.service.extractor.maven.pom;

import org.springframework.beans.factory.BeanNameAware;

/**
 * @author Gregory Boissinot
 */
public abstract class RemotePOMURLStrategyAdapter implements RemotePOMURLStrategy, BeanNameAware {

    private String beanName;

    @Override
    public void setBeanName(String name) {
        this.beanName = name;
    }

    public String getBeanName() {
        return beanName;
    }
}
