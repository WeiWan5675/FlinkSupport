package com.weiwan.support.core.coprocessor;

import com.weiwan.support.core.SupportAppContext;
import com.weiwan.support.core.api.SupportDataFlow;

/**
 * @Author: xiaozhennan
 * @Date: 2020/11/12 9:52
 * @Package: com.weiwan.support.core.coprocessor.StreamCoprocessor
 * @ClassName: StreamCoprocessor
 * @Description:
 **/
public class StreamCoprocessor extends SupportCoprocessor {
    public StreamCoprocessor(SupportAppContext context) {
        super(context);
    }

    @Override
    public <E, S1, S2> Object process(E env, SupportDataFlow<E, S1, S2> dataFlow, Object obj) throws Exception {
        S2 process = dataFlow.process((S1) obj, context);
        return nextProcess(env, dataFlow, process);
    }
}
