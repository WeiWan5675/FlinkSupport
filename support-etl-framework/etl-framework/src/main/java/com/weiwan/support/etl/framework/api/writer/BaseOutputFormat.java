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
package com.weiwan.support.etl.framework.api.writer;

import com.weiwan.support.api.Support;
import com.weiwan.support.api.config.JobConfig;
import com.weiwan.support.api.config.SupportContext;
import com.weiwan.support.api.config.WriterConfig;
import com.weiwan.support.api.pojo.JobFormatState;
import org.apache.flink.api.common.io.RichOutputFormat;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: xiaozhennan
 * @Date: 2020/7/14 17:23
 * @Package: com.weiwan.support.etl.framework.api.writer
 * @ClassName: BaseRichOutputFormat
 * @Description: BaseRichOutputFormat 负责处理数据,维护状态,调用子类处理数据的方法, 提供Sink调用的方法
 **/
public abstract class BaseOutputFormat<T> extends RichOutputFormat<T>implements Support<StreamExecutionEnvironment> {


    private static final Logger LOGGER = LoggerFactory.getLogger(BaseOutputFormat.class);
    protected SupportContext context;
    protected JobConfig jobConfig;
    protected WriterConfig writerConfig;
    protected JobFormatState formatState;

    protected int taskNumber;
    protected int numTasks;


    //批处理模式支持
    protected List<T> batchRecords;
    protected boolean isBatchWriteMode;
    protected int batchWriteSize;


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
        this.writerConfig = jobConfig.getWriterConfig();
    }

    /**
     * 打开数据源
     *
     * @param taskNumber   当前task的并行索引
     * @param numTasks     task并行度
     * @param SupportContext Support上下文
     */
    public abstract void openOutput(int taskNumber, int numTasks, SupportContext context);


    /**
     * 写出一条记录
     *
     * @param record
     */
    public abstract void writerRecordInternal(T record);


    /**
     * 写出多条记录,如果不实现,会默认调用{@link BaseRichOutputFormat#writerRecordInternal(DataRecord)}
     *
     * @param batchRecords
     */
    public abstract void batchWriteRecordsInternal(List<T> batchRecords) throws IOException;


    /**
     * 关闭output,释放资源
     */
    public abstract void closeOutput() throws IOException;

    /**
     * 进行快照前处理
     *
     * @param formatState
     */
    public abstract void snapshot(JobFormatState formatState) throws IOException;


    @Override
    public void configure(Configuration parameters) {
        //什么都不做,如果子类重写也方便
        System.out.println("output format configure");
    }


    @Override
    public void open(int taskNumber, int numTasks) throws IOException {
        this.isBatchWriteMode = writerConfig.getBooleanVal("writer.batchWriteMode", false);
        this.batchWriteSize = writerConfig.getIntVal("writer.batchWriteSize", 1000);
        this.isStream = true;
        if (isBatchWriteMode) {
            this.batchRecords = new ArrayList(batchWriteSize);
        }
        this.taskNumber = taskNumber;
        this.numTasks = numTasks;
        if (!isRestore()) {
            //不是Restore 需要手动创建formatstate
            formatState = new JobFormatState();
        }
        //子类打开资源
        openOutput(this.taskNumber, this.numTasks, context);
        //子类初始化完成
    }


    @Override
    public void writeRecord(T record) throws IOException {
        if (isBatchWriteMode && batchWriteSize > 1) {
            //批处理模式
            batchRecords.add(record);
            if (batchRecords.size() == batchWriteSize)
                writeRecords();
        } else {
            //逐条处理模式
            writerRecordInternal(record);
        }
    }

    private void writeRecords() {
        try {
            batchWriteRecordsInternal(batchRecords);
        } catch (Exception e) {
            //写入异常
            e.printStackTrace();
            //变成逐条处理
            batchRecords.forEach(this::writerRecordInternal);
        }
        batchRecords.clear();
    }


    @Override
    public void close() throws IOException {
        if (isBatchWriteMode && batchWriteSize > 0) {
            //关闭前将批处理的都写出去
            writeRecords();
        }
        closeOutput();
    }

    public JobFormatState getSnapshotState() throws IOException {
        this.snapshot(formatState);
        return this.formatState;
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

    public void setJobFormatState(JobFormatState jobFormatState) {
        this.formatState = jobFormatState;
    }


    public void checkpointComplete(long currentCheckpointIndex) {
        //do nothing
    }


}
