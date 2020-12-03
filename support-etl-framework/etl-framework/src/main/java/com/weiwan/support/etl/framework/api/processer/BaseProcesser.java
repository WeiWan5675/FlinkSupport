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
import com.weiwan.support.core.start.RunOptions;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * @Author: xiaozhennan
 * @Date: 2020/7/19 20:13
 * @Package: com.weiwan.support.core
 * @ClassName: BaseChannel
 * @Description:
 **/
public abstract class BaseProcesser<IN, OUT> implements Processer<IN, OUT> {

    private static final String KEY_PROCESSER_NAME = "etl.processer.name";
    private static final String KEY_PROCESSER_TYPE = "etl.processer.type";
    private static final String KEY_PROCESSER_CLASS_NAME = "etl.processer.class";
    private static final String KEY_PROCESSER_PARALLELISM = "etl.processer.parallelism";

    private JobConfig jobConfig;
    private StreamExecutionEnvironment env;
    private SupportContext context;
    private RunOptions runOptions;

    protected ProcesserConfig processerConfig;
    protected String processerName;
    protected String processerType;
    protected String processerClassName;
    protected int processerParallelism;

    public BaseProcesser() {
    }

    @Override
    public SupportContext getContext() {
        return this.context;
    }

    @Override
    public void setContext(SupportContext context) {
        this.context = context;
    }

    @Override
    public void initEnv(StreamExecutionEnvironment env, SupportContext context, RunOptions options) {
        this.env = env;
        this.context = context;
        this.runOptions = options;
        this.jobConfig = context.getJobConfig();
        this.processerConfig = context.getJobConfig().getProcesserConfig();
        this.processerName = processerConfig.getStringVal(KEY_PROCESSER_NAME, "SupportProcesser");
        this.processerClassName = processerConfig.getStringVal(KEY_PROCESSER_CLASS_NAME);
        this.processerParallelism = processerConfig.getIntVal(KEY_PROCESSER_PARALLELISM, 1);
        this.processerType = processerConfig.getStringVal(KEY_PROCESSER_TYPE, "Stream");

    }


    @Override
    public StreamExecutionEnvironment getEnv() {
        return this.env;
    }

    public abstract BaseProcessHandler<IN, OUT> getProcessHandler(SupportContext context);

    public abstract void require(SupportContext context);

    @Override
    public DataStream<OUT> process(DataStream<IN> stream) {
        require(context);
        BaseProcessHandler channelHandler = getProcessHandler(context);
        DataStream<OUT> out = stream.map(channelHandler);
        return out;
    }
}
