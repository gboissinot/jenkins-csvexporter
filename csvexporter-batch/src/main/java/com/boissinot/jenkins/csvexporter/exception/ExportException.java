package com.boissinot.jenkins.csvexporter.exception;

/**
 * @author Gregory Boissinot
 */
public class ExportException extends RuntimeException {

    public ExportException() {
    }

    public ExportException(String s) {
        super(s);
    }

    public ExportException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public ExportException(Throwable throwable) {
        super(throwable);
    }
}
