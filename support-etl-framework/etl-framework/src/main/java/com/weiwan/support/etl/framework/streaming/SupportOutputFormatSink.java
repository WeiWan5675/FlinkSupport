package com.weiwan.support.etl.framework.streaming;

import com.weiwan.support.core.SupportAppContext;
import com.weiwan.support.etl.framework.api.writer.BaseOutputFormat;
import org.apache.flink.api.common.ExecutionConfig;
import org.apache.flink.api.common.functions.RuntimeContext;
import org.apache.flink.api.common.io.CleanupWhenUnsuccessful;
import org.apache.flink.api.common.io.OutputFormat;
import org.apache.flink.api.common.io.RichOutputFormat;
import org.apache.flink.api.common.state.ListState;
import org.apache.flink.api.common.state.ListStateDescriptor;
import org.apache.flink.api.common.state.OperatorStateStore;
import org.apache.flink.api.common.typeinfo.TypeHint;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.java.typeutils.InputTypeConfigurable;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.runtime.state.CheckpointListener;
import org.apache.flink.runtime.state.FunctionInitializationContext;
import org.apache.flink.runtime.state.FunctionSnapshotContext;
import org.apache.flink.streaming.api.checkpoint.CheckpointedFunction;
import org.apache.flink.streaming.api.functions.sink.RichSinkFunction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: xiaozhennan
 * @Date: 2020/7/14 17:09
 * @Package: com.weiwan.support.pub.api
 * @ClassName: SupportOutputFormatSink
 * @Description:
 **/
public class SupportOutputFormatSink<T> extends RichSinkFunction<T> implements InputTypeConfigurable, CheckpointedFunction, CheckpointListener {

    private static final long serialVersionUID = 1L;
    private static final Logger LOG = LoggerFactory.getLogger(SupportOutputFormatSink.class);
    private OutputFormat<T> format;
    private boolean cleanupCalled = false;


    private ListState<JobFormatState> listState;
    private Map<Integer, JobFormatState> cacheMapStates;
    private boolean isRestore;
    private SupportAppContext context;
    private long currentCheckpointIndex;

    public SupportOutputFormatSink(OutputFormat<T> format) {
        this.format = format;
    }

    @Override
    public void open(Configuration parameters) throws Exception {
        RuntimeContext context = getRuntimeContext();
        format.configure(parameters);
        int indexInSubtaskGroup = context.getIndexOfThisSubtask();
        int currentNumberOfSubtasks = context.getNumberOfParallelSubtasks();

        //在启动时,配置context | formatstate
        if (format instanceof BaseOutputFormat) {
            BaseOutputFormat outputFormat = ((BaseOutputFormat) format);
            if (isRestore) {
                outputFormat.isRestore(true);
                outputFormat.setJobFormatState(cacheMapStates.get(indexInSubtaskGroup));
            }
        }


        format.open(indexInSubtaskGroup, currentNumberOfSubtasks);
    }

    @Override
    public void setRuntimeContext(RuntimeContext context) {
        super.setRuntimeContext(context);
        if (format instanceof RichOutputFormat) {
            ((RichOutputFormat) format).setRuntimeContext(context);
        }
    }

    @Override
    public void setInputType(TypeInformation<?> type, ExecutionConfig executionConfig) {
        if (format instanceof InputTypeConfigurable) {
            InputTypeConfigurable itc = (InputTypeConfigurable) format;
            itc.setInputType(type, executionConfig);
        }
    }


    @Override
    public void invoke(T value, Context context) throws Exception {
        try {
            format.writeRecord(value);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public void close() throws Exception {
        try {
            format.close();
        } catch (Exception ex) {
            cleanup();
            throw ex;
        }
    }

    private void cleanup() {
        try {
            if (!cleanupCalled && format instanceof CleanupWhenUnsuccessful) {
                cleanupCalled = true;
                ((CleanupWhenUnsuccessful) format).tryCleanupOnError();
            }
        } catch (Throwable t) {
            LOG.error("Cleanup on error failed.", t);
        }
    }

    public OutputFormat<T> getFormat() {
        return format;
    }


    @Override
    public void snapshotState(FunctionSnapshotContext context) throws Exception {
        JobFormatState formatState = ((BaseOutputFormat) format).getSnapshotState();
        if (formatState != null) {
            listState.clear();
            listState.add(formatState);
        }
    }


    @Override
    public void initializeState(FunctionInitializationContext context) throws Exception {
        OperatorStateStore stateStore = context.getOperatorStateStore();
        listState = stateStore.getUnionListState(new ListStateDescriptor<>(
                "input-format-state",
                TypeInformation.of(new TypeHint<JobFormatState>() {
                })));
        if (context.isRestored()) {
            isRestore = true;
            //如果是restore 就把restore的jobformatstate 缓存起来,在open中,把对应任务的state设置进去
            cacheMapStates = new HashMap<>(16);
            for (JobFormatState formatState : listState.get()) {
                cacheMapStates.put(formatState.getNumOfSubTask(), formatState);
            }
        }
    }


    @Override
    public void notifyCheckpointComplete(long checkpointId) throws Exception {
        //只有这个类型的outputformat才进行notifyCheckpoint通知
        if (format != null && format instanceof BaseOutputFormat) {
            this.currentCheckpointIndex = checkpointId;
            ((BaseOutputFormat) format).checkpointComplete(currentCheckpointIndex);
        }
    }


    @Override
    public void notifyCheckpointAborted(long checkpointId) throws Exception {
        //not yet Supported
    }
}
