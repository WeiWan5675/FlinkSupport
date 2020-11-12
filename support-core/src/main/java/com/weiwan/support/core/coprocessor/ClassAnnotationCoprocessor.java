package com.weiwan.support.core.coprocessor;

import com.weiwan.support.core.SupportAppContext;
import com.weiwan.support.core.annotation.SupportClassAnno;
import com.weiwan.support.core.api.SupportDataFlow;
import com.weiwan.support.core.coprocessor.SupportCoprocessor;

import java.lang.annotation.Annotation;

/**
 * @Author: xiaozhennan
 * @Date: 2020/11/12 22:25
 * @Package: com.weiwan.support.core
 * @ClassName: ClassAnnotationCoprocessor
 * @Description:
 **/
public class ClassAnnotationCoprocessor extends SupportCoprocessor {
    public ClassAnnotationCoprocessor(SupportAppContext context) {
        super(context);
    }

    @Override
    public <E, S1, S2> Object process(E env, SupportDataFlow<E, S1, S2> dataFlow, Object obj) {
        Class<? extends SupportDataFlow> userClass = dataFlow.getClass();
        SupportClassAnno[] values = SupportClassAnno.values();
        for (SupportClassAnno value : values) {
            Class<Object> annoClass = value.getAnnoClass();
        }
        return null;
    }
}
