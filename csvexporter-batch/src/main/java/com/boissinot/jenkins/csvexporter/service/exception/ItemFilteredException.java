package com.boissinot.jenkins.csvexporter.service.exception;

/**
 * @author Gregory Boissinot
 */
public class ItemFilteredException extends RuntimeException {

    public ItemFilteredException() {
    }

    public ItemFilteredException(String s) {
        super(s);
    }

    public ItemFilteredException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public ItemFilteredException(Throwable throwable) {
        super(throwable);
    }
}
