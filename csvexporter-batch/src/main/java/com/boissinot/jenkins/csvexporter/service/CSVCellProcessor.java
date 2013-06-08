package com.boissinot.jenkins.csvexporter.service;

/**
 * @author Gregory Boissinot
 */
public class CSVCellProcessor {


    public String getCSVAware(String content) {
        if (content == null) {
            return null;
        }

        if (content.isEmpty()) {
            return content;
        }

        if (content.contains("\"")) {
            content = content.replaceAll("\"", "\"\"");
        }
        return "\"" + content + "\"";
    }
}
