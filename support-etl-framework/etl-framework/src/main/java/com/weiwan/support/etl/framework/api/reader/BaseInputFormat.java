package com.weiwan.support.etl.framework.api.reader;

import com.weiwan.support.core.SupportAppContext;
import com.weiwan.support.core.config.JobConfig;
import com.weiwan.support.core.config.ReaderConfig;
import com.weiwan.support.core.pojo.DataRecord;
import com.weiwan.support.etl.framework.streaming.JobFormatState;
import org.apache.flink.api.common.io.DefaultInputSplitAssigner;
import org.apache.flink.api.common.io.RichInputFormat;
import org.apache.flink.api.common.io.statistics.BaseStatistics;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.core.io.InputSplit;
import org.apache.flink.core.io.InputSplitAssigner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

/**
 * @Author: xiaozhennan
 * @Date: 2020/7/14 17:23
 * @Package: org.weiwan.argus.pub.api
 * @ClassName: BaseInputFormat
 * @Description:
 **/
public abstract class BaseInputFormat<OT, T extends InputSplit> extends RichInputFormat<OT, T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaseInputFormat.class);


    protected SupportAppContext context;
    protected JobConfig jobConfig;
    protected ReaderConfig readerConfig;
    protected JobFormatState formatState;


    protected List<DataRecord<? extends Serializable>> batchList;

    //当前任务的index
    protected int indexOfSubTask;
    private boolean taskComplete = false;
    private boolean isRestore;
    private boolean isStream;

    public BaseInputFormat(SupportAppContext context) {
        this.context = context;
        this.jobConfig = context.getJobConfig();
        this.readerConfig = jobConfig.getReaderConfig();
    }

    public BaseInputFormat() {
    }

    /**
     * 打开InputFormat,根据split读取数据
     *
     * @param split 当前处理的分区InputSplit
     */
    public abstract void openInput(T split);

    /**
     * 根据minNumSplits决定如何划分分区
     *
     * @param minNumSplits 最小分区数
     * @return 分区数组
     */
    public abstract T[] getInputSpliter(int minNumSplits);

    /**
     * 返回一条记录
     * 当数据处理结束后,需要手动调用{@link BaseRichInputFormat#isComplete} }
     * 如果不想使用isComplete 需要重写{@link BaseRichInputFormat#reachedEnd()}
     *
     * @return 数据
     */
    public abstract OT nextRecordInternal(OT reuse);

    /**
     * 关闭Input,释放资源
     */
    public abstract void closeInput();


    /**
     * 快照前允许特殊处理
     *
     * @param formatState
     */
    public abstract void snapshot(JobFormatState formatState);

    @Override
    public void configure(Configuration parameters) {
        //什么都不需要做 最先被调用
    }


    @Override
    public void open(T split) throws IOException {
        int indexOfThisSubtask = getRuntimeContext().getIndexOfThisSubtask();

        this.isStream = true;
        //不是restore 就创建一个formatstate
        if (!isRestore()) {
            formatState = new JobFormatState();
            formatState.setNumOfSubTask(indexOfThisSubtask);
            //TODO need to get JobName from http
            formatState.setJobName("input job name");
        } else {
            indexOfSubTask = formatState.getNumOfSubTask();
            //用state的offset恢复
        }
        LOGGER.info("initializeInputFormatSuccessfully");
        //初始化完成,后续调用子类的openInput,打开数据源
        openInput(split);
    }

    @Override
    public void openInputFormat() throws IOException {
        //初始化任务编号
        indexOfSubTask = getRuntimeContext().getIndexOfThisSubtask();

    }

    @Override
    public BaseStatistics getStatistics(BaseStatistics cachedStatistics) throws IOException {
        return null;
    }

    @Override
    public T[] createInputSplits(int minNumSplits) throws IOException {
        return getInputSpliter(minNumSplits);
    }


    @Override
    public InputSplitAssigner getInputSplitAssigner(T[] inputSplits) {
        return new DefaultInputSplitAssigner(inputSplits);
    }


    @Override
    public boolean reachedEnd() throws IOException {
        return isComplete();
    }


    @Override
    public OT nextRecord(OT reuse) throws IOException {
        OT ot = this.nextRecordInternal(reuse);
        return ot;
    }


    @Override
    public void close() throws IOException {
        closeInput();
    }

    public JobFormatState getSnapshotState() {
        this.snapshot(this.formatState);
        return this.formatState;
    }


    public void setJobFormatState(JobFormatState jobFormatState) {
        this.formatState = jobFormatState;
    }

    public void setArgusContext(SupportAppContext context) {
        this.context = context;
    }


    protected boolean isComplete(Boolean... flag) {
        if (flag.length == 1) {
            this.taskComplete = flag[0];
        }
        return taskComplete;
    }

    public boolean isRestore(boolean... flags) {
        if (flags.length == 1) {
            this.isRestore = flags[0];
        }
        return this.isRestore;
    }

    public boolean isStream(boolean ... flags){
        if (flags.length == 1) {
            this.isStream = flags[0];
        }
        return this.isStream;
    }

    public void notifyCheckpointComplete(long checkpointId) {
        //do noting
    }
}