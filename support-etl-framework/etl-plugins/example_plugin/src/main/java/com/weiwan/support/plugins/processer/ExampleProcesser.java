package com.weiwan.support.plugins.processer;

import com.weiwan.support.core.SupportAppContext;
import com.weiwan.support.core.pojo.DataRecord;
import com.weiwan.support.etl.framework.api.processer.BaseProcessHandler;
import com.weiwan.support.etl.framework.api.processer.BaseProcesser;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * @Author: xiaozhennan
 * @Date: 2020/11/18 17:09
 * @Package: com.weiwan.support.plugins.processer.ExampleProcesser
 * @ClassName: ExampleProcesser
 * @Description:
 **/
public class ExampleProcesser extends BaseProcesser<DataRecord<String>, DataRecord<String>> {
    public ExampleProcesser(StreamExecutionEnvironment env, SupportAppContext supportAppContext) {
        super(env, supportAppContext);
    }

    @Override
    public BaseProcessHandler<DataRecord<String>, DataRecord<String>> getProcessHandler(SupportAppContext context) {

        return new ExampleProcessHandler(context);
    }
}
