package com.weiwan.tester.run;

import com.weiwan.support.core.StreamSupport;
import com.weiwan.support.core.SupportContext;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * @Author: xiaozhennan
 * @Date: 2020/12/2 17:21
 * @Package: com.weiwan.tester.run.TestFixDetail
 * @ClassName: TestFixDetail
 * @Description:
 **/
public class TestFixDetail extends StreamSupport<String,String> {

    @Override
    public DataStream<String> open(StreamExecutionEnvironment env, SupportContext context) {
        return null;
    }

    @Override
    public DataStream<String> process(DataStream<String> stream) {
        return null;
    }

    @Override
    public Object output(DataStream<String> stream) {
        return null;
    }
}
