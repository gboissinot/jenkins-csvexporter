package com.boissinot.jenkins.csvexporter.integration.aspect;

import com.boissinot.jenkins.csvexporter.domain.OutputCSVJobObj;
import com.boissinot.jenkins.csvexporter.exception.ExportException;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;

/**
 * @author Gregory Boissinot
 */
@Component
@Aspect
public class DerivedJobAndEmailsFileProducer {

    private String updateEmailFilePath;

    public void setUpdateEmailFilePath(@Value("#{jobParameters['update.email.filepath']}") String updateEmailFilePath) {
        this.updateEmailFilePath = updateEmailFilePath;
    }

    @AfterReturning(value = "execution(* com.boissinot.jenkins.csvexporter.service.extractor.jenkins.OutputObjBuilder.buildObj(*))", returning = "outputCSVJobObj")
    public void display(OutputCSVJobObj outputCSVJobObj) {

        if (updateEmailFilePath == null) {
            updateEmailFilePath = "jobEmails.txt";
        }

        FileWriter fileWriter = null;
        BufferedWriter bufferedWriter = null;
        PrintWriter printWriter = null;
        try {
            File jobEmailsFile = new File(updateEmailFilePath);
            jobEmailsFile.createNewFile();
            fileWriter = new FileWriter(jobEmailsFile, true);
            bufferedWriter = new BufferedWriter(fileWriter);
            printWriter = new PrintWriter(bufferedWriter);
            printWriter
                    .append(outputCSVJobObj.getName())
                    .append(";")
                    .append(outputCSVJobObj.getDevelopers())
                    .append(",");

        } catch (IOException ioe) {
            throw new ExportException(ioe);
        } finally {
            if (printWriter != null)
                printWriter.close();
            if (bufferedWriter != null) {
                try {
                    bufferedWriter.close();
                } catch (IOException ioe) {
                    throw new ExportException(ioe);
                }
            }
            if (fileWriter != null) {
                try {
                    fileWriter.close();
                } catch (IOException ioe) {
                    throw new ExportException(ioe);
                }
            }
        }
    }
}
