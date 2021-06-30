package io.github.penghaojie.btool.processor;

import io.github.penghaojie.btool.annotation.GenerateBuildingInfoController;
import io.github.penghaojie.btool.annotation.GenerateBuildingInfoService;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import java.util.HashSet;
import java.util.Set;

public class GenerateBuildingInfoProcessor extends AbstractProcessor {
    private Filer filer;
    private Elements elements;
    private boolean generateControllerFlag = false;
    private boolean generateServiceFlag = false;

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (Element ele : roundEnv.getElementsAnnotatedWith(GenerateBuildingInfoController.class)) {
            if (ele.getKind() == ElementKind.CLASS && !generateControllerFlag) {
                GenerateBuildingInfoController annotation = ele.getAnnotation(GenerateBuildingInfoController.class);
                TypeElement typeElement = (TypeElement) ele;
                String qualifiedName = typeElement.getQualifiedName().toString();
                int i = qualifiedName.lastIndexOf(".");
                String pkgName = qualifiedName.substring(0, i );
                CodeGenerator.generateController(filer,pkgName,annotation.name(),annotation.path());
                generateControllerFlag = true;
            }
        }
        for (Element ele : roundEnv.getElementsAnnotatedWith(GenerateBuildingInfoService.class)) {
            if (ele.getKind() == ElementKind.CLASS && !generateServiceFlag) {
                CodeGenerator.generateService(filer);
                generateServiceFlag = true;
            }
        }
        return false;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> set = new HashSet<>();
        set.add(GenerateBuildingInfoController.class.getCanonicalName());
        set.add(GenerateBuildingInfoService.class.getCanonicalName());
        return set;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        this.filer = processingEnv.getFiler();
        this.elements = processingEnv.getElementUtils();
    }
}
