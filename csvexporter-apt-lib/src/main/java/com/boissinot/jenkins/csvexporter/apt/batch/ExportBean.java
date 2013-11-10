package com.boissinot.jenkins.csvexporter.apt.batch;

/**
 * @author Gregory Boissinot
 */
public class ExportBean implements Comparable<ExportBean> {

    private final int order;

    private final String name;

    private final String label;

    public ExportBean(int order, String name, String label) {
        this.order = order;
        this.name = name;
        this.label = label;
    }

    public int getOrder() {
        return order;
    }

    public String getName() {
        return name;
    }

    public String getLabel() {
        return label;
    }

    public int compareTo(ExportBean orderLabelBean) {
        if (this.order < orderLabelBean.order) {
            return -1;
        } else if (this.order == orderLabelBean.order) {
            return 0;
        } else {
            return 1;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ExportBean that = (ExportBean) o;

        if (order != that.order) return false;
        if (label != null ? !label.equals(that.label) : that.label != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = order;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (label != null ? label.hashCode() : 0);
        return result;
    }
}
