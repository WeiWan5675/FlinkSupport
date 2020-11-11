package com.weiwan.tester.run;

import com.weiwan.support.core.StreamAppSupport;
import com.weiwan.support.core.SupportAppContext;
import com.weiwan.support.core.annotation.*;
import com.weiwan.support.core.pojo.DataRecord;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSink;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.source.RichSourceFunction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author: xiaozhennan
 * @Date: 2020/11/5 14:43
 * @Package: com.weiwan.tester.run.TasterApp
 * @ClassName: TasterApp
 * @Description:
 **/
@PrintToLog
@Support
@Checkpoint
public class TasterApp extends StreamAppSupport<DataRecord<String>, DataRecord<String>> {

    private static final Logger logger = LoggerFactory.getLogger(TasterApp.class);

    @Override
    @SupportKafkaSource
    @SupportMysqlSource
    @SupportSourceParallelism
    public DataStream<DataRecord<String>> open(StreamExecutionEnvironment env, SupportAppContext context) {
        return null;
    }

    @Override
    public DataStream<DataRecord<String>> process(DataStream<DataRecord<String>> stream, SupportAppContext context) {
        return stream.map(new MapFunction<DataRecord<String>, DataRecord<String>>() {
            @Override
            public DataRecord<String> map(DataRecord<String> value) throws Exception {

                return new DataRecord<>(value.getData() + " ++++ map");
            }
        });
    }

    @Override
    public DataStreamSink output(DataStream<DataRecord<String>> stream, SupportAppContext context) {
        return stream.print();
    }


}

