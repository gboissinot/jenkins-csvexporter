package com.boissinot.jenkins.csvexporter.service.extractor.maven.pom;

import com.boissinot.jenkins.csvexporter.domain.maven.pom.Developer;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Administrateur
 * Date: 09/10/13
 * Time: 14:20
 * To change this template use File | Settings | File Templates.
 */
public class POMDeveloperSectionExtractorTest {


    @Test
    public void test1() throws IOException {

        String pomContent = IOUtils.toString(this.getClass().getResourceAsStream("pom-1.xml"));
        POMDeveloperSectionExtractor extractor = new POMDeveloperSectionExtractor();
        List<Developer> developers = extractor.extract(pomContent);
    }
}
