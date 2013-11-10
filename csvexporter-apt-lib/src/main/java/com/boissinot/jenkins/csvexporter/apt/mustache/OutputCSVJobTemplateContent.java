package com.boissinot.jenkins.csvexporter.apt.mustache;

/**
 * @author Gregory Boissinot
 */
public class OutputCSVJobTemplateContent {

    private final String originClassName;

    private final String className;

    private final String names;

    private final String nameLabels;

    public OutputCSVJobTemplateContent(String originClassName, String className, String names, String nameLabels) {
        this.originClassName = originClassName;
        this.className = className;
        this.names = names;
        this.nameLabels = nameLabels;
    }

    public String OriginClassName() {
        return originClassName;
    }

    public String Class() {
        return className;
    }

    public String NameLabels() {
        return nameLabels;
    }

    public String Names() {
        return "new String[] { " + names + "}";
    }

}
