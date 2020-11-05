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

    private StreamExecutionEnvironment env;
    private SupportAppContext context;


    public abstract DataStream<IN> streamOpen(StreamExecutionEnvironment environment, SupportAppContext appContext);

    public abstract DataStream<OIN> streamProcess(DataStream<IN> inputStream);

    public abstract DataStreamSink streamOutput(DataStream<OIN> outputStream);

    @Override
    public void initEnv(StreamExecutionEnvironment executionEnvironment, SupportAppContext context) {
        this.env = executionEnvironment;
        this.context = context;
    }

    public StreamExecutionEnvironment getEnv() {
        return this.env;
    }

    public SupportAppContext getContext() {
        return this.context;
    }

    public TaskResult submitFlinkTask(StreamExecutionEnvironment environment) throws Exception {
        System.err.println("任务提交!!!");
        DataStream<IN> inDataStream = this.streamOpen(environment, context);
        DataStream<OIN> oinDataStream = this.streamProcess(inDataStream);
        DataStreamSink dataStreamSink = this.streamOutput(oinDataStream);
        JobExecutionResult execute = environment.execute();
        JobID jobID = execute.getJobID();
        System.out.println(jobID);
        return null;
    }


}
