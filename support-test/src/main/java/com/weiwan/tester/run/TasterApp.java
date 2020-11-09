package com.weiwan.tester.run;

import com.weiwan.support.core.StreamAppSupport;
import com.weiwan.support.core.SupportAppContext;
import com.weiwan.support.core.pojo.DataRecord;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.streaming.api.checkpoint.CheckpointedFunction;
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
public class TasterApp extends StreamAppSupport<DataRecord<String>, DataRecord<String>> {

    private static final Logger logger = LoggerFactory.getLogger(TasterApp.class);

    @Override
    public DataStream<DataRecord<String>> streamOpen(StreamExecutionEnvironment environment, SupportAppContext appContext) {
        return environment.addSource(new RichSourceFunction<DataRecord<String>>() {
            @Override
            public void run(SourceContext<DataRecord<String>> ctx) throws Exception {
                while (true) {
                    DataRecord<String> record = new DataRecord<>();
                    record.setData("testData");
                    logger.info("生产数据!!!" + record.toString());
                    ctx.collect(record);
                    Thread.sleep(3000L);
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

                return new DataRecord<>(value.getData() + " ++++ map");
            }
        });
    }

    @Override
    public DataStreamSink streamOutput(DataStream<DataRecord<String>> outputStream) {
        return outputStream.print();
    }
}
