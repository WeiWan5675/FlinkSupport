package com.weiwan.support.core;

import com.weiwan.support.core.api.FlinkSupport;

import java.util.List;

/**
 * @Author: xiaozhennan
 * @Date: 2020/11/11 14:48
 * @Package: com.weiwan.support.core.SupportAnnotationScaner
 * @ClassName: SupportAnnotationScaner
 * @Description:
 **/
public class SupportAnnotationScaner {

    private FlinkSupport flinkSupport;

    public SupportAnnotationScaner(FlinkSupport flinkSupport) {
        this.flinkSupport = flinkSupport;
    }

    public List<AnnotationInfo> scanClass(String className) {

        return null;
    }

    public List<AnnotationInfo> scanMethon(String s) {

        return null;
    }
}
