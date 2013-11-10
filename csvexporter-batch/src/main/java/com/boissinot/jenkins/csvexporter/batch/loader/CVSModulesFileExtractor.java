package com.boissinot.jenkins.csvexporter.batch.loader;

import com.boissinot.jenkins.csvexporter.exception.ExportException;
import com.boissinot.jenkins.csvexporter.service.http.HttpResourceContentFetcher;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Gregory Boissinot
 */
public class CVSModulesFileExtractor implements ContextExtractorExtensionPoint {

    private final HttpResourceContentFetcher httpResourceContentFetcher;

    private final String cvsModuleURL;

    public CVSModulesFileExtractor(HttpResourceContentFetcher httpResourceContentFetcher, String cvsModuleURL) {
        this.httpResourceContentFetcher = httpResourceContentFetcher;
        this.cvsModuleURL = cvsModuleURL;
    }

    @Override
    public String getKey() {
        return "CVS";
    }

    @Override
    public Map<String, String> getContextualInfo() {

        String moduleContent = httpResourceContentFetcher.getContent(cvsModuleURL);

        Map<String, String> result = new HashMap<String, String>();
        BufferedReader br = new BufferedReader(new StringReader(moduleContent));
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
