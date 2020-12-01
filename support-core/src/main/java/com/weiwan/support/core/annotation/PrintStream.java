package com.weiwan.support.core.annotation;

import java.lang.annotation.*;

/**
 * @Author: xiaozhennan
 * @Date: 2020/12/1 15:07
 * @Package: com.weiwan.support.core.annotation.PrintStream
 * @ClassName: PrintStream
 * @Description:
 **/
@Inherited
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface PrintStream {
}
