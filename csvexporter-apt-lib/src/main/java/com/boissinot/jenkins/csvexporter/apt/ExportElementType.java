package com.boissinot.jenkins.csvexporter.apt;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Gregory Boissinot
 */
@Retention(value = RetentionPolicy.SOURCE)
@Target(ElementType.TYPE)
public @interface ExportElementType {

    String value();
}
