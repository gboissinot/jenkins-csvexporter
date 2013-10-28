package com.boissinot.jenkins.csvexporter.launcher;

/**
 * @author Gregory Boissinot
 */
public class Main {

    public static void main(String[] args) throws Exception {
        StartGenerator startGenerator = new StartGenerator();
        startGenerator.generate();
    }
}
