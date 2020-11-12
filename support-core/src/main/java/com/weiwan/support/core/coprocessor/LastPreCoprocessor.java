package com.weiwan.support.core.coprocessor;

import com.weiwan.support.core.SupportAppContext;
import com.weiwan.support.core.api.SupportDataFlow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author: xiaozhennan
 * @Date: 2020/11/12 22:29
 * @Package: com.weiwan.support.core.coprocessor
 * @ClassName: LastPreCoprocessor
 * @Description:
 **/
public class LastPreCoprocessor extends SupportCoprocessor {

    private static final Logger logger = LoggerFactory.getLogger(LastPreCoprocessor.class);

    public LastPreCoprocessor(SupportAppContext context) {
        super(context);
    }

    @Override
    public <E, S1, S2> Object process(E env, SupportDataFlow<E, S1, S2> dataFlow, Object obj) {
        return null;
    }
}
