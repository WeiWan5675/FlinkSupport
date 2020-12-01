package com.weiwan.support.core.coprocessor;

import com.weiwan.support.core.SupportContext;
import com.weiwan.support.core.api.SupportDataFlow;

/**
 * @Author: xiaozhennan
 * @Date: 2020/12/1 17:09
 * @Package: com.weiwan.support.core.coprocessor.SinkCoprocessor
 * @ClassName: SinkCoprocessor
 * @Description:
 **/
public class SinkCoprocessor extends SupportCoprocessor {
    public SinkCoprocessor(SupportContext context) {
        super(context);
    }

    @Override
    public <E, S1, S2> Object process(E env, SupportDataFlow<E, S1, S2> dataFlow, Object obj) throws Exception {
        return null;
    }
}
