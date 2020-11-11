package com.weiwan.support.core.annotation;

import jdk.nashorn.internal.ir.annotations.Reference;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author: xiaozhennan
 * @Date: 2020/11/4 16:58
 * @Package: com.weiwan.support.core.annotation.Support
 * @ClassName: Support
 * @Description:
 **/
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Support {
}
