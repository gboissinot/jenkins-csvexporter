package com.boissinot.jenkins.csvexporter.service.extractor.jenkins;

import javax.annotation.Nullable;

/**
 * @author Gregory Boissinot
 */
public class CSVCellProcessor {


    public String getCSVAware(@Nullable String content) {
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
