package com.boissinot.jenkins.csvexporter.batch;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import java.io.File;
import java.util.List;

/**
 * @author Gregory Boissinot
 */
public class CleanupOutputFilesTasklet implements Tasklet, InitializingBean {

    private List<String> filePath2Delete;

    public CleanupOutputFilesTasklet(List<String> filePath2Delete) {
        this.filePath2Delete = filePath2Delete;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(filePath2Delete, "A file list to delete must be set");
    }

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {

        for (String filePath : filePath2Delete) {
            File file = new File(filePath);
            if (file.exists()) {
                file.delete();
            }
        }

        return null;
    }
}
