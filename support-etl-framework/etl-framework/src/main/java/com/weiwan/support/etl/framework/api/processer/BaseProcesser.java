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
package com.weiwan.support.etl.framework.api.processer;

import com.weiwan.support.core.SupportContext;
import com.weiwan.support.core.api.Processer;
import com.weiwan.support.core.config.ProcesserConfig;
import com.weiwan.support.core.config.JobConfig;
import com.weiwan.support.core.pojo.DataRecord;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * @Author: xiaozhennan
 * @Date: 2020/7/19 20:13
 * @Package: com.weiwan.support.core
 * @ClassName: BaseChannel
 * @Description:
 **/
public abstract class BaseProcesser<IN extends DataRecord, OUT extends DataRecord> implements Processer<IN, OUT> {
    protected JobConfig jobConfig;
    protected StreamExecutionEnvironment env;
    protected String channelName;
    protected SupportContext supportContext;
    protected ProcesserConfig processerConfig;

    public BaseProcesser(StreamExecutionEnvironment env, SupportContext supportContext) {
        this.env = env;
        this.supportContext = supportContext;
        this.jobConfig = supportContext.getJobConfig();
        this.processerConfig = supportContext.getJobConfig().getProcesserConfig();
        this.channelName = processerConfig.getChannleName();
    }

    public abstract BaseProcessHandler<IN, OUT> getProcessHandler(SupportContext context);

    @Override
    public DataStream<OUT> process(DataStream<IN> stream, SupportContext context) {
        BaseProcessHandler channelHandler = getProcessHandler(supportContext);
        DataStream<OUT> out = stream.map(channelHandler);
        return out;
    }
}
