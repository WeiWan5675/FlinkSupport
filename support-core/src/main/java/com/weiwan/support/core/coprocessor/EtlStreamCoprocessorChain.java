package com.weiwan.support.core.coprocessor;

import com.weiwan.support.core.StreamSupport;
import com.weiwan.support.core.SupportContext;
import com.weiwan.support.core.SupportContextHolder;
import com.weiwan.support.core.api.SupportDataFlow;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * @Author: xiaozhennan
 * @Date: 2020/12/2 11:25
 * @Package: com.weiwan.support.core.coprocessor.EtlStreamCoprocessorChain
 * @ClassName: EtlStreamCoprocessorChain
 * @Description:
 **/
public class EtlStreamCoprocessorChain<E, S1, S2> implements CoprocessorChain<E, S1, S2> {
    private final Object obj;
    private final E env;
    private final SupportDataFlow<E, S1, S2> dataFlow;
    private final SupportContext context;
    private SupportCoprocessor head;

    public EtlStreamCoprocessorChain(E env, SupportDataFlow<E, S1, S2> dataFlow, Object obj) {
        this.env = env;
        this.dataFlow = dataFlow;
        this.obj = obj;
        this.context = SupportContextHolder.getContext();
        initChain();
    }

    private void initChain() {
        head = new FirstPreCoprocessor(context);
        SupportCoprocessor next = head;
        next = next.nextCoprocessor(new EtlCoprocessor(context))
                .nextCoprocessor(new LastPreCoprocessor(context));
    }


    @Override
    public Object coProcessing() throws Exception {
        if (head != null) {
            return head.process(env, dataFlow, obj);
        } else {
            throw new RuntimeException("The coprocessor has not been initialized");
        }
    }
}
