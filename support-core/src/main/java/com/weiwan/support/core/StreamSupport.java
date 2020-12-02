/*
 *      Copyright [2020] [xiaozhennan1995@gmail.com]
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 *      http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.weiwan.support.core;

import com.weiwan.support.core.api.*;
import com.weiwan.support.core.config.JobConfig;
import com.weiwan.support.core.constant.SupportKey;
import com.weiwan.support.core.coprocessor.*;
import com.weiwan.support.core.start.RunOptions;
import com.weiwan.support.core.start.TaskResult;
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
public abstract class StreamSupport<I_OUT, P_OUT> implements
        FlinkSupport<StreamExecutionEnvironment>, SupportDataFlow<StreamExecutionEnvironment, DataStream<I_OUT>, DataStream<P_OUT>> {
    private static final Logger _LOGGER = LoggerFactory.getLogger(StreamSupport.class);

    private StreamExecutionEnvironment env;
    private SupportContext internalContext;
    private RunOptions options;

    private CoprocessorChain<StreamExecutionEnvironment, DataStream<I_OUT>, DataStream<P_OUT>> coprocessorChain;


    /**
     * 初始化支持环境,
     * 该方法除了初始化Support运行环境外,还进行TaskGraph协处理器的创建
     *
     * @param executionEnvironment flink环境
     * @param context              supportContext
     * @param options              启动参数
     */
    @Override
    public final void initEnv(StreamExecutionEnvironment executionEnvironment, SupportContext context, RunOptions options) {
        this.env = executionEnvironment;
        this.internalContext = context;
        this.options = options;
        this.coprocessorChain = CoprocessorChainFactory.createStreamCoprocessorChain(env, this, options);
    }

    public final StreamExecutionEnvironment getEnv() {
        return this.env;
    }

    public final SupportContext getContext() {
        return this.internalContext;
    }

    @Override
    public void setContext(SupportContext context) {
        this.internalContext = context;
    }

    /**
     * 运行类{@link com.weiwan.support.runtime.SupportAppEnter} 中反射该方法进行任务提交
     *
     * @return
     * @throws Exception 任务提交出错抛出此异常
     */
    private TaskResult submit() throws Exception {
        FlinkSupport flinkSupport = preProcessing();
        TaskResult taskResult = flinkSupport.executeTask();
        return taskResult;
    }

    public TaskResult executeTask() throws Exception {
        JobConfig jobConfig = internalContext.getJobConfig();
        JobExecutionResult execute = env.execute(jobConfig.getStringVal(SupportKey.APP_NAME, "Support Application"));
        JobID jobID = execute.getJobID();
        _LOGGER.info("the job has been submitted and the job id is {}", jobID.toString());
        TaskResult taskResult = new TaskResult(jobID);
        return taskResult;
    }

    private FlinkSupport preProcessing() throws Exception {
        if (coprocessorChain != null) {
            coprocessorChain.coProcessing();
        }
        return this;
    }


}
