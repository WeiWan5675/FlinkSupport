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
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSink;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * @Author: xiaozhennan
 * @Date: 2020/11/22 14:06
 * @Package: com.weiwan.tester.run
 * @ClassName: TesterAppV2
 * @Description:
 **/
@SupportTest(jobFile = "F:\\Project\\FlinkSupport\\support-test\\src\\main\\resources\\app.yaml")
@Support
@Parallelism
@PrintToLog
@Checkpoint
public class TestConsoleTester extends StreamSupport<String, String> {


    public static void main(String[] args) throws Exception {
        SupportTestConsole build = SupportTestConsole.newBuilder().waitTestClass(TestConsoleTester.class).build();
        build.run();
    }


    @Override
    public DataStream<String> open(StreamExecutionEnvironment env, SupportContext context) {
        return null;
    }

    @Override
    public DataStream<String> process(DataStream<String> stream) {

        return null;
    }

    @Override
    public DataStreamSink output(DataStream<String> stream) {
        return null;
    }
}
