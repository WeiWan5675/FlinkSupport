package com.weiwan.support.core.api;

import com.weiwan.support.core.SupportAppContext;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSink;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * @Author: xiaozhennan
 * @Date: 2020/11/11 15:05
 * @Package: com.weiwan.support.core.api.SupportDataFlow
 * @ClassName: SupportDataFlow
 * @Description:
 **/
public interface SupportDataFlow<E, S1, S2> {

    S1 open(E env, SupportAppContext context);

    S2 process(S1 stream, SupportAppContext context);

    DataStreamSink output(S2 stream, SupportAppContext context);

}
