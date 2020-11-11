package com.weiwan.support.core;

import com.weiwan.support.core.api.*;
import com.weiwan.support.core.start.RunOptions;
import org.apache.flink.api.common.JobExecutionResult;
import org.apache.flink.api.common.JobID;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSink;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: xiaozhennan
 * @Date: 2020/9/28 16:03
 * @Package: com.weiwan.support.core.FlinkSupportAssembly
 * @ClassName: FlinkSupportAssembly
 * @Description:
 **/
public abstract class StreamAppSupport<I_OUT, P_OUT> implements
        FlinkSupport<StreamExecutionEnvironment>, SupportDataFlow<StreamExecutionEnvironment, DataStream<I_OUT>, DataStream<P_OUT>> {

    private StreamExecutionEnvironment env;
    private SupportAppContext context;
    private RunOptions options;
    private boolean isEtl;
    private boolean isTable;
    private boolean enableAnnotation;
    private Map<Class, SupportCoprocessor> coprocessors = new LinkedHashMap<Class, SupportCoprocessor>();


    private static final Logger _LOGGER = LoggerFactory.getLogger(StreamAppSupport.class);


    @Override
    public final void initEnv(StreamExecutionEnvironment executionEnvironment, SupportAppContext context, RunOptions options) {
        this.env = executionEnvironment;
        this.context = context;
        this.options = options;
        this.isEtl = options.isEtl();
        this.isTable = options.isTable();
        this.enableAnnotation = options.isEnableAnnotation();
        if (isEtl) {
            coprocessors.put(EtlCoprocessor.class, new EtlCoprocessor());
        }
        if (isTable) {
            coprocessors.put(TableCoprocessor.class, new TableCoprocessor());
        }
        if (enableAnnotation) {
            coprocessors.put(AnnotationCoprocessor.class, new AnnotationCoprocessor());
        }
    }

    public final StreamExecutionEnvironment getEnv() {
        return this.env;
    }

    public final SupportAppContext getContext() {
        return this.context;
    }

    private TaskResult submit() throws Exception {
        FlinkSupport flinkSupport = preProcessing();
        TaskResult taskResult = flinkSupport.executeTask();
        return taskResult;
    }

    public TaskResult executeTask() throws Exception {
        JobExecutionResult execute = env.execute();
        JobID jobID = execute.getJobID();
        _LOGGER.info("the job has been submitted and the job id is {}", jobID.toString());
        TaskResult taskResult = new TaskResult(jobID);
        return taskResult;
    }

    private FlinkSupport preProcessing() {
        if (coprocessors.size() < 1) {
            DataStream<I_OUT> open = this.open(env, context);
            DataStream<P_OUT> process = this.process(open, context);
            DataStreamSink output = this.output(process, context);
            return this;
        } else {
            SupportCoprocessor sc1 = coprocessors.get(AnnotationCoprocessor.class);
            if (sc1 != null) {
                sc1.process(this);
            }

            SupportCoprocessor sc2 = coprocessors.get(EtlCoprocessor.class);
            if (sc2 != null) {
                sc2.process(this);
            }

            SupportCoprocessor sc3 = coprocessors.get(EtlCoprocessor.class);
            if (sc3 != null) {
                sc3.process(this);
            }
        }
        return this;
    }


}
