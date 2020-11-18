package com.weiwan.support.plugins.reader;

import com.weiwan.support.core.SupportAppContext;
import com.weiwan.support.etl.framework.api.reader.BaseInputFormat;
import com.weiwan.support.etl.framework.streaming.JobFormatState;
import org.apache.flink.core.io.InputSplit;

/**
 * @Author: xiaozhennan
 * @Date: 2020/11/18 17:08
 * @Package: com.weiwan.support.plugins.reader.ExampleInputFormat
 * @ClassName: ExampleInputFormat
 * @Description:
 **/
public class ExampleInputFormat extends BaseInputFormat {
    public ExampleInputFormat(SupportAppContext context) {
    }

    /**
     * 打开InputFormat,根据split读取数据
     *
     * @param split 当前处理的分区InputSplit
     */
    @Override
    public void openInput(InputSplit split) {

    }

    /**
     * 根据minNumSplits决定如何划分分区
     *
     * @param minNumSplits 最小分区数
     * @return 分区数组
     */
    @Override
    public InputSplit[] getInputSpliter(int minNumSplits) {
        return new InputSplit[0];
    }

    /**
     * 返回一条记录
     * 当数据处理结束后,需要手动调用{@link BaseRichInputFormat#isComplete} }
     * 如果不想使用isComplete 需要重写{@link BaseRichInputFormat#reachedEnd()}
     *
     * @param reuse
     * @return 数据
     */
    @Override
    public Object nextRecordInternal(Object reuse) {
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
