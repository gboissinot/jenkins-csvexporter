package com.boissinot.jenkins.csvexporter.service.extractor.cvs;

import com.boissinot.jenkins.csvexporter.exception.ExportException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Gregory Boissinot
 */
public class ModulesFileExtractor {

    public Map<String, String> getModulesMap(String modulesFileContent) {

        Map<String, String> result = new HashMap<String, String>();

        BufferedReader br = new BufferedReader(new StringReader(modulesFileContent));
        String line;
        try {
            while ((line = br.readLine()) != null) {
                String[] lineTab = line.split("\\s+");
                if (lineTab.length > 1) {
                    result.put(lineTab[0].trim(), lineTab[1].trim());
                }
            }
        } catch (IOException ioe) {
            throw new ExportException(ioe);
        } finally {
            try {
                br.close();
            } catch (IOException ioe) {
                throw new ExportException(ioe);
            }
        }

        return result;
    }
}
