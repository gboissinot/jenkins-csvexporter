package com.boissinot.jenkins.csvexporter.batch;

import com.boissinot.jenkins.csvexporter.service.extractor.cvs.ModulesFileExtractor;
import com.boissinot.jenkins.csvexporter.service.http.ResourceContentFetcher;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;

/**
 * @author Gregory Boissinot
 */
public class CVSModulesLoaderTasklet implements Tasklet {

    private ResourceContentFetcher resourceContentFetcher;

    private String cvsModuleURL;

    public void setResourceContentFetcher(ResourceContentFetcher resourceContentFetcher) {
        this.resourceContentFetcher = resourceContentFetcher;
    }

    public void setCvsModuleURL(String cvsModuleURL) {
        this.cvsModuleURL = cvsModuleURL;
    }

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
        String moduleContent = resourceContentFetcher.getContent(cvsModuleURL);
        ModulesFileExtractor extractor = new ModulesFileExtractor();
        ExecutionContext executionContext = chunkContext.getStepContext().getStepExecution().getExecutionContext();
        executionContext.put("moduleMap", extractor.getModulesMap(moduleContent));
        return null;
    }
}
