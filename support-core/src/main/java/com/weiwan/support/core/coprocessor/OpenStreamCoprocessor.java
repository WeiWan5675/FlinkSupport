package com.weiwan.support.core.coprocessor;

import com.weiwan.support.core.AnnotationInfo;
import com.weiwan.support.core.SupportAnnotationScaner;
import com.weiwan.support.core.SupportAppContext;
import com.weiwan.support.core.api.SupportDataFlow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * @Author: xiaozhennan
 * @Date: 2020/11/12 9:51
 * @Package: com.weiwan.support.core.coprocessor.OpenStreamCoprocessor
 * @ClassName: OpenStreamCoprocessor
 * @Description:
 **/
public class OpenStreamCoprocessor extends SupportCoprocessor {

    private static final Logger logger = LoggerFactory.getLogger(OpenStreamCoprocessor.class);

    public OpenStreamCoprocessor(SupportAppContext context) {
        super(context);
    }


    @Override
    public <E, S1, S2> Object process(E env, SupportDataFlow<E, S1, S2> dataFlow, Object obj) throws Exception {
        logger.info("open stream coprocessor process");
        S1 open = dataFlow.open(env, context);
        return nextProcess(env, dataFlow, open);
    }
}
