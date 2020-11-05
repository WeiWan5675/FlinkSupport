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
public abstract class StreamAppSupport<IN, OIN>  {

    private Reader<IN> reader;
    private Processer<IN, OIN> process;
    private Writer<OIN> writer;
    private StreamExecutionEnvironment env;
    private SupportAppContext context;

    public StreamAppSupport(StreamExecutionEnvironment environment, SupportAppContext appContext) {
        this.env = environment;
        this.context = appContext;
    }

    public StreamAppSupport() {
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


    public StreamExecutionEnvironment getEnv() {
        return this.env;
    }

    public SupportAppContext getContext() {
        return this.context;
    }

    public TaskResult submitFlinkTask(StreamExecutionEnvironment environment) throws Exception {
        DataStream<IN> inDataStream = this.streamOpen(environment, context);
        DataStream<OIN> oinDataStream = this.streamProcess(inDataStream);
        DataStreamSink dataStreamSink = this.streamOutput(oinDataStream);
        JobExecutionResult execute = environment.execute();
        JobID jobID = execute.getJobID();
        System.out.println(jobID);
        return null;
    }

}
