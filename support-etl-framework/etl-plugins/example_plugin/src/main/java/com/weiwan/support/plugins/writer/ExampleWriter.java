package com.weiwan.support.plugins.writer;

import com.weiwan.support.core.SupportAppContext;
import com.weiwan.support.core.pojo.DataRecord;
import com.weiwan.support.etl.framework.api.writer.BaseOutputFormat;
import com.weiwan.support.etl.framework.api.writer.BaseWriter;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * @Author: xiaozhennan
 * @Date: 2020/11/18 17:10
 * @Package: com.weiwan.support.plugins.writer.ExampleWriter
 * @ClassName: ExampleWriter
 * @Description:
 **/
public class ExampleWriter extends BaseWriter<DataRecord<String>> {
    public ExampleWriter(StreamExecutionEnvironment env, SupportAppContext context) {
        super(env, context);
    }

    @Override
    public BaseOutputFormat<DataRecord<String>> getOutputFormat(SupportAppContext context) {
        return new ExampleOutputFormat(context);
    }

    /**
     * 为什么要在这里有这个方法呢,output是并行得,但是有些前置条件要再并行任务执行前处理,所以提供这个方法
     *
     * @param context
     */
    @Override
    public void writeRequire(SupportAppContext context) {

    }
}