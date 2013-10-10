package com.boissinot.jenkins.csvexporter.batch.loader;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Gregory Boissinot
 */
public class BatchContextLoader implements Tasklet {

    private List<? extends ContextExtractor> extractorList = new ArrayList<ContextExtractor>();

    public void setExtractorList(List<? extends ContextExtractor> extractorList) {
        this.extractorList = extractorList;
    }

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        Map<String, Map<String, String>> mapContext = new HashMap<String, Map<String, String>>();
        if (extractorList != null) {
            for (ContextExtractor contextExtractor : extractorList) {
                mapContext.put(contextExtractor.getKey(), contextExtractor.getContextualInfo());
            }
        }
        ExecutionContext executionContext = chunkContext.getStepContext().getStepExecution().getExecutionContext();
        executionContext.put("mapContext", mapContext);
        return null;
    }

}
