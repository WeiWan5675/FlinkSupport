package com.weiwan.support.core.enums;

import com.weiwan.support.core.annotation.Parallelism;
import com.weiwan.support.core.annotation.PrintStream;
import com.weiwan.support.core.annotation.SupportSource;

/**
 * @Author: xiaozhennan
 * @Date: 2020/12/1 15:23
 * @Package: com.weiwan.support.core.enums.FieldAnnoEnum
 * @ClassName: FieldAnnoEnum
 * @Description:
 **/
public enum FieldAnnoEnum {
    Parallelism(com.weiwan.support.core.annotation.Parallelism.class),
    PrintStream(com.weiwan.support.core.annotation.PrintStream.class),
    SupportSource(com.weiwan.support.core.annotation.SupportSource.class);

    public final Class<?> clazz;

    FieldAnnoEnum(Class<?> clazz) {
        this.clazz = clazz;
    }
}
