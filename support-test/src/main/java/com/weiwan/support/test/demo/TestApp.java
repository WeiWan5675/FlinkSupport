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
package com.weiwan.support.test.demo;

import com.weiwan.support.api.config.SupportContext;
import com.weiwan.support.core.StreamSupport;
import com.weiwan.support.core.annotation.*;
import com.weiwan.support.api.pojo.DataRecord;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSink;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.sink.SinkFunction;
import org.apache.flink.streaming.api.functions.source.SourceFunction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author: xiaozhennan
 * @Date: 2020/11/5 14:43
 * @Package: com.weiwan.tester.run.Tester
 * @ClassName: Tester
 * @Description:
 **/
@Support
public class TestApp extends StreamSupport<DataRecord<String>, DataRecord<String>> {

    private static final Logger logger = LoggerFactory.getLogger(TestApp.class);

    @Override
    public DataStream<DataRecord<String>> open(StreamExecutionEnvironment env, SupportContext context) {
        return env.addSource(new SourceFunction<DataRecord<String>>() {
            @Override
            public void run(SourceContext<DataRecord<String>> ctx) throws Exception {
                while (true) {
                    DataRecord<String> record = new DataRecord<>();
                    record.setData("test data");
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
    public DataStream<DataRecord<String>> process(DataStream<DataRecord<String>> stream) {
        return stream.map(new MapFunction<DataRecord<String>, DataRecord<String>>() {
            @Override
            public DataRecord<String> map(DataRecord<String> value) throws Exception {

                return new DataRecord<>(value.getData() + " ++++ map");
            }
        });
    }

    @Override
    public DataStreamSink output(DataStream<DataRecord<String>> stream) {

        return stream.addSink(new SinkFunction<DataRecord<String>>() {

            @Override
            public void invoke(DataRecord<String> value, Context context) throws Exception {
                logger.info(value.getData());
            }
        });
    }


}

