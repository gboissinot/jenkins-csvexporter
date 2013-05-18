package com.boissinot.jenkins.csvexporter.apt.processor;

import com.boissinot.jenkins.csvexporter.apt.ExportElement;
import com.boissinot.jenkins.csvexporter.apt.ExportElementType;
import com.boissinot.jenkins.csvexporter.apt.batch.ExportBean;
import org.junit.Before;
import org.junit.Test;
import sun.jvm.hotspot.utilities.Assert;

import javax.lang.model.element.*;
import javax.lang.model.type.NoType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.type.TypeVisitor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.*;

/**
 * @author Gregory Boissinot
 */
public class ExportBeanListRetrieverTest {

    private ExportBeanListRetriever exportBeanListRetriever;

    @Before
    public void buildExportBeanListRetriever() {
        exportBeanListRetriever = new ExportBeanListRetriever();
    }

    static Name name(final CharSequence content) {
        return new Name() {
            @Override
            public boolean contentEquals(CharSequence cs) {
                return cs.toString().equals(content.toString());
            }

            @Override
            public String toString() {
                return content == null ? "<null>" : content.toString();
            }

            @Override
            public int length() {
                return content.length();
            }

            @Override
            public char charAt(int index) {
                return content.charAt(index);
            }

            @Override
            public CharSequence subSequence(int start, int end) {
                return content.subSequence(start, end);
            }

            @Override
            public int hashCode() {
                throw new UnsupportedOperationException();
            }

        };
    }

    public static TypeMirror noType() {
        return new NoType() {
            @Override
            public <R, P> R accept(TypeVisitor<R, P> v, P p) {
                throw new UnsupportedOperationException();
            }

            @Override
            public TypeKind getKind() {
                throw new UnsupportedOperationException();
            }
        };
    }

    private static TypeElement createNameElement(
            final Class<?> wrapperObjectClass,
            final String name) {

        return new TypeElement() {

            @Override
            public ElementKind getKind() {
                return ElementKind.FIELD;
            }

            @Override
            public List<? extends Element> getEnclosedElements() {
                return new ArrayList<Element>();
            }

            @Override
            public Name getQualifiedName() {
                return name(name);
            }

            @Override
            public Name getSimpleName() {
                return name(name);
            }

            @Override
            public TypeMirror getSuperclass() {
                return noType();
            }

            @Override
            public List<? extends TypeMirror> getInterfaces() {
                return new ArrayList<TypeMirror>();
            }

            @Override
            public String toString() {
                return name;
            }

            @Override
            public <R, P> R accept(ElementVisitor<R, P> v, P p) {
                return v.visitType(this, p);
            }

            @Override
            public int hashCode() {
                return name.hashCode();
            }

            @Override
            public NestingKind getNestingKind() {
                throw new UnsupportedOperationException();
            }

            @Override
            public List<? extends TypeParameterElement> getTypeParameters() {
                throw new UnsupportedOperationException();
            }

            @Override
            public TypeMirror asType() {
                throw new UnsupportedOperationException();
            }

            @Override
            public <A extends Annotation> A getAnnotation(Class<A> annotationType) {
                try {
                    final Field field = wrapperObjectClass.getDeclaredField(name);
                    if (field == null) {
                        throw new RuntimeException("Field " + field + " must exists.");
                    }
                    return field.getAnnotation(annotationType);
                } catch (NoSuchFieldException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public List<? extends AnnotationMirror> getAnnotationMirrors() {
                throw new UnsupportedOperationException();
            }

            @Override
            public Element getEnclosingElement() {
                throw new UnsupportedOperationException();
            }

            @Override
            public Set<Modifier> getModifiers() {
                throw new UnsupportedOperationException();
            }

        };
    }


    @ExportElementType("WrapperObject")
    public static class WrapperObject {

        @ExportElement(order = 1, label = "FIELD1")
        private String field1;

        @ExportElement(order = 2, label = "FIELD2")
        private String field2;

        @ExportElement(order = 3, label = "FIELD3")
        private String field3;

    }

    @Test
    public void buildExportBeanList() {

        Set<TypeElement> typeElements = new HashSet<TypeElement>();
        typeElements.add(createNameElement(WrapperObject.class, "field1"));
        typeElements.add(createNameElement(WrapperObject.class, "field2"));
        typeElements.add(createNameElement(WrapperObject.class, "field3"));

        final SortedSet<ExportBean> actualExportBeans = exportBeanListRetriever.buildExportBeanList(typeElements);

        ExportBean exportBean1 = new ExportBean(1, "field1", "FIELD1");
        ExportBean exportBean2 = new ExportBean(2, "field2", "FIELD2");
        ExportBean exportBean3 = new ExportBean(3, "field3", "FIELD3");
        final SortedSet<ExportBean> expectedExportBeans = new TreeSet<ExportBean>();
        expectedExportBeans.add(exportBean1);
        expectedExportBeans.add(exportBean2);
        expectedExportBeans.add(exportBean3);

        for (ExportBean actualExportBean : actualExportBeans) {
            Assert.that(expectedExportBeans.contains(actualExportBean), "");
        }
    }

}
