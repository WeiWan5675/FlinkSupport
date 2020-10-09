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


    public abstract DataStream<IN> streamOpen(StreamExecutionEnvironment env, SupportAppContext context);

    public abstract DataStream<OIN> streamProcess(DataStream<IN> inputStream);

    public abstract DataStreamSink streamOutput(DataStream<OIN> outputStream);

    @Override
    public TaskResult submitFlinkTask(StreamExecutionEnvironment env) {
        return null;
    }
}
