package com.weiwan.test.easylife;

import com.weiwan.support.core.StreamAppSupport;
import com.weiwan.support.core.SupportAppContext;
import com.weiwan.support.core.annotation.Support;
import com.weiwan.support.core.pojo.DataRecord;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSink;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.source.SourceFunction;

/**
 * @Author: xiaozhennan
 * @Date: 2020/9/29 11:39
 * @Package: com.weiwan.test.easylife.TestApp
 * @ClassName: TestApp
 * @Description:
 **/
@Support
public class TestApp extends StreamAppSupport<DataRecord<String>, DataRecord<String>> {


    private TestApp(StreamExecutionEnvironment environment, SupportAppContext appContext) {
        super(environment,appContext);
    }

    @Override
    public DataStream<DataRecord<String>> streamOpen(StreamExecutionEnvironment environment, SupportAppContext appContext) {
        return environment.addSource(new SourceFunction<DataRecord<String>>() {
            private int index = -1;

            @Override
            public void run(SourceContext<DataRecord<String>> ctx) throws Exception {
                while (++index == 100) {
                    DataRecord<String> record = new DataRecord<>();
                    record.setData("========" + index + " this is record data ,data index is " + index);
                    ctx.collect(record);
                }
            }

            @Override
            public void cancel() {

            }
        });
    }

    @Override
    public DataStream<DataRecord<String>> streamProcess(DataStream<DataRecord<String>> inputStream) {
        return inputStream.map(new MapFunction<DataRecord<String>, DataRecord<String>>() {
            @Override
            public DataRecord<String> map(DataRecord<String> value) throws Exception {
                value.setData(value.getData() + " 经过map 转化");
                return value;
            }
        });
    }

    @Override
    public DataStreamSink streamOutput(DataStream<DataRecord<String>> outputStream) {
        return outputStream.print();
    }


}
