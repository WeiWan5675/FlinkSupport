package com.weiwan.support.core;

import com.weiwan.support.core.api.*;
import com.weiwan.support.core.start.RunOptions;
import org.apache.flink.api.java.ExecutionEnvironment;

/**
 * @Author: xiaozhennan
 * @Date: 2020/9/28 16:25
 * @Package: com.weiwan.support.core.BatchAppSupport
 * @ClassName: BatchAppSupport
 * @Description:
 **/
public class BatchAppSupport<IN, OIN> implements FlinkSupport<ExecutionEnvironment> {


    private ExecutionEnvironment environment;
    private SupportAppContext appContext;

    public BatchAppSupport(ExecutionEnvironment environment, SupportAppContext appContext) {
        this.environment = environment;
        this.appContext = appContext;
    }

    @Override
    public void initEnv(ExecutionEnvironment executionEnvironment, SupportAppContext context, RunOptions options) {

    }

    @Override
    public ExecutionEnvironment getEnv() {
        return this.environment;
    }

    @Override
    public SupportAppContext getContext() {
        return this.appContext;
    }

    @Override
    public TaskResult executeTask() throws Exception {
        return null;
    }

    private TaskResult submit() {
        return null;
    }
}
