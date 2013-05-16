package com.boissinot.jenkins.csvexporter.frmk;

import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;
import java.io.InputStream;
import java.io.StringReader;
import java.io.Writer;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author Gregory Boissinot
 */
public class OutputCSVJobObjProcessor extends AbstractProcessor {

    private ProcessingEnvironment processingEnv;

    @Override
    public void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        this.processingEnv = processingEnv;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latest();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        HashSet<String> supportedAnnotationTypes = new HashSet<String>();
        supportedAnnotationTypes.add(ExportElementType.class.getCanonicalName());
        return supportedAnnotationTypes;
    }

    private static class OrderLabelBean implements Comparable<OrderLabelBean> {

        private int order;

        private String name;

        private String label;

        public OrderLabelBean(int order, String name, String label) {
            this.order = order;
            this.name = name;
            this.label = label;
        }

        public int compareTo(OrderLabelBean orderLabelBean) {
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

            OrderLabelBean that = (OrderLabelBean) o;

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

    @Override
    public boolean process(Set<? extends TypeElement> annotationsElement, RoundEnvironment roundEnv) {

        try {

            for (TypeElement typeAnnotation : annotationsElement) {
                for (Element element : roundEnv.getElementsAnnotatedWith(typeAnnotation)) {

                    System.out.println("OutputCSVObj Processing ....");

                    String className = element.getAnnotation(ExportElementType.class).value();
                    InputStream inputStream = this.getClass().getResourceAsStream("subClassTemplate.mustache");
                    StringBuffer sb = new StringBuffer();
                    int c;
                    while ((c = inputStream.read()) != -1) {
                        sb.append((char) c);
                    }
                    StringReader stringReader = new StringReader(sb.toString());

                    MustacheFactory mf = new JavaDefaultMustacheFactory();
                    Mustache mustache = mf.compile(stringReader, "mustacheResult");

                    JavaFileObject fileObject = processingEnv.getFiler().createSourceFile("com.boissinot.jenkins.csvexporter.domain.generated.OutputCSVJobObj_", element);

                    Set<OrderLabelBean> orderLabelBeans = getSortedOrderLabelBean(roundEnv.getElementsAnnotatedWith(ExportElement.class));

                    Writer writer = fileObject.openWriter();
                    mustache.execute(writer, new OutputCSVJobTemplateContent(className, className + "_", getNames(orderLabelBeans), getNameLabels(orderLabelBeans)));
                    writer.flush();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, e.getMessage());
            return false;
        }


        return true;
    }

    private Set<OrderLabelBean> getSortedOrderLabelBean(final Set<? extends Element> elementsAnnotatedWithExportElement) {
        Set<OrderLabelBean> sortedSet = new TreeSet<OrderLabelBean>();
        for (Element element1 : elementsAnnotatedWithExportElement) {
            String label = element1.getAnnotation(ExportElement.class).label();
            int order = element1.getAnnotation(ExportElement.class).order();
            sortedSet.add(new OrderLabelBean(order, element1.getSimpleName().toString(), label));
        }
        return sortedSet;
    }

    private String getNames(Set<OrderLabelBean> sortedSet) {

        StringBuilder stringBuilder = new StringBuilder();
        for (OrderLabelBean orderLabelBean : sortedSet) {
            stringBuilder.append(",");
            stringBuilder.append("\"");
            stringBuilder.append(orderLabelBean.name);
            stringBuilder.append("\"");
        }
        stringBuilder.delete(0, 1);
        return stringBuilder.toString();
    }

    private String getNameLabels(Set<OrderLabelBean> sortedSet) {

        StringBuilder stringBuilder = new StringBuilder();
        for (OrderLabelBean orderLabelBean : sortedSet) {
            stringBuilder.append(";");
            stringBuilder.append(orderLabelBean.label);
        }
        stringBuilder.delete(0, 1);
        return stringBuilder.toString();
    }

}



