package com.weiwan.tester.run;

import com.weiwan.support.core.StreamAppSupport;
import com.weiwan.support.core.SupportAppContext;
import com.weiwan.support.core.pojo.DataRecord;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSink;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * @Author: xiaozhennan
 * @Date: 2020/11/5 14:43
 * @Package: com.weiwan.tester.run.TasterApp
 * @ClassName: TasterApp
 * @Description:
 **/
public class TasterApp extends StreamAppSupport<DataRecord<String>, DataRecord<String>> {


    @Override
    public DataStream<DataRecord<String>> streamOpen(StreamExecutionEnvironment environment, SupportAppContext appContext) {
        return null;
    }

    @Override
    public DataStream<DataRecord<String>> streamProcess(DataStream<DataRecord<String>> inputStream) {
        return null;
    }

    @Override
    public DataStreamSink streamOutput(DataStream<DataRecord<String>> outputStream) {
        return null;
    }
}
