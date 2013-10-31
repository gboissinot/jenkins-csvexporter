package com.boissinot.jenkins.csvexporter.service.extractor.maven.pom.aspect;

import com.boissinot.jenkins.csvexporter.domain.jenkins.job.ConfigJob;
import com.boissinot.jenkins.csvexporter.service.extractor.maven.pom.RemotePOMURLStrategy;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author Gregory Boissinot
 */
@Component
@Aspect
public class PomExtractionLogger {

    private Logger logger = LoggerFactory.getLogger(PomExtractionLogger.class);

    @Pointcut("within(com.boissinot.jenkins.csvexporter.service.extractor.maven.pom.DeveloperInfoRetriever) && args(configJob, objects)")
    private void getDevelopersPointcut(ConfigJob configJob, Object... objects) {
    }

    @Before(value = "getDevelopersPointcut(configJob, objects)")
    private void logGetDevelopers(ConfigJob configJob, Object... objects) {
        if (logger.isDebugEnabled()) {
            String jobName = configJob.getName();
            logger.debug(String.format("Extracting POM URL for %s job.", jobName));
        }
    }

    @Pointcut("execution(String com.boissinot.jenkins.csvexporter.service.extractor.maven.pom.RemotePOMURLStrategy.getRemotePomURL(*,*)) && args(configJob, objects) && target(remoteStrategy)")
    private void remotePomURLPointcut(ConfigJob configJob, RemotePOMURLStrategy remoteStrategy, Object... objects) {
    }

    @AfterReturning(value = "remotePomURLPointcut(configJob, remoteStrategy,objects)", returning = "pomRemoteURL")
    private void logGetRemotePomURLPointcut(ConfigJob configJob, RemotePOMURLStrategy remoteStrategy, String pomRemoteURL, Object... objects) {
        if (logger.isDebugEnabled()) {
            String jobName = configJob.getName();
            String beanName = remoteStrategy.getBeanName();
            logger.debug(String.format("Using Spring bean %s.", beanName));
            logger.debug(String.format("Extracted POM content for '%s' job.", jobName));
            if (pomRemoteURL == null) {
                logger.debug(String.format("Can't compute the remote %s POM URL", remoteStrategy.getType()));
                return;
            }
            logger.debug(String.format("The remote POM URL(%s URL) is '%s'.", remoteStrategy.getType(), pomRemoteURL));
        }
    }

}
