package com.boissinot.jenkins.csvexporter.batch;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

import java.io.File;
import java.util.List;

/**
 * @author Gregory Boissinot
 */
public class CleanupOutputFilesTasklet implements Tasklet {

    private final List<String> filePathList2Delete;

    public CleanupOutputFilesTasklet(List<String> filePathList2Delete) {
        if (filePathList2Delete == null) {
            throw new NullPointerException("A list of file path for deletion must be set");
        }
        this.filePathList2Delete = filePathList2Delete;
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
