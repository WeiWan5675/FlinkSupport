package com.weiwan.support.etl.framework;

import com.weiwan.support.core.StreamAppSupport;
import com.weiwan.support.core.SupportAppContext;
import com.weiwan.support.core.pojo.DataRecord;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSink;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * @Author: xiaozhennan
 * @Date: 2020/9/29 14:54
 * @Package: com.weiwan.support.etl.framework.app.StreamETLBaseAPP
 * @ClassName: StreamETLBaseAPP
 * @Description:
 **/

public class StreamETLBaseApp extends StreamAppSupport<DataRecord, DataRecord> {

    @Override
    public DataStream<DataRecord> open(StreamExecutionEnvironment env, SupportAppContext context) {
        return null;
    }

    @Override
    public DataStream<DataRecord> process(DataStream<DataRecord> stream, SupportAppContext context) {
        return null;
    }

    @Override
    public DataStreamSink output(DataStream<DataRecord> stream, SupportAppContext context) {
        return null;
    }
}
