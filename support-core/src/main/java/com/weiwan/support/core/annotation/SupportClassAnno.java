package com.weiwan.support.core.annotation;

/**
 * @Author: xiaozhennan
 * @Date: 2020/11/12 22:14
 * @Package: com.weiwan.support.core.annotation
 * @ClassName: SupportClassAnnos
 * @Description:
 **/
public enum SupportClassAnno {
    Checkpoint(com.weiwan.support.core.annotation.Checkpoint.class, "Checkpoint"),
    PrintToLog(com.weiwan.support.core.annotation.PrintToLog.class, "PrintToLog"),
    Support(com.weiwan.support.core.annotation.Support.class, "Support"),
    Parallelism(com.weiwan.support.core.annotation.Parallelism.class, "Parallelism");

    private Class annoClass;
    private String name;

    SupportClassAnno(Class clazz, String checkpoint) {
        this.annoClass = clazz;
        this.name = checkpoint;
    }

    public SupportClassAnno getAnnoByClass(Class annoClass) {
        SupportClassAnno[] values = SupportClassAnno.values();
        for (SupportClassAnno value : values) {
            if (value.annoClass == annoClass) {
                return value;
            }
        }
        return null;
    }


    public <T> Class<T> getAnnoClass() {
        return annoClass;
    }

    public void setAnnoClass(Class<?> annoClass) {
        this.annoClass = annoClass;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
