package com.boissinot.jenkins.csvexporter.service;

import com.boissinot.jenkins.csvexporter.exception.ExportException;
import com.boissinot.jenkins.csvexporter.service.extractor.jenkins.FunctionalJobTypeRetriever;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;


/**
 * @author Gregory Boissinot
 */
public class FunctionalJobTypeRetrieverTest {

    private static FunctionalJobTypeRetriever jobTypeRetriever;

    @BeforeClass
    public static void init() {
        jobTypeRetriever = new FunctionalJobTypeRetriever();
    }

    @Test(expected = ExportException.class)
    public void getJobTypeWithNullJobName() {
        jobTypeRetriever.getJobType(null);
    }

    @Test(expected = ExportException.class)
    public void getJobTypeWithEmptyJobName() {
        jobTypeRetriever.getJobType("");
    }

    @Test
    public void getJobType_JAR() {
        Assert.assertThat(
                jobTypeRetriever.getJobType("TEST_JAR"),
                is(FunctionalJobTypeRetriever.JOB_TYPE.JAR));
    }

    @Test
    public void getJobType_JAR_RELEASE() {
        Assert.assertThat(
                jobTypeRetriever.getJobType("TEST_JAR_RELEASE"),
                is(FunctionalJobTypeRetriever.JOB_TYPE.JAR_RELEASE));
    }

    @Test
    public void getJobType_WAR() {
        Assert.assertThat(
                jobTypeRetriever.getJobType("TEST_WAR"),
                is(FunctionalJobTypeRetriever.JOB_TYPE.WAR));
    }

    @Test
    public void getJobType_WAR_RELEASE() {
        Assert.assertThat(
                jobTypeRetriever.getJobType("TEST_WAR_RELEASE"),
                is(FunctionalJobTypeRetriever.JOB_TYPE.WAR_RELEASE));
    }

    @Test
    public void getJobType_NAR() {
        Assert.assertThat(
                jobTypeRetriever.getJobType("TEST_NAR"),
                is(FunctionalJobTypeRetriever.JOB_TYPE.CPP));
    }

    @Test
    public void getJobType_NAR_REPORT() {
        Assert.assertThat(
                jobTypeRetriever.getJobType("TEST_NAR_REPORT"),
                is(FunctionalJobTypeRetriever.JOB_TYPE.CPP_REPORT));
    }

    @Test
    public void getJobType_NAR_RELEASE() {
        Assert.assertThat(
                jobTypeRetriever.getJobType("TEST_NAR_RELEASE"),
                is(FunctionalJobTypeRetriever.JOB_TYPE.CPP_RELEASE));
    }

    @Test
    public void getJobType_NAR_RELEASE_REPORT() {
        Assert.assertThat(
                jobTypeRetriever.getJobType("TEST_NAR_RELEASE_REPORT"),
                is(FunctionalJobTypeRetriever.JOB_TYPE.CPP_REPORT));
    }

    @Test
    public void getJobType_PKG() {
        Assert.assertThat(
                jobTypeRetriever.getJobType("TEST_PKG"),
                is(FunctionalJobTypeRetriever.JOB_TYPE.PKG));
    }

    @Test
    public void getJobType_TEMPLATE() {
        Assert.assertThat(
                jobTypeRetriever.getJobType("TEST_TEMPLATE"),
                is(FunctionalJobTypeRetriever.JOB_TYPE.TEMPLATE));
    }

    @Test
    public void getJobType_PLUGIN() {
        Assert.assertThat(
                jobTypeRetriever.getJobType("TEST_PLUGIN"),
                is(FunctionalJobTypeRetriever.JOB_TYPE.PLUGIN));
    }

}
