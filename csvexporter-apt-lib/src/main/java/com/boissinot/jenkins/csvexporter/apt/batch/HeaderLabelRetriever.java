package com.boissinot.jenkins.csvexporter.apt.batch;

import java.util.SortedSet;

/**
 * @author Gregory Boissinot
 */
public class HeaderLabelRetriever {

    public String getNameLabels(SortedSet<ExportBean> elements) {

        StringBuilder stringBuilder = new StringBuilder();
        for (ExportBean element : elements) {
            stringBuilder.append(";");
            stringBuilder.append(element.label);
        }
        stringBuilder.delete(0, 1);
        return stringBuilder.toString();
    }

}
