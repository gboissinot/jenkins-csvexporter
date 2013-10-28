package com.boissinot.jenkins.csvexporter.batch;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import java.io.File;

/**
 * @author Gregory Boissinot
 */
public class CleanupOutputFilesTasklet implements Tasklet, InitializingBean {

    private final String errorCSVFilePath;
    private final String updateEmailFilePath;
    private final String logFilePath;

    public CleanupOutputFilesTasklet(String errorCSVFilePath, String updateEmailFilePath, String logFilePath) {
        this.errorCSVFilePath = errorCSVFilePath;
        this.updateEmailFilePath = updateEmailFilePath;
        this.logFilePath = logFilePath;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(errorCSVFilePath, "The error CSV file path must be set");
        Assert.notNull(updateEmailFilePath, "The update email file path must be set");
        Assert.notNull(logFilePath, "The log file path must be set");

    }

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {

        //Error CSV File
        File errorCSVFile = new File(errorCSVFilePath);
        if (errorCSVFile.exists()) {
            errorCSVFile.delete();
        }

        //Update Email File
        File updateEmailFile = new File(updateEmailFilePath);
        if (updateEmailFile.exists()) {
            updateEmailFile.delete();
        }

        //Log File
        File logFile = new File(logFilePath);
        if (logFile.exists()) {
            logFile.delete();
        }

        return null;
    }

}
