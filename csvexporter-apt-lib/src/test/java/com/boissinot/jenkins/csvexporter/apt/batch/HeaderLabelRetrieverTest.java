package com.boissinot.jenkins.csvexporter.apt.batch;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.SortedSet;
import java.util.TreeSet;

import static org.hamcrest.CoreMatchers.is;

/**
 * @author Gregory Boissinot
 */
public class HeaderLabelRetrieverTest {

    private HeaderLabelRetriever headerLabelRetriever;

    @Before
    public void buildHeaderRetriever() {
        headerLabelRetriever = new HeaderLabelRetriever();
    }

    @Test
    public void getNameLabelsEmptyElements() {
        final String nameLabels = headerLabelRetriever.getNameLabels(new TreeSet<ExportBean>());
        Assert.assertThat(nameLabels, is(""));
    }

    @Test
    public void getNameLabelsOneElement() {
        ExportBean exportBean = new ExportBean(1, "elt", "LABEL");
        SortedSet<ExportBean> beans = new TreeSet<ExportBean>();
        beans.add(exportBean);
        final String nameLabels = headerLabelRetriever.getNameLabels(beans);
        Assert.assertThat(nameLabels, is("LABEL"));
    }

    @Test
    public void getNameLabelsTwoElements() {
        ExportBean exportBean1 = new ExportBean(1, "elt1", "LABEL1");
        ExportBean exportBean2 = new ExportBean(2, "elt2", "LABEL2");
        SortedSet<ExportBean> beans = new TreeSet<ExportBean>();
        beans.add(exportBean1);
        beans.add(exportBean2);
        final String nameLabels = headerLabelRetriever.getNameLabels(beans);
        Assert.assertThat(nameLabels, is("LABEL1;LABEL2"));
    }

    @Test
    public void getNameLabelsThreeElements() {
        ExportBean exportBean1 = new ExportBean(1, "elt1", "LABEL1");
        ExportBean exportBean2 = new ExportBean(2, "elt2", "LABEL2");
        ExportBean exportBean3 = new ExportBean(3, "elt3", "LABEL3");
        SortedSet<ExportBean> beans = new TreeSet<ExportBean>();
        beans.add(exportBean1);
        beans.add(exportBean2);
        beans.add(exportBean3);
        final String nameLabels = headerLabelRetriever.getNameLabels(beans);
        Assert.assertThat(nameLabels, is("LABEL1;LABEL2;LABEL3"));
    }

}
