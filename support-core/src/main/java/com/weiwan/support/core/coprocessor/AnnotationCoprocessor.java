package com.weiwan.support.core.coprocessor;

import com.weiwan.support.core.AnnotationInfo;
import com.weiwan.support.core.SupportAnnotationScaner;
import com.weiwan.support.core.SupportAppContext;
import com.weiwan.support.core.annotation.Support;
import com.weiwan.support.core.api.SupportDataFlow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * @Author: xiaozhennan
 * @Date: 2020/11/11 15:44
 * @Package: com.weiwan.support.core.coprocessor.AnnotationCoprocessor
 * @ClassName: AnnotationCoprocessor
 * @Description:
 **/
public class AnnotationCoprocessor extends SupportCoprocessor {

    private static final Logger logger = LoggerFactory.getLogger(AnnotationCoprocessor.class);

    public AnnotationCoprocessor(SupportAppContext context) {
        super(context);
    }

    @Override
    public <E, S1, S2> Object process(E env, SupportDataFlow<E, S1, S2> dataFlow, Object obj) {
        return nextProcess(env, dataFlow, obj);
    }
}
