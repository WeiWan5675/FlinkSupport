package com.weiwan.support.core;

import java.lang.annotation.Annotation;

/**
 * @Author: xiaozhennan
 * @Date: 2020/11/11 14:53
 * @Package: com.weiwan.support.core.AnnotationInfo
 * @ClassName: AnnotationInfo
 * @Description:
 **/
public class AnnotationInfo {

    private String className;
    private String methonName;
    private Class annoClass;

    public AnnotationInfo(Class<? extends Annotation> aClass) {
        this.annoClass = aClass;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethonName() {
        return methonName;
    }

    public void setMethonName(String methonName) {
        this.methonName = methonName;
    }

    public Class getAnnoClass() {
        return annoClass;
    }

    public void setAnnoClass(Class annoClass) {
        this.annoClass = annoClass;
    }
}
