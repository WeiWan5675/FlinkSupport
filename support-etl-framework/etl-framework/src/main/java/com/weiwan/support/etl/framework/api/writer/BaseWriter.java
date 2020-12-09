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
package com.weiwan.support.etl.framework.api.writer;

import com.weiwan.support.api.config.JobConfig;
import com.weiwan.support.api.config.SupportContext;
import com.weiwan.support.api.config.WriterConfig;
import com.weiwan.support.api.etl.Writer;
import com.weiwan.support.api.options.RunOptions;
import com.weiwan.support.etl.framework.streaming.SupportOutputFormatSink;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSink;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * @Author: xiaozhennan
 * @Date: 2020/7/14 17:03
 * @Package: com.weiwan.support.etl.framework.api.writer
 * @ClassName: BaseWriter
 * @Description:
 **/
public abstract class BaseWriter<IN> implements Writer<IN> {


    private static final String KEY_WRITER_NAME = "etl.writer.name";
    private static final String KEY_WRITER_TYPE = "etl.writer.type";
    private static final String KEY_WRITER_CLASS_NAME = "etl.writer.class";
    private static final String KEY_WRITER_PARALLELISM = "etl.writer.parallelism";

    private StreamExecutionEnvironment env;
    private SupportContext context;
    private JobConfig jobConfig;
    private RunOptions runOptions;

    protected WriterConfig writerConfig;
    protected String writerName;
    protected String writerType;
    protected String writerClassName;
    protected Integer writerParallelism;


    public BaseWriter() {
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
        this.writerConfig = context.getJobConfig().getWriterConfig();
        this.writerName = writerConfig.getStringVal(KEY_WRITER_NAME, "SupportWriter");
        this.writerClassName = writerConfig.getStringVal(KEY_WRITER_CLASS_NAME);
        this.writerType = writerConfig.getStringVal(KEY_WRITER_TYPE, "Stream");
        this.writerParallelism = writerConfig.getIntVal(KEY_WRITER_PARALLELISM, 1);
    }

    @Override
    public StreamExecutionEnvironment getEnv() {
        return this.env;
    }

    public abstract BaseOutputFormat<IN> getOutputFormat(SupportContext context);

    /**
     * 前置条件
     *
     * @param context
     */
    public abstract void require(SupportContext context);

    @Override
    public DataStreamSink<IN> write(DataStream<IN> dataStream) {
        require(context);
        DataStream<IN> beforeWritingStream = beforeWriting(dataStream);
        BaseOutputFormat<IN> outputFormat = getOutputFormat(context);
        SupportOutputFormatSink<IN> outputFormatSink = new SupportOutputFormatSink<IN>(outputFormat);
        DataStreamSink<IN> sink = beforeWritingStream.addSink(outputFormatSink);
        sink.name(writerName);
        sink.setParallelism(writerParallelism);
        return sink;
    }

    protected DataStream<IN> beforeWriting(DataStream<IN> dataStream) {
        //do nothing
        return dataStream;
    }
}
