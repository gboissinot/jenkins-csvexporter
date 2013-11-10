package com.boissinot.jenkins.csvexporter.apt.batch;

import java.util.SortedSet;

/**
 * @author Gregory Boissinot
 */
public class BeanFieldRetriever {

    public String getNames(SortedSet<ExportBean> sortedSet) {

        StringBuilder stringBuilder = new StringBuilder();
        for (ExportBean exportBean : sortedSet) {
            stringBuilder.append(",");
            stringBuilder.append("\"");
            stringBuilder.append(exportBean.getName());
            stringBuilder.append("\"");
        }
        stringBuilder.delete(0, 1);
        return stringBuilder.toString();
    }
}
