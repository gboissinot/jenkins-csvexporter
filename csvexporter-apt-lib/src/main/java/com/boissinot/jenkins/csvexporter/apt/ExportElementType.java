package com.boissinot.jenkins.csvexporter.apt;

import java.lang.annotation.*;

/**
 * @author Gregory Boissinot
 */
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.TYPE)
@Documented
public @interface ExportElementType {

    String value();
}
