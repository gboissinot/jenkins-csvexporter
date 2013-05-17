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
public class BeanFieldRetrieverTest {

    private BeanFieldRetriever beanFieldRetriever;

    @Before
    public void buildBeanFieldRetriever() {
        beanFieldRetriever = new BeanFieldRetriever();
    }

    @Test
    public void getNamesEmptyElement() {
        String names = beanFieldRetriever.getNames(new TreeSet<ExportBean>());
        Assert.assertThat(names, is(""));
    }

    @Test
    public void getNamesOneElement() {
        ExportBean exportBean = new ExportBean(1, "elt", "LABEL");
        SortedSet<ExportBean> beans = new TreeSet<ExportBean>();
        beans.add(exportBean);
        final String nameLabels = beanFieldRetriever.getNames(beans);
        Assert.assertThat(nameLabels, is("\"elt\""));
    }

    @Test
    public void getNamesTwoElements() {
        ExportBean exportBean1 = new ExportBean(1, "elt1", "LABEL1");
        ExportBean exportBean2 = new ExportBean(2, "elt2", "LABEL2");
        SortedSet<ExportBean> beans = new TreeSet<ExportBean>();
        beans.add(exportBean1);
        beans.add(exportBean2);
        final String nameLabels = beanFieldRetriever.getNames(beans);
        Assert.assertThat(nameLabels, is("\"elt1\",\"elt2\""));
    }

    @Test
    public void getNamesThreeElements() {
        ExportBean exportBean1 = new ExportBean(1, "elt1", "LABEL1");
        ExportBean exportBean2 = new ExportBean(2, "elt2", "LABEL2");
        ExportBean exportBean3 = new ExportBean(3, "elt3", "LABEL3");
        SortedSet<ExportBean> beans = new TreeSet<ExportBean>();
        beans.add(exportBean1);
        beans.add(exportBean2);
        beans.add(exportBean3);
        final String nameLabels = beanFieldRetriever.getNames(beans);
        Assert.assertThat(nameLabels, is("\"elt1\",\"elt2\",\"elt3\""));
    }
}
