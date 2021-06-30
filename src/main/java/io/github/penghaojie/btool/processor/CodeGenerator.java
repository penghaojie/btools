package io.github.penghaojie.btool.processor;

import io.github.penghaojie.btool.model.BuildingInfo;
import io.github.penghaojie.btool.service.BuildingInfoService;
import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.processing.Filer;
import javax.lang.model.element.Modifier;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

public class CodeGenerator {
    public static void generateController(Filer filer, String pkgName,String controllerName,String path) {
        Properties properties = System.getProperties();
        AnnotationSpec annotationSpec = AnnotationSpec.builder(GetMapping.class)
                .addMember("value", "$S", path)
                .build();
        MethodSpec main = MethodSpec.methodBuilder("buildingInfo")
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(annotationSpec)
                .returns(BuildingInfo.class)
                .addStatement("$T info = new $T()",BuildingInfo.class, BuildingInfo.class)
                .addStatement("info.setCompileTime($S)",getCurrentTime())
                .addStatement("info.setOsName($S)",properties.getProperty("os.name"))
                .addStatement("info.setOsVersion($S)",properties.getProperty("os.version"))
                .addStatement("info.setJavaVersion($S)",properties.getProperty("java.version"))
                .addStatement("info.setUserName($S)",properties.getProperty("user.name"))
                .addStatement("return info")
                .build();

        TypeSpec buildingInfo = TypeSpec.classBuilder(controllerName)
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addMethod(main)
                .addAnnotation(RestController.class)
                .build();

        JavaFile javaFile = JavaFile.builder(pkgName, buildingInfo)
                .build();
        try {
            javaFile.writeTo(filer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void generateService(Filer filer) {
        Properties properties = System.getProperties();
        MethodSpec main = MethodSpec.methodBuilder("getInfo")
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(Override.class)
                .returns(BuildingInfo.class)
                .addStatement("$T info = new $T()",BuildingInfo.class, BuildingInfo.class)
                .addStatement("info.setCompileTime($S)",getCurrentTime())
                .addStatement("info.setOsName($S)",properties.getProperty("os.name"))
                .addStatement("info.setOsVersion($S)",properties.getProperty("os.version"))
                .addStatement("info.setJavaVersion($S)",properties.getProperty("java.version"))
                .addStatement("info.setUserName($S)",properties.getProperty("user.name"))
                .addStatement("return info")
                .build();

        TypeSpec buildingInfo = TypeSpec.classBuilder("BuildingInfoServiceImpl")
                .addSuperinterface(BuildingInfoService.class)
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addMethod(main)
                .build();

        JavaFile javaFile = JavaFile.builder("com.github.penghaojie.btool.service", buildingInfo)
                .build();
        try {
            javaFile.writeTo(filer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String getCurrentTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date());
    }
}
