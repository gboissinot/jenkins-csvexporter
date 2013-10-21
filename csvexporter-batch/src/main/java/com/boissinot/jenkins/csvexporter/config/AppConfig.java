package com.boissinot.jenkins.csvexporter.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author Gregory Boissinot
 */
@Configuration
@ComponentScan("com.boissinot.jenkins.csvexporter.integration.aspect")
@EnableAspectJAutoProxy
public class AppConfig {
}
