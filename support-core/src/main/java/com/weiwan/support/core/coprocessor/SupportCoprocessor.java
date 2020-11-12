package com.weiwan.support.core.coprocessor;


import com.weiwan.support.core.SupportAnnotationScaner;
import com.weiwan.support.core.SupportAppContext;
import com.weiwan.support.core.api.FlinkSupport;
import com.weiwan.support.core.api.SupportDataFlow;

import java.util.List;

/**
 * @Author: xiaozhennan
 * @Date: 2020/11/10 16:50
 * @Package: com.weiwan.support.core.coprocessor.SupportCoprocessor
 * @ClassName: SupportCoprocessor
 * @Description:
 **/
public abstract class SupportCoprocessor {

    protected SupportAppContext context;

    protected SupportCoprocessor nextCoprocessor;

    public SupportCoprocessor(SupportAppContext context) {
        this.context = context;
    }

    public abstract <E, S1, S2> Object process(E env, SupportDataFlow<E, S1, S2> dataFlow, Object obj) throws Exception;


    public final SupportCoprocessor nextCoprocessor(SupportCoprocessor next) {
        this.nextCoprocessor = next;
        return this;
    }

    protected final <E, S1, S2> Object nextProcess(E env, SupportDataFlow<E, S1, S2> dataFlow, Object obj) throws Exception {
        if (this.nextCoprocessor != null) {
            return this.nextCoprocessor.process(env, dataFlow, obj);
        }
        return obj;
    }
}
