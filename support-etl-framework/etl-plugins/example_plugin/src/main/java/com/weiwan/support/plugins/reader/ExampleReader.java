package com.weiwan.support.plugins.reader;

import com.weiwan.support.core.SupportAppContext;
import com.weiwan.support.core.pojo.DataRecord;
import com.weiwan.support.etl.framework.api.reader.BaseInputFormat;
import com.weiwan.support.etl.framework.api.reader.BaseReader;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * @Author: xiaozhennan
 * @Date: 2020/11/18 17:07
 * @Package: com.weiwan.support.plugins.reader.ExampleReader
 * @ClassName: ExampleReader
 * @Description:
 **/
public class ExampleReader extends BaseReader<DataRecord<String>> {

    public ExampleReader(StreamExecutionEnvironment env, SupportAppContext context) {
        super(env, context);
    }

    @Override
    public BaseInputFormat getInputFormat(SupportAppContext context) {
        return new ExampleInputFormat(context);
    }

    @Override
    public void readRequire(SupportAppContext context) {

    }
}
