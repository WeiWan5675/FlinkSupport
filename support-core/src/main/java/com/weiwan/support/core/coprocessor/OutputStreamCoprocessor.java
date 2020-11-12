package com.weiwan.support.core.coprocessor;

import com.weiwan.support.core.SupportAnnotationScaner;
import com.weiwan.support.core.SupportAppContext;
import com.weiwan.support.core.api.SupportDataFlow;
import org.apache.flink.streaming.api.datastream.DataStreamSink;

/**
 * @Author: xiaozhennan
 * @Date: 2020/11/12 9:53
 * @Package: com.weiwan.support.core.coprocessor.OutputStreamCoprocessor
 * @ClassName: OutputStreamCoprocessor
 * @Description:
 **/
public class OutputStreamCoprocessor extends SupportCoprocessor {
    public OutputStreamCoprocessor(SupportAppContext context) {
        super(context);
    }

    @Override
    public <E, S1, S2> Object process(E env, SupportDataFlow<E, S1, S2> dataFlow, Object obj) {
        DataStreamSink output = dataFlow.output((S2) obj, context);
        return nextProcess(env, dataFlow, output);
    }
}
