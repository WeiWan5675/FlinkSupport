package com.weiwan.support.core;

import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSink;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * @Author: xiaozhennan
 * @Date: 2020/11/5 13:52
 * @Package: com.weiwan.support.core.AppRe
 * @ClassName: AppRe
 * @Description:
 **/
public class AppRe extends StreamAppSupport<String,String>{

    @Override
    public DataStream<String> streamOpen(StreamExecutionEnvironment environment, SupportAppContext appContext) {
        return null;
    }

    @Override
    public DataStream<String> streamProcess(DataStream<String> inputStream) {
        return null;
    }

    @Override
    public DataStreamSink streamOutput(DataStream<String> outputStream) {
        return null;
    }
}
