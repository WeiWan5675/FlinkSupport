package com.weiwan.support.core.annotation;

import com.weiwan.support.core.api.Writer;

import java.lang.annotation.*;

/**
 * @Author: xiaozhennan
 * @Date: 2020/12/2 9:01
 * @Package: com.weiwan.support.core.annotation.SupportSink
 * @ClassName: SupportSink
 * @Description:
 **/
@Inherited
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface SupportSink {

    Class<? extends Writer> type();

    int parallelism() default 1;

    String name() default "SupportSource";

    String[] vars() default {};

}
