package com.boissinot.jenkins.csvexporter.integration;

import com.boissinot.jenkins.csvexporter.domain.OutputCSVJobObj;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;

import java.io.*;

/**
 * @author Gregory Boissinot
 */
@Aspect
public class EmailMapViewer {

    @AfterReturning(value = "execution(* com.boissinot.jenkins.csvexporter.service.extractor.jenkins.OutputObjBuilder.buildObj(*))", returning = "outputCSVJobObj")
    public void display(OutputCSVJobObj outputCSVJobObj) {
        System.out.println("Aspect");
        File jobEmailsFile = new File("jobEmails.txt");
        FileWriter fileWriter = null;
        BufferedWriter bufferedWriter = null;
        PrintWriter printWriter = null;
        try {
            fileWriter = new FileWriter(jobEmailsFile, true);
            bufferedWriter = new BufferedWriter(fileWriter);
            printWriter = new PrintWriter(bufferedWriter);
            printWriter
                    .append(outputCSVJobObj.getName())
                    .append(";")
                    .append(outputCSVJobObj.getDevelopers())
                    .append(",");

        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } finally {
            if (printWriter != null)
                printWriter.close();
            if (bufferedWriter != null) {
                try {
                    bufferedWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
            }
            if (fileWriter != null) {
                try {
                    fileWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
            }
        }
    }
}
