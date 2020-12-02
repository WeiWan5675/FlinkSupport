/*
 *      Copyright [2020] [xiaozhennan1995@gmail.com]
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 *      http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.weiwan.tester.run;

import com.weiwan.support.core.StreamSupport;
import com.weiwan.support.core.SupportContext;
import com.weiwan.support.core.annotation.*;
import com.weiwan.support.core.junit.SupportTest;
import com.weiwan.support.core.junit.SupportTestConsole;
import com.weiwan.support.plugins.reader.ExampleReader;
import com.weiwan.support.plugins.writer.ExampleWriter;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * @Author: xiaozhennan
 * @Date: 2020/11/30 17:13
 * @Package: com.weiwan.tester.run.TestAnnoSupport
 * @ClassName: TestAnnoSupport
 * @Description:
 **/
@Support(enable = true)
@Parallelism(num = 1)
@SupportTest(jobFile = "F:\\Project\\FlinkSupport\\support-test\\src\\main\\resources\\app.yaml")
public class TestAnnoSupport extends StreamSupport<String, String> {

    @Parallelism(num = 1)
    @PrintStream
    DataStream<String> dataStream1;


    @SupportSource(type = ExampleReader.class, vars = {"etl.reader.example.readereVar=easylife_order"})
    @SupportSink(type = ExampleWriter.class)
    DataStream<String> dataStream2;


    @Override
    @Parallelism(num = 1)
    @SupportSource(type = ExampleReader.class, vars = {})
    public DataStream<String> open(StreamExecutionEnvironment env, SupportContext context) {
        return null;
    }

    @Override
    public DataStream<String> process(DataStream<String> stream) {
        return stream;
    }

    @Override
    @SupportSink(type = ExampleWriter.class)
    public Object output(DataStream<String> stream) {
        return null;
    }


    public static void main(String[] args) throws Exception {
        SupportTestConsole.Builder builder = SupportTestConsole.newBuilder().waitTestClass(TestAnnoSupport.class);
        SupportTestConsole build = builder.build();
        build.run();
    }
}
