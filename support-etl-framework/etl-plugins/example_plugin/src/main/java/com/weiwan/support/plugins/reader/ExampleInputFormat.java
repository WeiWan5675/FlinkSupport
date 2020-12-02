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

import com.weiwan.support.core.SupportContext;
import com.weiwan.support.etl.framework.api.reader.BaseInputFormat;
import com.weiwan.support.etl.framework.streaming.JobFormatState;
import org.apache.flink.core.io.GenericInputSplit;
import org.apache.flink.core.io.InputSplit;

/**
 * @Author: xiaozhennan
 * @Date: 2020/11/18 17:08
 * @Package: com.weiwan.support.plugins.reader.ExampleInputFormat
 * @ClassName: ExampleInputFormat
 * @Description:
 **/
public class ExampleInputFormat extends BaseInputFormat<String, GenericInputSplit> {

    /**
     * 打开InputFormat,根据split读取数据
     *
     * @param split 当前处理的分区InputSplit
     */
    @Override
    public void openInput(GenericInputSplit split) {

    }

    /**
     * 根据minNumSplits决定如何划分分区
     *
     * @param minNumSplits 最小分区数
     * @return 分区数组
     */
    @Override
    public GenericInputSplit[] getInputSpliter(int minNumSplits) {
        return new GenericInputSplit[0];
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
        return null;
    }

    /**
     * 关闭Input,释放资源
     */
    @Override
    public void closeInput() {

    }

    /**
     * 快照前允许特殊处理
     *
     * @param formatState
     */
    @Override
    public void snapshot(JobFormatState formatState) {

    }
}
