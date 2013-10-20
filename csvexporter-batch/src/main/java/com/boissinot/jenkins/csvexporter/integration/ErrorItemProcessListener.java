package com.boissinot.jenkins.csvexporter.integration;

import com.boissinot.jenkins.csvexporter.domain.InputSBJobObj;
import com.boissinot.jenkins.csvexporter.domain.OutputCSVJobObj;
import org.springframework.batch.core.ItemProcessListener;

/**
 * @author Gregory Boissinot
 */
public class ErrorItemProcessListener implements ItemProcessListener<InputSBJobObj, OutputCSVJobObj> {

    @Override
    public void beforeProcess(InputSBJobObj item) {
    }

    @Override
    public void afterProcess(InputSBJobObj item, OutputCSVJobObj result) {
        if (result == null) {
            System.out.println("Skipping item.");
        }
    }

    @Override
    public void onProcessError(InputSBJobObj item, Exception e) {
    }
}
