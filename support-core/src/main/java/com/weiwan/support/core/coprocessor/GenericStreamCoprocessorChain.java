package com.weiwan.support.core.coprocessor;

import com.weiwan.support.core.SupportContext;
import com.weiwan.support.core.SupportContextHolder;
import com.weiwan.support.core.annotation.Support;
import com.weiwan.support.core.api.SupportDataFlow;

/**
 * @Author: xiaozhennan
 * @Date: 2020/12/2 10:54
 * @Package: com.weiwan.support.core.coprocessor.CoprocessorLink
 * @ClassName: CoprocessorLink
 * @Description:
 **/
public class GenericStreamCoprocessorChain<E, S1, S2> implements CoprocessorChain<E, S1, S2> {

    private final SupportContext context;
    private final E env;
    private final SupportDataFlow<E, S1, S2> dataFlow;
    private final Object obj;

    private SupportCoprocessor head;

    public GenericStreamCoprocessorChain(final E env, final SupportDataFlow<E, S1, S2> dataFlow, final Object obj) {
        this.env = env;
        this.dataFlow = dataFlow;
        this.obj = obj;
        this.context = SupportContextHolder.getContext();
        initChain();
    }

    private void initChain() {
        head = new FirstPreCoprocessor(SupportContextHolder.getContext());
        SupportCoprocessor next = head;
        Support supportAnno = dataFlow.getClass().getAnnotation(Support.class);

        //开启注解支持,添加类的注解处理器,和Source的注解处理器
        if (supportAnno.enable()) {
            next = next.nextCoprocessor(new StreamClassCoprocessor(context)).nextCoprocessor(new SourceStreamCoprocessor(context));
        }
        //数据处理
        next = next.nextCoprocessor(new OpenStreamCoprocessor(context))
                .nextCoprocessor(new StreamCoprocessor(context))
                .nextCoprocessor(new OutputStreamCoprocessor(context));
        //开启注解支持,在output前添加Sink处理器
        if (supportAnno.enable()) {
            next = next.nextCoprocessor(new SinkStreamCoprocessor(context));
        }
        next = next.nextCoprocessor(new LastPreCoprocessor(context));
    }

    public Object coProcessing() throws Exception {
        if (head != null) {
            return head.process(env, dataFlow, obj);
        }else{
            throw new RuntimeException("The coprocessor has not been initialized");
        }
    }
}
