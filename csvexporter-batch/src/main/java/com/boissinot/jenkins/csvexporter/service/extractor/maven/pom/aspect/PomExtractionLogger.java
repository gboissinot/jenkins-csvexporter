package com.boissinot.jenkins.csvexporter.service.extractor.maven.pom.aspect;

import com.boissinot.jenkins.csvexporter.domain.jenkins.job.ConfigJob;
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


    @Pointcut("bean(svnRemotePOMURLStrategy) && args(configJob, objects)")
    private void svnGetRemotePomURLPointcut(ConfigJob configJob, Object... objects) {
    }

    @Pointcut("bean(gitRemotePOMURLStrategy) && args(configJob, objects)")
    private void gitGetRemotePomURLPointcut(ConfigJob configJob, Object... objects) {
    }

    @Pointcut("bean(cvsRemotePOMURLStrategy) && args(configJob, objects)")
    private void cvsGetRemotePomURLPointcut(ConfigJob configJob, Object... objects) {
    }

    @AfterReturning(value = "svnGetRemotePomURLPointcut(configJob, objects)", returning = "pomRemoteURL")
    private void svnLogGetRemotePomURLPointcut(ConfigJob configJob, String pomRemoteURL, Object... objects) {
        if (logger.isDebugEnabled()) {
            String jobName = configJob.getName();
            logger.debug(String.format("Extracted POM Content for %s job with a SVN URL.", jobName));
            if (pomRemoteURL == null) {
                logger.debug("Can't compute the SVN URL");
                return;
            }
            logger.debug(String.format("The SVN URL is %s.", pomRemoteURL));
        }
    }

    @AfterReturning(value = "gitGetRemotePomURLPointcut(configJob, objects)", returning = "pomRemoteURL")
    private void gitLogGetRemotePomURLPointcut(ConfigJob configJob, String pomRemoteURL, Object... objects) {
        if (logger.isDebugEnabled()) {
            String jobName = configJob.getName();
            logger.debug(String.format("Extracted POM Content for %s job with a GIT URL.", jobName));
            if (pomRemoteURL == null) {
                logger.debug("Can't compute the GIT URL");
                return;
            }
            logger.debug(String.format("The GIT URL is %s.", pomRemoteURL));
        }
    }

    @AfterReturning(value = "cvsGetRemotePomURLPointcut(configJob, objects)", returning = "pomRemoteURL")
    private void cvsLogGetRemotePomURLPointcut(ConfigJob configJob, String pomRemoteURL, Object... objects) {
        if (logger.isDebugEnabled()) {
            String jobName = configJob.getName();
            logger.debug(String.format("Extracted POM Content for %s job with a CVS URL.", jobName));
            if (pomRemoteURL == null) {
                logger.debug("Can't compute the CVS URL");
                return;
            }
            logger.debug(String.format("The CVS URL is %s.", pomRemoteURL));
        }
    }

}
