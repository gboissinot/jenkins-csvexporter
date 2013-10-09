package com.boissinot.jenkins.csvexporter.apt;

import java.lang.annotation.*;

/**
 * @author Gregory Boissinot
 */
@Retention(value = RetentionPolicy.SOURCE)
@Target(ElementType.TYPE)
@Documented
public @interface ExportElementType {

    String value();
}
