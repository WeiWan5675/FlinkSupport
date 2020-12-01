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
package com.weiwan.support.plugins.writer;

import com.weiwan.support.core.SupportContext;
import com.weiwan.support.core.pojo.DataRecord;
import com.weiwan.support.etl.framework.api.writer.BaseOutputFormat;
import com.weiwan.support.etl.framework.api.writer.BaseWriter;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * @Author: xiaozhennan
 * @Date: 2020/11/18 17:10
 * @Package: com.weiwan.support.plugins.writer.ExampleWriter
 * @ClassName: ExampleWriter
 * @Description:
 **/
public class ExampleWriter extends BaseWriter<DataRecord<String>> {
    public ExampleWriter(StreamExecutionEnvironment env, SupportContext context) {
        super(env, context);
    }

    @Override
    public BaseOutputFormat<DataRecord<String>> getOutputFormat(SupportContext context) {
        return new ExampleOutputFormat(context);
    }

    /**
     * 为什么要在这里有这个方法呢,output是并行得,但是有些前置条件要再并行任务执行前处理,所以提供这个方法
     *
     * @param context
     */
    @Override
    public void writeRequire(SupportContext context) {

    }
}
