package com.weiwan.support.etl.framework.api.writer;

import com.weiwan.support.core.SupportAppContext;
import com.weiwan.support.core.api.Writer;
import com.weiwan.support.core.config.JobConfig;
import com.weiwan.support.core.config.WriterConfig;
import com.weiwan.support.core.pojo.DataRecord;
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
public abstract class BaseWriter<T extends DataRecord> implements Writer<T> {

    protected StreamExecutionEnvironment env;

    protected SupportAppContext context;
    protected JobConfig jobConfig;
    protected WriterConfig writerConfig;
    protected String writerName;
    protected String writerType;
    protected String writerClassName;
    protected Integer writerParallelism;

    private static final String KEY_WRITER_NAME = "writer.name";
    private static final String KEY_WRITER_TYPE = "writer.type";
    private static final String KEY_WRITER_CLASS_NAME = "writer.class";
    private static final String KEY_WRITER_PARALLELISM = "writer.parallelism";


    public BaseWriter(StreamExecutionEnvironment env, SupportAppContext context) {
        this.env = env;
        this.context = context;
        this.jobConfig = context.getJobConfig();
        this.writerConfig = context.getJobConfig().getWriterConfig();
        this.writerName = writerConfig.getStringVal(KEY_WRITER_NAME, "SupportWriter");
        this.writerClassName = writerConfig.getStringVal(KEY_WRITER_CLASS_NAME);
        this.writerType = writerConfig.getStringVal(KEY_WRITER_TYPE);
        this.writerParallelism = writerConfig.getIntVal(KEY_WRITER_PARALLELISM, 1);
    }

    public abstract BaseOutputFormat<T> getOutputFormat(SupportAppContext context);

    /**
     * 为什么要在这里有这个方法呢,output是并行得,但是有些前置条件要再并行任务执行前处理,所以提供这个方法
     *
     * @param context
     */
    public abstract void writeRequire(SupportAppContext context);

    @Override
    public DataStreamSink<T> write(DataStream<T> dataStream, SupportAppContext context) {
        DataStream<T> beforeWritingStream = beforeWriting(dataStream);
        BaseOutputFormat<T> outputFormat = getOutputFormat(context);
        SupportOutputFormatSink<T> outputFormatSink = new SupportOutputFormatSink<T>(outputFormat);
        DataStreamSink<T> sink = beforeWritingStream.addSink(outputFormatSink);
        sink.name(writerName);
        sink.setParallelism(writerParallelism);
        return sink;
    }

    protected DataStream<T> beforeWriting(DataStream<T> dataStream) {
        return dataStream;
    }
}
