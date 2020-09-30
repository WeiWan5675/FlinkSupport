package com.weiwan.test.easylife;

import com.weiwan.support.core.StreamAppSupport;
import com.weiwan.support.core.SupportAppContext;
import com.weiwan.support.core.pojo.DataField;
import com.weiwan.support.core.pojo.DataRecord;
import com.weiwan.support.core.pojo.DataRow;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.streaming.api.checkpoint.CheckpointedFunction;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSink;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.source.RichSourceFunction;
import org.apache.flink.streaming.api.functions.source.SourceFunction;

/**
 * @Author: xiaozhennan
 * @Date: 2020/9/29 11:39
 * @Package: com.weiwan.test.easylife.TestApp
 * @ClassName: TestApp
 * @Description:
 **/
public class TestApp extends StreamAppSupport<DataRecord<DataRow<DataField<String>>>, String> {


    public TestApp(StreamExecutionEnvironment env, SupportAppContext context) {
        super(env, context);
    }

    @Override
    public DataStream<DataRecord<DataRow<DataField<String>>>> openResource(StreamExecutionEnvironment env, SupportAppContext context) {
        DataStreamSource<DataRecord<DataRow<DataField<String>>>> dataRecordDataStreamSource = env.addSource(new RichSourceFunction<DataRecord<DataRow<DataField<String>>>>() {
            @Override
            public void run(SourceContext<DataRecord<DataRow<DataField<String>>>> ctx) throws Exception {

            }

            @Override
            public void cancel() {

            }
        });
        return dataRecordDataStreamSource;
    }

    @Override
    public DataStream<String> streamProcess(DataStream<DataRecord<DataRow<DataField<String>>>> inputStream) {
        SingleOutputStreamOperator<String> map = inputStream.map(new MapFunction<DataRecord<DataRow<DataField<String>>>, String>() {

            @Override
            public String map(DataRecord<DataRow<DataField<String>>> value) throws Exception {
                return null;
            }
        });

        return map;
    }

    @Override
    public DataStreamSink streamOutput(DataStream<String> outputStream) {
        DataStreamSink<String> stringDataStreamSink = outputStream.writeAsCsv("");
        return stringDataStreamSink;
    }


}
