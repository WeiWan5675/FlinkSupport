package com.weiwan.support.core.api;

import com.weiwan.support.core.SupportAppContext;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * @Author: xiaozhennan
 * @Date: 2020/9/28 15:56
 * @Package: com.weiwan.support.core.api.Reader
 * @ClassName: Reader
 * @Description:
 **/
@FunctionalInterface
public interface Reader<OUT> {

    public DataStream<OUT> read(StreamExecutionEnvironment env, SupportAppContext context);

}
