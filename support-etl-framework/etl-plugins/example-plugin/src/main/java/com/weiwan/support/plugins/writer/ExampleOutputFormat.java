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
package com.weiwan.support.plugins.writer;

import com.weiwan.support.api.config.SupportContext;
import com.weiwan.support.api.pojo.JobFormatState;
import com.weiwan.support.etl.framework.api.writer.BaseOutputFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

/**
 * @Author: xiaozhennan
 * @Date: 2020/11/18 17:11
 * @Package: com.weiwan.support.plugins.writer.ExampleOutputFormat
 * @ClassName: ExampleOutputFormat
 * @Description:
 **/
public class ExampleOutputFormat extends BaseOutputFormat<String> {
    public static final Logger logger = LoggerFactory.getLogger(ExampleOutputFormat.class);
    private String exampleVar;

    /**
     * 打开数据源
     *
     * @param taskNumber 当前task的并行索引
     * @param numTasks   task并行度
     * @param context
     */
    @Override
    public void openOutput(int taskNumber, int numTasks, SupportContext context) {
        exampleVar = writerConfig.getStringVal("writer.example.writerVar");
        logger.info("example output format open");
    }

    /**
     * 写出一条记录
     *
     * @param record
     */
    @Override
    public void writerRecordInternal(String record) {
        logger.info("writer record internal run");
        System.out.println(record.toString());
    }

    /**
     * 写出多条记录,如果不实现,会默认调用{@link BaseRichOutputFormat#writerRecordInternal(DataRecord)}
     *
     * @param batchRecords
     */
    @Override
    public void batchWriteRecordsInternal(List<String> batchRecords) throws IOException {

    }

    /**
     * 关闭output,释放资源
     */
    @Override
    public void closeOutput() throws IOException {
        logger.info("close example output format");
    }

    /**
     * 进行快照前处理
     *
     * @param formatState
     */
    @Override
    public void snapshot(JobFormatState formatState) throws IOException {
        logger.info("snapshot run!");
    }

    public ExampleOutputFormat(SupportContext context) {
        super(context);
    }
}
