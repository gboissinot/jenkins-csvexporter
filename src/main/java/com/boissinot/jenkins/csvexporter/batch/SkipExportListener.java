package com.boissinot.jenkins.csvexporter.batch;


import com.boissinot.jenkins.csvexporter.domain.InputSBJobObj;
import org.springframework.batch.core.listener.SkipListenerSupport;

/**
 * @author Gregory Boissinot
 */
public class SkipExportListener extends SkipListenerSupport {

    @Override
    public void onSkipInProcess(Object item, Throwable t) {
        System.out.println("\nSKIPPED...");
        System.out.println(item);
        if (item instanceof InputSBJobObj) {
            InputSBJobObj jobElement = (InputSBJobObj) item;
            System.out.println("job name:" + jobElement.getJobName());
            System.out.println();
            System.out.println(jobElement.getConfigXML());
            t.printStackTrace();
        }
    }

}
