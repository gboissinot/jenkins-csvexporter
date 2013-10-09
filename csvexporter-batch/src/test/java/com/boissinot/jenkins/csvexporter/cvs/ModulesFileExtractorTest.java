package com.boissinot.jenkins.csvexporter.cvs;

import com.boissinot.jenkins.csvexporter.service.extractor.cvs.ModulesFileExtractor;
import junit.framework.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Administrateur
 * Date: 02/10/13
 * Time: 16:44
 * To change this template use File | Settings | File Templates.
 */
public class ModulesFileExtractorTest {

    private static String modulesContent;
    private ModulesFileExtractor extractor = new ModulesFileExtractor();

    @BeforeClass
    public static void init() throws IOException {

        InputStream modulesStream = ModulesFileExtractorTest.class.getResourceAsStream("modules");
        StringBuilder modulesContentBuilder = new StringBuilder();
        int c;
        while ((c = modulesStream.read()) != -1) {
            modulesContentBuilder.append((char) c);
        }
        modulesContent = modulesContentBuilder.toString();

    }

    @Test
    public void testCVSModulesExtractor() {
        Map<String, String> modulesMap = extractor.getModulesMap(modulesContent);
        Assert.assertNotNull(modulesMap);
        Assert.assertTrue(modulesMap.size() != 0);
    }

}
