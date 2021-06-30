package io.github.penghaojie.btool.model;

/**
 * 项目编译时的机器参数
 */
public class BuildingInfo {
    /**
     * 编译时间
     */
    private String compileTime;
    /**
     * 操作系统名称
     */
    private String osName;
    /**
     * 操作系统版本
     */
    private String osVersion;
    /**
     * java版本
     */
    private String javaVersion;
    /**
     * 机器用户名称
     */
    private String userName;

    public String getCompileTime() {
        return compileTime;
    }

    public void setCompileTime(String compileTime) {
        this.compileTime = compileTime;
    }

    public String getOsName() {
        return osName;
    }

    public void setOsName(String osName) {
        this.osName = osName;
    }

    public String getOsVersion() {
        return osVersion;
    }

    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion;
    }

    public String getJavaVersion() {
        return javaVersion;
    }

    public void setJavaVersion(String javaVersion) {
        this.javaVersion = javaVersion;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}

