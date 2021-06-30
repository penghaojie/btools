package io.github.penghaojie.btool.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.TYPE)
public @interface GenerateBuildingInfoController {
    /**
     * 生成的controller类名称
     */
    String name() default "BuildingInfoController";

    /**
     * 生成接口的url路径
     */
    String path() default "/btool/building";
}
