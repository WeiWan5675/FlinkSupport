package com.weiwan.tester.run;

import com.weiwan.support.core.StreamAppSupport;
import com.weiwan.support.core.SupportAppContext;
import com.weiwan.support.core.annotation.Parallelism;
import com.weiwan.support.core.annotation.Support;
import com.weiwan.support.core.junit.SupportTest;
import com.weiwan.support.core.junit.SupportTestConsole;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSink;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.source.SourceFunction;

/**
 * @Author: xiaozhennan
 * @Date: 2020/11/30 17:13
 * @Package: com.weiwan.tester.run.TestAnnoSupport
 * @ClassName: TestAnnoSupport
 * @Description:
 **/
@Support
@Parallelism(num = 1)
@SupportTest(jobFile = "F:\\Project\\FlinkSupport\\support-test\\src\\main\\resources\\app.yaml")
public class TestAnnoSupport extends StreamAppSupport<String, String> {

    @Parallelism(num = 1)
    DataStream<String> dataStream1;

    @Override
    @Parallelism(num = 1)
    public DataStream<String> open(StreamExecutionEnvironment env, SupportAppContext context) {
        dataStream1 = env.addSource(new SourceFunction<String>() {
            @Override
            public void run(SourceContext<String> ctx) throws Exception {


            }

            @Override
            public void cancel() {

            }
        });

        return dataStream1;
    }

    @Override
    public DataStream<String> process(DataStream<String> stream) {
        return null;
    }

    @Override
    public DataStreamSink output(DataStream<String> stream) {
        return null;
    }


    public static void main(String[] args) throws Exception {
        SupportTestConsole.Builder builder = SupportTestConsole.newBuilder().waitTestClass(TestAnnoSupport.class);
        SupportTestConsole build = builder.build();
        build.run();
    }
}
