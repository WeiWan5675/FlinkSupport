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
public class BatchAppSupport implements FlinkSupport<ExecutionEnvironment> {


    private ExecutionEnvironment env;
    private SupportAppContext context;

    public BatchAppSupport(ExecutionEnvironment env, SupportAppContext context) {
        this.env = env;
        this.context = context;
    }

    @Override
    public void reader(Reader reader) {
        return;
    }

    @Override
    public void process(Processer porcesser) {
        return;
    }

    @Override
    public void writer(Writer writer) {
        return;
    }

    @Override
    public TaskResult submitFlinkTask(ExecutionEnvironment env) {
        return null;
    }
}
