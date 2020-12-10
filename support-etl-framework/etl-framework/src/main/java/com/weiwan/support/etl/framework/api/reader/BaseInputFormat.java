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
package com.weiwan.support.etl.framework.api.reader;

import com.weiwan.support.api.Support;
import com.weiwan.support.api.config.JobConfig;
import com.weiwan.support.api.config.ReaderConfig;
import com.weiwan.support.api.config.SupportContext;
import com.weiwan.support.api.options.RunOptions;
import com.weiwan.support.api.pojo.JobFormatState;
import org.apache.flink.api.common.io.DefaultInputSplitAssigner;
import org.apache.flink.api.common.io.RichInputFormat;
import org.apache.flink.api.common.io.statistics.BaseStatistics;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.core.io.InputSplit;
import org.apache.flink.core.io.InputSplitAssigner;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @Author: xiaozhennan
 * @Date: 2020/7/14 17:23
 * @Package: com.weiwan.support.etl.framework.api.reader
 * @ClassName: BaseInputFormat
 * @Description:
 **/
public abstract class BaseInputFormat<OUT, T extends InputSplit> extends RichInputFormat<OUT, T> implements Support<StreamExecutionEnvironment> {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaseInputFormat.class);


    private SupportContext context;
    protected JobConfig jobConfig;
    protected ReaderConfig readerConfig;
    protected JobFormatState formatState;

    //当前任务的index
    protected int indexOfSubTask;
    private boolean taskComplete = false;
    private boolean isRestore;
    private boolean isStream;


    @Override
    public SupportContext getContext() {
        return this.context;
    }

    @Override
    public void setContext(SupportContext context) {
        this.context = context;
        this.jobConfig = context.getJobConfig();
        this.readerConfig = jobConfig.getReaderConfig();
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
     * 当数据处理结束后,需要手动调用{@link BaseInputFormat#isComplete} }
     * 如果不想使用isComplete 需要重写{@link BaseInputFormat#reachedEnd()}
     *
     * @return 数据
     */
    public abstract OUT nextRecordInternal(OUT reuse);

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
    public OUT nextRecord(OUT reuse) throws IOException {
        OUT ot = this.nextRecordInternal(reuse);
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

    public void setSupportContext(SupportContext context) {
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

    public boolean isStream(boolean... flags) {
        if (flags.length == 1) {
            this.isStream = flags[0];
        }
        return this.isStream;
    }

    public void notifyCheckpointComplete(long checkpointId) {
        //do noting
    }
}