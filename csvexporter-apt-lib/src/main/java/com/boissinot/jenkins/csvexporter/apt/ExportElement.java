package com.boissinot.jenkins.csvexporter.apt;

import java.lang.annotation.*;

/**
 * @author Gregory Boissinot
 */

@Retention(value = RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
public @interface ExportElement {

    public int order();

    public String label();
}
