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

    private final List<String> filePathList2Delete;

    public CleanupOutputFilesTasklet(List<String> filePathList2Delete) {
        this.filePathList2Delete = filePathList2Delete;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(filePathList2Delete, "A list of file path for deletion must be set");
    }

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {

        for (String filePath : filePathList2Delete) {
            File file = new File(filePath);
            if (file.exists()) {
                file.delete();
            }
        }

        return null;
    }
}
