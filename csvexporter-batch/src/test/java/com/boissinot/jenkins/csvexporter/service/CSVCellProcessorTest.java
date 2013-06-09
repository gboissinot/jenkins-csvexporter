package com.boissinot.jenkins.csvexporter.service;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;

/**
 * @author Gregory Boissinot
 */
public class CSVCellProcessorTest {

    private static CSVCellProcessor cellProcessor;

    @BeforeClass
    public static void setUp() throws Exception {
        cellProcessor = new CSVCellProcessor();
    }

    @Test
    public void testNullContent() {
        Assert.assertThat(cellProcessor.getCSVAware(null), nullValue());
    }

    @Test
    public void testEmptyContent() {
        Assert.assertThat(cellProcessor.getCSVAware(""), is(""));
    }

    @Test
    public void testNormalContent() {
        String cellContent = "abc";
        Assert.assertThat(cellProcessor.getCSVAware(cellContent), is("\"" + cellContent + "\""));
    }

    @Test
    public void testContentWithQuoute() {
        String cellContent = "ab\"c";
        Assert.assertThat(cellProcessor.getCSVAware(cellContent), is("\"ab\"\"c\""));
    }

}
