package com.boissinot.jenkins.csvexporter.apt.processor;

import com.boissinot.jenkins.csvexporter.apt.ExportElement;
import com.boissinot.jenkins.csvexporter.apt.batch.ExportBean;

import javax.lang.model.element.Element;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * @author Gregory Boissinot
 */
public class ExportBeanListRetriever {

    public SortedSet<ExportBean> buildExportBeanList(final Set<? extends Element> annotatedFieldSet) {
        SortedSet<ExportBean> sortedSet = new TreeSet<ExportBean>();
        for (Element annotatedField : annotatedFieldSet) {
            final ExportElement exportElement = annotatedField.getAnnotation(ExportElement.class);
            String label = exportElement.label();
            int order = exportElement.order();
            sortedSet.add(new ExportBean(order, annotatedField.getSimpleName().toString(), label));
        }
        return sortedSet;
    }

}
