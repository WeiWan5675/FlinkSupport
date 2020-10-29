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
public abstract class StreamAppSupport<IN, OIN> implements FlinkSupport<StreamExecutionEnvironment> {

    private Reader<IN> reader;
    private Processer<IN,OIN> process;
    private Writer<OIN> writer;
    private StreamExecutionEnvironment environment;
    private SupportAppContext appContext;

    public StreamAppSupport(StreamExecutionEnvironment environment, SupportAppContext appContext) {
        this.environment = environment;
        this.appContext = appContext;
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


    public abstract DataStream<IN> streamOpen(StreamExecutionEnvironment environment, SupportAppContext appContext);

    public abstract DataStream<OIN> streamProcess(DataStream<IN> inputStream);

    public abstract DataStreamSink streamOutput(DataStream<OIN> outputStream);


    @Override
    public StreamExecutionEnvironment getEnv() {
        return this.environment;
    }

    @Override
    public SupportAppContext getContext() {
        return this.appContext;
    }

    @Override
    public TaskResult submitFlinkTask(StreamExecutionEnvironment environment) {
        return null;
    }
}
