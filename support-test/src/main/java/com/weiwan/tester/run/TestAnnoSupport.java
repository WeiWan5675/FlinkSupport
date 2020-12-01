package com.weiwan.tester.run;

import com.weiwan.support.core.StreamSupport;
import com.weiwan.support.core.SupportContext;
import com.weiwan.support.core.annotation.Parallelism;
import com.weiwan.support.core.annotation.PrintStream;
import com.weiwan.support.core.annotation.Support;
import com.weiwan.support.core.annotation.SupportSource;
import com.weiwan.support.core.enums.SinkElement;
import com.weiwan.support.core.enums.SourceElement;
import com.weiwan.support.core.junit.SupportTest;
import com.weiwan.support.core.junit.SupportTestConsole;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSink;
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
public class TestAnnoSupport extends StreamSupport<String, String> {

    @Parallelism(num = 2)
    @PrintStream
    DataStream<String> dataStream1;


    /**
     * etl:
     *   reader:
     *     name: ExampleReader #Reader插件
     *     class: com.weiwan.support.plugins.reader.ExampleReader
     *     parallelism: 1
     *     example:
     *       readereVar: 1000
     */
    @SupportSource(type = SourceElement.ExampleSource,vars = {"etl.reader.example.readereVar=easylife_order"})
    DataStream<String> dataStream2;
//
//    @SupportSource(type = SourceElement.ExampleSource,path="b.txt")
//    DataStream<String> dataStream3;
//
//    @SourceSink(type = SinkElement.HdfsSink,input = "dataStream3")
//    DataStreamSink sink1;


    @Override
    @Parallelism(num = 1)
    public DataStream<String> open(StreamExecutionEnvironment env, SupportContext context) {
        dataStream1 = env.addSource(new SourceFunction<String>() {
            @Override
            public void run(SourceContext<String> ctx) throws Exception {
                int index = 1;
                while (index++ < 2) {
                    ctx.collect("test =========== + test");
                }
            }

            @Override
            public void cancel() {

            }
        });
        dataStream1.print();
        return dataStream1;
    }

    @Override
    public DataStream<String> process(DataStream<String> stream) {
        return stream;
    }

    @Override
    public DataStreamSink output(DataStream<String> stream) {
        dataStream2.print();
        return null;
    }


    public static void main(String[] args) throws Exception {
        SupportTestConsole.Builder builder = SupportTestConsole.newBuilder().waitTestClass(TestAnnoSupport.class);
        SupportTestConsole build = builder.build();
        build.run();
    }
}
