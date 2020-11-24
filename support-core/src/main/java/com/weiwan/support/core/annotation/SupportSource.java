package com.weiwan.support.core.annotation;

import com.weiwan.support.core.enums.SourceElement;

import java.lang.annotation.*;

/**
 * @Author: xiaozhennan
 * @Date: 2020/11/24 16:05
 * @Package: com.weiwan.support.core.annotation.SupportSources
 * @ClassName: SupportSources
 * @Description:
 **/
@Inherited
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface SupportSource {
    SourceElement type();
    int parallelism() default 1;
    String name();
}
