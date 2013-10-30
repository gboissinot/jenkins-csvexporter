package com.boissinot.jenkins.csvexporter.javaconfig;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author Gregory Boissinot
 */
@Configuration
@ComponentScan({"com.boissinot.jenkins.csvexporter.service.integration.aspect", "com.boissinot.jenkins.csvexporter.batch.aspect", "com.boissinot.jenkins.csvexporter.service.extractor.maven.pom.aspect"})
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class AspectConfig {
}
