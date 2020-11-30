package com.weiwan.support.core.enums;

import com.weiwan.support.core.annotation.Checkpoint;
import com.weiwan.support.core.annotation.Parallelism;
import com.weiwan.support.core.annotation.Support;

/**
 * @Author: xiaozhennan
 * @Date: 2020/11/27 11:15
 * @Package: com.weiwan.support.core.enums.ClassAnnoEnum
 * @ClassName: ClassAnnoEnum
 * @Description:
 **/
public enum ClassAnnoEnum {
    CHECKPOINT(Checkpoint.class),
    PARALLELISM(Parallelism.class),
    SUPPORT(Support.class);

    private final Class<?> clazz;

    ClassAnnoEnum(Class<?> clazz) {
        this.clazz = clazz;
    }

    public static ClassAnnoEnum getAnnoEnum(Class<?> clazz) {
        ClassAnnoEnum[] values = ClassAnnoEnum.values();
        for (ClassAnnoEnum value : values) {
            Class<?> clazz1 = value.getClazz();
            if (clazz1 == clazz) {
                return value;
            }
        }
        return null;
    }

    public Class<?> getClazz() {
        return clazz;
    }
}
