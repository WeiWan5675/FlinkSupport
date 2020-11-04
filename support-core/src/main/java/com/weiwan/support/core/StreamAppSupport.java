package com.weiwan.support.core;

import com.weiwan.support.core.api.*;
import org.apache.flink.api.common.JobExecutionResult;
import org.apache.flink.api.common.JobID;
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

    protected Reader<IN> reader;
    protected Processer<IN, OIN> process;
    protected Writer<OIN> writer;
    protected StreamExecutionEnvironment environment;
    protected SupportAppContext appContext;

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
    public TaskResult submitFlinkTask(StreamExecutionEnvironment environment) throws Exception {
        DataStream<IN> inDataStream = this.streamOpen(environment, appContext);
        DataStream<OIN> oinDataStream = this.streamProcess(inDataStream);
        DataStreamSink dataStreamSink = this.streamOutput(oinDataStream);
        JobExecutionResult execute = environment.execute();
        JobID jobID = execute.getJobID();
        System.out.println(jobID);
        return null;
    }

    public StreamExecutionEnvironment getEnvironment() {
        return environment;
    }

    public void setEnvironment(StreamExecutionEnvironment environment) {
        this.environment = environment;
    }

    public SupportAppContext getAppContext() {
        return appContext;
    }

    public void setAppContext(SupportAppContext appContext) {
        this.appContext = appContext;
    }
}
