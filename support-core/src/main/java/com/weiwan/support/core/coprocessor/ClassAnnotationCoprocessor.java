package com.weiwan.support.core.coprocessor;

import com.weiwan.support.core.SupportAppContext;
import com.weiwan.support.core.annotation.Support;
import com.weiwan.support.core.annotation.SupportClassAnno;
import com.weiwan.support.core.api.SupportDataFlow;
import com.weiwan.support.core.coprocessor.SupportCoprocessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    private static final Logger logger = LoggerFactory.getLogger(ClassAnnotationCoprocessor.class);

    @Override
    public <E, S1, S2> Object process(E env, SupportDataFlow<E, S1, S2> dataFlow, Object obj) throws Exception {
        Class<? extends SupportDataFlow> userClass = dataFlow.getClass();

        Support annotation = userClass.getAnnotation(Support.class);
        if (annotation != null) {
            logger.info("support annotation is not null");
        }
        return nextProcess(env,dataFlow,obj);
    }
}
