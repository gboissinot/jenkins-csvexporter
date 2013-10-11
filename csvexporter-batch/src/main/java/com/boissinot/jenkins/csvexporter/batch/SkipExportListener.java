package com.boissinot.jenkins.csvexporter.batch;


import com.boissinot.jenkins.csvexporter.domain.InputSBJobObj;
import com.boissinot.jenkins.csvexporter.exception.ExportException;
import org.springframework.batch.core.listener.SkipListenerSupport;

import java.io.*;

/**
 * @author Gregory Boissinot
 */
public class SkipExportListener extends SkipListenerSupport {

    @Override
    public void onSkipInRead(Throwable t) {
        System.out.println("\nSKIPPED ON READ...");
        t.printStackTrace();
    }

    @Override
    public void onSkipInWrite(Object item, Throwable t) {
        System.out.println("\nSKIPPED ON WRITE...");
        t.printStackTrace();
    }

    @Override
    public void onSkipInProcess(Object item, Throwable t) {
        System.out.println("\nSKIPPED...");
        if (item instanceof InputSBJobObj) {
            InputSBJobObj jobElement = (InputSBJobObj) item;
            File errorFile = new File("export-jobs-errors.txt");
            FileWriter fileWriter = null;
            BufferedWriter bufferedWriter = null;
            PrintWriter printWriter = null;
            try {
                fileWriter = new FileWriter(errorFile, true);
                bufferedWriter = new BufferedWriter(fileWriter);
                printWriter = new PrintWriter(bufferedWriter);
                StringBuilder errorLine = new StringBuilder();
                errorLine.append(jobElement.getJobName());
                errorLine.append(": ");

                if (t instanceof ExportException) {
                    errorLine.append(t.getMessage());
                    t.printStackTrace();
                } else if ((t.getCause() != null) && (t.getCause() instanceof ExportException)) {
                    errorLine.append(t.getCause().getMessage());
                    t.printStackTrace();
                } else {
                    errorLine.append(t.getMessage());
                    t.printStackTrace();
                }

                printWriter.println(errorLine.toString());

            } catch (FileNotFoundException fne) {
                throw new RuntimeException(fne);
            } catch (IOException ioe) {
                throw new RuntimeException(ioe);
            } finally {
                if (printWriter != null) {
                    printWriter.close();
                }
                if (bufferedWriter != null) {
                    try {
                        bufferedWriter.close();
                    } catch (IOException ioe) {
                        throw new RuntimeException(ioe);
                    }
                }
                if (fileWriter != null) {
                    try {
                        fileWriter.close();
                    } catch (IOException ioe) {
                        throw new RuntimeException(ioe);
                    }
                }
            }
        }
    }

}
