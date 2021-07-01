<p align="center">
  <a href="https://search.maven.org/search?q=io.github.penghaojie">
    <img alt="maven" src="https://img.shields.io/maven-metadata/v?metadataUrl=https%3A%2F%2Frepo1.maven.org%2Fmaven2%2Fio%2Fgithub%2Fpenghaojie%2Fbtools%2Fmaven-metadata.xml">
  </a>
  <a href="https://www.apache.org/licenses/LICENSE-2.0">
    <img alt="code style" src="https://img.shields.io/badge/license-Apache%202-4EB1BA.svg?style=flat-square">
  </a>
</p>
# 简介 

你是否有遇到过这种场景，项目bug修改后打包发给运维去部署，开发环境没问题 生产环境还是存在bug，经过各种排查后都没发现代码有什么问题 最后发现是运维部署的jar包是旧的（(╯▔皿▔)╯）。

为了避免再出现这种情况，于是我开发了btools工具。btools可以在项目构建时动态生成接口，该接口返回了 项目构建时的相关信息，如：项目是什么时候编译的、编译时用的是什么操作系统、用的是什么java版本等。

# 使用说明

该构件已经发布到maven中央仓库了，在你的pom配置文件中引入下面的依赖：

```xml
<dependency>
    <groupId>io.github.penghaojie</groupId>
    <artifactId>btools</artifactId>
    <version>1.2.5</version>
</dependency>
```
## 方式1  使用`@GenerateBuildingInfoController`注解
```java
@SpringBootApplication
@GenerateBuildingInfoController
public class DemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class,args);
    }
}
```
在项目编译时，会在DemoApplication类所在的包路径下生成一个`BuildingInfoController`文件，内容如下：
```java
import io.github.penghaojie.btool.model.BuildingInfo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public final class BuildingInfoController {
  @GetMapping("/btool/building")
  public BuildingInfo buildingInfo() {
    BuildingInfo info = new BuildingInfo();
    info.setCompileTime("2021-07-01 14:05:42");
    info.setOsName("Windows 10");
    info.setOsVersion("10.0");
    info.setJavaVersion("1.8.0_181");
    info.setUserName("phj");
    return info;
  }
}
```


注解`@GenerateBuildingInfoController`的源码如下：

```java
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
```
该注解有两个属性，用来支持自定义生成的类名称和接口的url路径。如：
```java
@SpringBootApplication
@GenerateBuildingInfoController(name = "MyController",path = "myPath")
public class DemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class,args);
    }
}
```
则会生成一个`MyController`类文件，生成的代码如下:
```java
import io.github.penghaojie.btool.model.BuildingInfo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public final class MyController {
  @GetMapping("myPath")
  public BuildingInfo buildingInfo() {
    BuildingInfo info = new BuildingInfo();
    info.setCompileTime("2021-07-01 14:15:17");
    info.setOsName("Windows 10");
    info.setOsVersion("10.0");
    info.setJavaVersion("1.8.0_181");
    info.setUserName("phj");
    return info;
  }
}
```

## 方式2  使用`@GenerateBuildingInfoService`注解
在某些特殊情况下，直接生成Controller可能不合适，这时就可以自己写接口，然后在
接口方法中调用`BToolUtils.getInfo()` 来获取构建信息，如：
```java
@SpringBootApplication
@GenerateBuildingInfoService
public class DemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class,args);
    }
    @GetMapping("/info")
    public BuildingInfo buildingInfo() {
        return BToolUtils.getInfo();
    }
}
```
注意： 如果没有`@GenerateBuildingInfoService`注解，直接调用`BToolUtils.getInfo()`则会报错。

# 视频教程
等有空时我会录个使用教程上传到b站上，敬请期待。

# 实现原理
用到了Java中的APT（Annotation Process Tool）工具和 Java Poet类库。具体的实现细节，有空时我会写篇博客或者出个视频教程传到b站上。

