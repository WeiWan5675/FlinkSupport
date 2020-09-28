package com.weiwan.support.core.api;

import com.weiwan.support.core.StreamAppSupport;
import com.weiwan.support.core.SupportAppContext;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSink;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * @Author: xiaozhennan
 * @Date: 2020/9/28 16:15
 * @Package: com.weiwan.support.core.api.MyFlinkApp
 * @ClassName: MyFlinkApp
 * @Description:
 **/
public class MyFlinkApp extends StreamAppSupport {


    public MyFlinkApp(StreamExecutionEnvironment env, SupportAppContext context) {
        super(env, context);
    }

}
