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
package com.weiwan.support.etl.framework;

import com.weiwan.support.core.StreamSupport;
import com.weiwan.support.core.SupportContext;
import com.weiwan.support.core.pojo.DataRecord;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSink;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * @Author: xiaozhennan
 * @Date: 2020/9/29 14:54
 * @Package: com.weiwan.support.etl.framework.app.StreamETLBaseAPP
 * @ClassName: StreamETLBaseAPP
 * @Description:
 **/

public class StreamETLBase extends StreamSupport<DataRecord, DataRecord> {

    @Override
    public DataStream<DataRecord> open(StreamExecutionEnvironment env, SupportContext context) {
        return null;
    }

    @Override
    public DataStream<DataRecord> process(DataStream<DataRecord> stream) {
        return null;
    }

    @Override
    public DataStreamSink output(DataStream<DataRecord> stream) {
        return null;
    }
}
