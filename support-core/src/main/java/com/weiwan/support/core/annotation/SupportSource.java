package com.weiwan.support.core.annotation;

import com.weiwan.support.core.api.Reader;
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
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface SupportSource {
    Class<? extends Reader> type();

    int parallelism() default 1;

    String name() default "SupportSource";

    String[] vars() default {};
}
