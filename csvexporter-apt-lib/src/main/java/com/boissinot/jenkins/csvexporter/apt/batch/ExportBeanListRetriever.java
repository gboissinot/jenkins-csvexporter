package com.boissinot.jenkins.csvexporter.apt.batch;

import com.boissinot.jenkins.csvexporter.apt.ExportElement;

import javax.lang.model.element.Element;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * @author Gregory Boissinot
 */
public class ExportBeanListRetriever {

    public SortedSet<ExportBean> buildExportBeanList(final Set<? extends Element> elementsAnnotatedWithExportElementAnnotation) {
        SortedSet<ExportBean> sortedSet = new TreeSet<ExportBean>();
        for (Element element1 : elementsAnnotatedWithExportElementAnnotation) {
            String label = element1.getAnnotation(ExportElement.class).label();
            int order = element1.getAnnotation(ExportElement.class).order();
            sortedSet.add(new ExportBean(order, element1.getSimpleName().toString(), label));
        }
        return sortedSet;
    }

}
