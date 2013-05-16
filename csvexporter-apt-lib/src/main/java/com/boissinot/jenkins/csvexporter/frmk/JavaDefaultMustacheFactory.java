package com.boissinot.jenkins.csvexporter.frmk;

import com.github.mustachejava.DefaultMustacheFactory;

import java.io.IOException;
import java.io.Writer;

/**
 * @author Gregory Boissinot
 */
public class JavaDefaultMustacheFactory extends DefaultMustacheFactory {

    @Override
    public void encode(String value, Writer writer) {
        try {
            writer.append(value);
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }
    }
}

