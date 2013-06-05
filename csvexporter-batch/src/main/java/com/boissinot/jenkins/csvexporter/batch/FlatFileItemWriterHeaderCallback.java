package com.boissinot.jenkins.csvexporter.batch;

import org.springframework.batch.item.file.FlatFileHeaderCallback;

import java.io.IOException;
import java.io.Writer;

/**
 * @author Gregory Boissinot
 */
public class FlatFileItemWriterHeaderCallback implements FlatFileHeaderCallback {

    public String[] labels;

    public void setLabels(String[] labels) {
        this.labels = labels;
    }

    @Override
    public void writeHeader(Writer writer) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        for (String label : labels) {
            stringBuilder.append(";");
            stringBuilder.append(label);
        }
        stringBuilder.delete(0, 1);
        writer.append(stringBuilder.toString());
    }
}
