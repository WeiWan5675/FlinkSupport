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
package com.weiwan.support.plugins.reader;

import com.weiwan.support.api.config.SupportContext;
import com.weiwan.support.api.pojo.DataField;
import com.weiwan.support.api.pojo.DataRecord;
import com.weiwan.support.api.pojo.DataRow;
import com.weiwan.support.api.pojo.JobFormatState;
import com.weiwan.support.common.utils.DateUtils;
import com.weiwan.support.api.enums.SupportType;
import com.weiwan.support.etl.framework.api.reader.BaseInputFormat;
import org.apache.flink.core.io.GenericInputSplit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * @Author: xiaozhennan
 * @Date: 2020/11/18 17:08
 * @Package: com.weiwan.support.plugins.reader.ExampleInputFormat
 * @ClassName: ExampleInputFormat
 * @Description:
 **/
public class ExampleInputFormat extends BaseInputFormat<String, GenericInputSplit> {
    public static final Logger logger = LoggerFactory.getLogger(ExampleInputFormat.class);
    int endIndex = 1000;
    private int currentIndex;

    /**
     * 打开InputFormat,根据split读取数据
     *
     * @param split 当前处理的分区InputSplit
     */
    @Override
    public void openInput(GenericInputSplit split) {
        this.endIndex = readerConfig.getIntVal("etl.reader.example.readerVar", 1000);
        logger.info("open the ExampleInputFormat");
    }

    /**
     * 根据minNumSplits决定如何划分分区
     *
     * @param minNumSplits 最小分区数
     * @return 分区数组
     */
    @Override
    public GenericInputSplit[] getInputSpliter(int minNumSplits) {
        logger.info("get input spliter run");
        GenericInputSplit[] splits = new GenericInputSplit[minNumSplits];
        for (int i = 0; i < minNumSplits; i++) {
            GenericInputSplit exampleInputSplit = new GenericInputSplit(i, minNumSplits);
            splits[i] = exampleInputSplit;
        }
        return splits;
    }

    /**
     * 返回一条记录
     * 当数据处理结束后,需要手动调用{@link BaseInputFormat#isComplete} }
     * 如果不想使用isComplete 需要重写{@link BaseInputFormat#reachedEnd()}
     *
     * @param reuse
     * @return 数据
     */
    @Override
    public String nextRecordInternal(String reuse) {
        logger.info("next record internal execution, currentIndex:{}", currentIndex);
        DataRecord<DataRow<DataField>> dataRecord = new DataRecord<>();
        DataRow<DataField> dataFieldDataRow = new DataRow(DataField.class, 1);
        DataField<Object> dataField = new DataField<>();
        dataField.setFieldType(SupportType.STRING);
        dataField.setValue(String.format("exampleValue:%s", currentIndex));
        dataField.setFieldKey("exampleKey");
        dataFieldDataRow.setField(0, dataField);
        dataRecord.setData(dataFieldDataRow);
        dataRecord.setTableName("ExampleTableName");
        dataRecord.setSchemaName("ExampleSchema");
        dataRecord.setTimestamp(DateUtils.getDateStr(new Date()));
        if (currentIndex++ == endIndex) {
            isComplete(true);
        }
        try {
            Thread.sleep(3000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return dataRecord.toString();
    }

    /**
     * 关闭Input,释放资源
     */
    @Override
    public void closeInput() {
        logger.info("close this ExampleInputFormat");
    }

    /**
     * 快照前允许特殊处理
     *
     * @param formatState
     */
    @Override
    public void snapshot(JobFormatState formatState) {
        if (isRestore()) {
            logger.info("snapshot run! this Startup Is Restore");
        }
        logger.info("snapshot run!");
    }
}
