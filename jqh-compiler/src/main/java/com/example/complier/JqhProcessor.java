package com.example.complier;

import com.example.annotations.AppRegisterGenerator;
import com.example.annotations.EntryGenerator;
import com.example.annotations.PayEntryGenerator;
import com.google.auto.service.AutoService;

import java.lang.annotation.Annotation;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.AnnotationValueVisitor;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;

/**
 * Created by jiangqianghua on 18/3/27.
 */
@SuppressWarnings("unysed")
@AutoService(Processor.class)
public class JqhProcessor extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        final Set<String> types = new LinkedHashSet<>();

        return false;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        final Set<String> types = new LinkedHashSet<>();
        final Set<Class<? extends Annotation>> supportAnnotations = getSupportedAnnotations();
        for(Class<? extends Annotation> annoations:supportAnnotations){
            types.add(annoations.getCanonicalName());
        }
        return types;
    }

    private Set<Class<? extends Annotation>> getSupportedAnnotations(){
        final Set<Class<? extends Annotation>> annotations = new LinkedHashSet<>();
        annotations.add(EntryGenerator.class);
        annotations.add(PayEntryGenerator.class);
        annotations.add(AppRegisterGenerator.class);
        return annotations;
    }

    /**
     * 扫描所有的类
     * @param env
     * @param annoation
     * @param visitor
     */
    private void scan(RoundEnvironment env,
                      Class<? extends Annotation> annoation,
                      AnnotationValueVisitor visitor){
        for(Element typeElement:env.getElementsAnnotatedWith(annoation)){
            final List<? extends AnnotationMirror> annotationMirrors =
                    typeElement.getAnnotationMirrors();
            for(AnnotationMirror annotationMirror:annotationMirrors){
                final Map<? extends ExecutableElement,? extends AnnotationValue> elementValues
                        = annotationMirror.getElementValues();
                for(Map.Entry<? extends ExecutableElement,? extends AnnotationValue> entry:
                    elementValues.entrySet()){
                    entry.getValue().accept(visitor,null);
                }
            }
        }
    }

    private void generateEntryCode(RoundEnvironment env){

    }
}
