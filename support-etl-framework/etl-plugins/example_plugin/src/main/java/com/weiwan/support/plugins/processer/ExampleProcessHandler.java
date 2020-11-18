package com.weiwan.support.plugins.processer;

import com.weiwan.support.core.SupportAppContext;
import com.weiwan.support.core.pojo.DataRecord;
import com.weiwan.support.etl.framework.api.processer.BaseProcessHandler;

/**
 * @Author: xiaozhennan
 * @Date: 2020/11/18 17:10
 * @Package: com.weiwan.support.plugins.processer.ExampleProcessHandler
 * @ClassName: ExampleProcessHandler
 * @Description:
 **/
public class ExampleProcessHandler extends BaseProcessHandler<DataRecord<String>, DataRecord<String>> {
    public ExampleProcessHandler(SupportAppContext context) {
        super(context);
    }

    @Override
    public DataRecord<String> process(DataRecord<String> value) {
        return value;
    }
}
