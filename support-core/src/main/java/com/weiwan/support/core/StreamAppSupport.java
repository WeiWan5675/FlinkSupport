package com.weiwan.support.core;

import com.weiwan.support.core.annotation.Support;
import com.weiwan.support.core.api.*;
import com.weiwan.support.core.coprocessor.*;
import com.weiwan.support.core.start.RunOptions;
import org.apache.flink.api.common.JobExecutionResult;
import org.apache.flink.api.common.JobID;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSink;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    private SupportCoprocessor coprocessors = new EnvCoprocessor();

    private static final Logger _LOGGER = LoggerFactory.getLogger(StreamAppSupport.class);


    @Override
    public final void initEnv(StreamExecutionEnvironment executionEnvironment, SupportAppContext context, RunOptions options) {
        this.env = executionEnvironment;
        this.context = context;
        this.options = options;


        /**
         * 这里使用了责任链,通过添加不同得coprocessor 进行任务执行前预处理工作
         * 默认包含一个{@link EnvCoprocessor}
         */
        if (options.isEtl()) {
            this.isEtl = options.isEtl();
            coprocessors = coprocessors.nextCoprocessor(new EtlCoprocessor());
        } else if (options.isTable()) {
            this.isTable = options.isTable();
            coprocessors = coprocessors.nextCoprocessor(new TableCoprocessor());
        } else {
            Support annotation = this.getClass().getAnnotation(Support.class);
            if (annotation != null) {
                enableAnnotation = true;
                coprocessors.nextCoprocessor(new AnnotationCoprocessor(context));
            }
            coprocessors.nextCoprocessor(
                    new OpenStreamCoprocessor(context).nextCoprocessor(
                            new StreamCoprocessor(context).nextCoprocessor(
                                    new OutputStreamCoprocessor(context))));
        }

    }

    public final StreamExecutionEnvironment getEnv() {
        return this.env;
    }

    public final SupportAppContext getContext() {
        return this.context;
    }

    /**
     * 运行类{@link com.weiwan.support.runtime.SupportAppEnter} 中反射该方法进行任务提交
     *
     * @return
     * @throws Exception
     */
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
        if (coprocessors != null) {
            coprocessors.process(env, this, null);
        }
        return this;
    }


}
