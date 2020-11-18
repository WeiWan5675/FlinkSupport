package com.weiwan.support.etl.framework.api.processer;

import com.weiwan.support.core.SupportAppContext;
import com.weiwan.support.core.config.ProcesserConfig;
import com.weiwan.support.core.config.JobConfig;
import com.weiwan.support.core.pojo.DataRecord;
import org.apache.flink.api.common.functions.RichMapFunction;
import org.apache.flink.runtime.state.FunctionInitializationContext;
import org.apache.flink.runtime.state.FunctionSnapshotContext;
import org.apache.flink.streaming.api.checkpoint.CheckpointedFunction;

/**
 * @Author: xiaozhennan
 * @Date: 2020/11/18 15:36
 * @Package: com.weiwan.support.etl.framework.api.processer.BaseProcessHandler
 * @ClassName: BaseProcessHandler
 * @Description:
 **/
public abstract class BaseProcessHandler<T extends DataRecord, O extends DataRecord> extends RichMapFunction<T, O> implements CheckpointedFunction {

    protected SupportAppContext context;
    protected JobConfig jobConfig;
    protected ProcesserConfig processerConfig;

    public BaseProcessHandler(SupportAppContext context) {
        this.context = context;
        this.jobConfig = context.getJobConfig();
        this.processerConfig = context.getJobConfig().getProcesserConfig();
    }

    public BaseProcessHandler() {
    }

    @Override
    public O map(T value) throws Exception {
        return process(value);
    }


    @Override
    public void snapshotState(FunctionSnapshotContext context) throws Exception {

    }

    @Override
    public void initializeState(FunctionInitializationContext context) throws Exception {

    }


    public abstract O process(T value);
}
