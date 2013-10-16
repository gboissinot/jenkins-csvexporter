package com.boissinot.jenkins.csvexporter.integration;

import com.boissinot.jenkins.csvexporter.domain.OutputCSVJobObj;

/**
 * @author Gregory Boissinot
 */
public class EmailMapViewer {

    public void display(OutputCSVJobObj outputCSVJobObj) {
        System.out.println(outputCSVJobObj.getName() + "-->" + outputCSVJobObj.getDevelopers());
    }
}
