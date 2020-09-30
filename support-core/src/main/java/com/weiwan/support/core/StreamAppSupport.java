package com.weiwan.support.core;

import com.weiwan.support.core.api.*;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSink;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * @Author: xiaozhennan
 * @Date: 2020/9/28 16:03
 * @Package: com.weiwan.support.core.FlinkSupportAssembly
 * @ClassName: FlinkSupportAssembly
 * @Description:
 **/
public abstract class StreamAppSupport<T, E> implements FlinkSupport<StreamExecutionEnvironment> {

    private Processer process;
    private Reader reader;
    private Writer writer;
    private StreamExecutionEnvironment env;
    private SupportAppContext context;

    public StreamAppSupport(StreamExecutionEnvironment env, SupportAppContext context) {
        this.env = env;
        this.context = context;
    }



    public void addReader(Reader reader) {
        this.reader = reader;
    }

    public void addProcess(Processer process) {
        this.process = process;
    }

    public void addWriter(Writer writer) {
        this.writer = writer;
    }


    public abstract DataStream<T> openResource(StreamExecutionEnvironment env, SupportAppContext context);

    public abstract DataStream<E> streamProcess(DataStream<T> inputStream);

    public abstract DataStreamSink streamOutput(DataStream<E> outputStream);

    @Override
    public TaskResult submitFlinkTask(StreamExecutionEnvironment env) {
        return null;
    }
}
