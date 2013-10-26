package com.boissinot.jenkins.csvexporter.integration.aspect;

import com.boissinot.jenkins.csvexporter.domain.JobMessageHeaders;
import com.boissinot.jenkins.csvexporter.domain.OutputCSVJobObj;
import com.boissinot.jenkins.csvexporter.exception.ExportException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.integration.MessageHeaders;
import org.springframework.stereotype.Component;

import java.io.*;

/**
 * @author Gregory Boissinot
 */
@Component
@Aspect
public class DerivedJobAndEmailsFileProducer {

    @Pointcut("execution(* com.boissinot.jenkins.csvexporter.service.extractor.jenkins.OutputObjBuilder.buildObj(*))")
    public void buildObj() {
    }

    @AfterReturning(value = "buildObj()", returning = "outputCSVJobObj")
    public void display(JoinPoint joinPoint, OutputCSVJobObj outputCSVJobObj) {

        FileWriter fileWriter = null;
        BufferedWriter bufferedWriter = null;
        PrintWriter printWriter = null;
        try {
            final MessageHeaders messageHeaders = (MessageHeaders) (joinPoint.getArgs()[0]);
            final String updateEmailFilePath = (String) messageHeaders.get(JobMessageHeaders.HEADER_EMAIL_FILE_PATH);
            if (updateEmailFilePath == null) {
                throw new ExportException("The update Email file path must be set");
            }
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

        } catch (Throwable e) {
            throw new ExportException(e);
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
