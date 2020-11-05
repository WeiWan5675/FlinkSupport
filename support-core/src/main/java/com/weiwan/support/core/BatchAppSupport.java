package com.weiwan.support.core;

import com.weiwan.support.core.api.*;
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
    public void initEnv(ExecutionEnvironment executionEnvironment, SupportAppContext context) {

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
    public TaskResult submitFlinkTask(ExecutionEnvironment environment) {
        return null;
    }
}
