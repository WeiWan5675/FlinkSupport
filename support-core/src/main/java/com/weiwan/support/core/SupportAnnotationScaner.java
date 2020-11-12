package com.weiwan.support.core;

import com.weiwan.support.core.api.FlinkSupport;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: xiaozhennan
 * @Date: 2020/11/11 14:48
 * @Package: com.weiwan.support.core.SupportAnnotationScaner
 * @ClassName: SupportAnnotationScaner
 * @Description:
 **/
public class SupportAnnotationScaner {

    private FlinkSupport flinkSupport;
    Map classAnno = null;


    public SupportAnnotationScaner(FlinkSupport flinkSupport) {
        this.flinkSupport = flinkSupport;
    }

    public Map<Class, AnnotationInfo> scanClass() {
        return null;
    }

    public Map<Class, AnnotationInfo> scanMethon(String methonName, Class<?>... parameterTypes) {
        Map methonAnno = new HashMap<Class, AnnotationInfo>();
        Method declaredMethod = null;
        try {
            declaredMethod = flinkSupport.getClass().getDeclaredMethod(methonName, parameterTypes);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            return null;
        }
        declaredMethod.setAccessible(true);
        Annotation[] annotations = declaredMethod.getAnnotations();
        for (Annotation annotation : annotations) {
            Class<? extends Annotation> aClass = annotation.getClass();
            AnnotationInfo annotationInfo = new AnnotationInfo(aClass);
            methonAnno.put(aClass, annotationInfo);
        }
        return methonAnno;
    }
}
