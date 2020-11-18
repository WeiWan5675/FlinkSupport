package com.weiwan.support.plugins.writer;

import com.weiwan.support.core.SupportAppContext;
import com.weiwan.support.core.pojo.DataRecord;
import com.weiwan.support.etl.framework.api.writer.BaseOutputFormat;
import com.weiwan.support.etl.framework.streaming.JobFormatState;

import java.io.IOException;
import java.util.List;

/**
 * @Author: xiaozhennan
 * @Date: 2020/11/18 17:11
 * @Package: com.weiwan.support.plugins.writer.ExampleOutputFormat
 * @ClassName: ExampleOutputFormat
 * @Description:
 **/
public class ExampleOutputFormat extends BaseOutputFormat<DataRecord<String>> {
    /**
     * 打开数据源
     *
     * @param taskNumber 当前task的并行索引
     * @param numTasks   task并行度
     * @param context
     */
    @Override
    public void openOutput(int taskNumber, int numTasks, SupportAppContext context) {

    }

    /**
     * 写出一条记录
     *
     * @param record
     */
    @Override
    public void writerRecordInternal(DataRecord<String> record) {

    }

    /**
     * 写出多条记录,如果不实现,会默认调用{@link BaseRichOutputFormat#writerRecordInternal(DataRecord)}
     *
     * @param batchRecords
     */
    @Override
    public void batchWriteRecordsInternal(List<DataRecord<String>> batchRecords) throws IOException {

    }

    /**
     * 关闭output,释放资源
     */
    @Override
    public void closeOutput() throws IOException {

    }

    /**
     * 进行快照前处理
     *
     * @param formatState
     */
    @Override
    public void snapshot(JobFormatState formatState) throws IOException {

    }

    public ExampleOutputFormat(SupportAppContext context) {
        super(context);
    }
}
