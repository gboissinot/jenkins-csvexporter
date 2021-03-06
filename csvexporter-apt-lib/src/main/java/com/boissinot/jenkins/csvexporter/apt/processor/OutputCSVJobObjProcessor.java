package com.boissinot.jenkins.csvexporter.apt.processor;

import com.boissinot.jenkins.csvexporter.apt.ExportElement;
import com.boissinot.jenkins.csvexporter.apt.ExportElementType;
import com.boissinot.jenkins.csvexporter.apt.batch.BeanFieldRetriever;
import com.boissinot.jenkins.csvexporter.apt.batch.ExportBean;
import com.boissinot.jenkins.csvexporter.apt.batch.HeaderLabelRetriever;
import com.boissinot.jenkins.csvexporter.apt.mustache.JavaDefaultMustacheFactory;
import com.boissinot.jenkins.csvexporter.apt.mustache.OutputCSVJobTemplateContent;
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
import java.util.SortedSet;

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

    @Override
    public boolean process(Set<? extends TypeElement> annotationsElement, RoundEnvironment roundEnv) {

        try {

            for (TypeElement typeAnnotation : annotationsElement) {
                for (Element element : roundEnv.getElementsAnnotatedWith(typeAnnotation)) {

                    System.out.println("OutputCSVObj Processing ....");

                    String className = element.getAnnotation(ExportElementType.class).value();
                    InputStream inputStream = this.getClass().getResourceAsStream("mustache/subClassTemplate.mustache");
                    StringBuilder sb = new StringBuilder();
                    int c;
                    while ((c = inputStream.read()) != -1) {
                        sb.append((char) c);
                    }
                    StringReader stringReader = new StringReader(sb.toString());

                    MustacheFactory mf = new JavaDefaultMustacheFactory();
                    Mustache mustache = mf.compile(stringReader, "mustacheResult");

                    JavaFileObject fileObject = processingEnv.getFiler().createSourceFile("com.boissinot.jenkins.csvexporter.domain.generated.OutputCSVJobObj_", element);

                    ExportBeanListRetriever exportBeanListRetriever = new ExportBeanListRetriever();
                    SortedSet<ExportBean> beans = exportBeanListRetriever.buildExportBeanList(roundEnv.getElementsAnnotatedWith(ExportElement.class));

                    Writer writer = fileObject.openWriter();

                    HeaderLabelRetriever headerLabelRetriever = new HeaderLabelRetriever();
                    BeanFieldRetriever beanFieldRetriever = new BeanFieldRetriever();
                    mustache.execute(writer,
                            new OutputCSVJobTemplateContent(className, className + "_",
                                    beanFieldRetriever.getNames(beans),
                                    headerLabelRetriever.getNameLabels(beans)));
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

}



