package com.weiwan.support.core;

import com.weiwan.support.core.annotation.Support;
import com.weiwan.support.core.api.*;
import com.weiwan.support.core.coprocessor.*;
import com.weiwan.support.core.start.RunOptions;
import org.apache.flink.api.common.JobExecutionResult;
import org.apache.flink.api.common.JobID;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author: xiaozhennan
 * @Date: 2020/9/28 16:03
 * @Package: com.weiwan.support.core.FlinkSupportAssembly
 * @ClassName: FlinkSupportAssembly
 * @Description: 流处理应用支持类
 **/
public abstract class StreamAppSupport<I_OUT, P_OUT> implements
        FlinkSupport<StreamExecutionEnvironment>, SupportDataFlow<StreamExecutionEnvironment, DataStream<I_OUT>, DataStream<P_OUT>> {
    private static final Logger _LOGGER = LoggerFactory.getLogger(StreamAppSupport.class);

    private StreamExecutionEnvironment env;
    private SupportAppContext context;
    private RunOptions options;


    private boolean isEtl;
    private boolean isTable;
    private SupportCoprocessor coprocessors;


    /**
     * 初始化支持环境,
     * 该方法除了初始化Support运行环境外,还进行TaskGraph协处理器的创建
     * @param executionEnvironment flink环境
     * @param context supportContext
     * @param options 启动参数
     */
    @Override
    public final void initEnv(StreamExecutionEnvironment executionEnvironment, SupportAppContext context, RunOptions options) {
        this.env = executionEnvironment;
        this.context = context;
        this.options = options;



        coprocessors = new FirstPreCoprocessor(this.context);  //第一个预处理处理器
        if (options.isEtl()) {
            this.isEtl = options.isEtl();
            coprocessors = coprocessors.nextCoprocessor(new EtlCoprocessor(this.context));  //etl插件模式处理器
        } else if (options.isTable()) {
            this.isTable = options.isTable();
            coprocessors = coprocessors.nextCoprocessor(new TableCoprocessor(this.context));  //table环境处理器
        } else {
            coprocessors.nextCoprocessor(
                    new ClassAnnotationCoprocessor(this.context).nextCoprocessor(  //类注解处理器
                            new OpenStreamCoprocessor(this.context).nextCoprocessor( //open方法处理器
                                    new StreamCoprocessor(this.context).nextCoprocessor(  //process方法处理器
                                            new OutputStreamCoprocessor(this.context))))); //output方法处理器

        }
        coprocessors.nextCoprocessor(new LastPreCoprocessor(this.context));  //最后一个预处理处理器

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

    private FlinkSupport preProcessing() throws Exception {
        if (coprocessors != null) {
            coprocessors.process(env, this, null);
        }
        return this;
    }


}
