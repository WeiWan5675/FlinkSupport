package com.weiwan.support.core.api;

import com.weiwan.support.core.SupportAppContext;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSink;

/**
 * @Author: xiaozhennan
 * @Date: 2020/9/28 15:57
 * @Package: com.weiwan.support.core.api.Writer
 * @ClassName: Writer
 * @Description:
 **/
@FunctionalInterface
public interface Writer<IN> {

    public DataStreamSink write(DataStream<IN> inputStream, SupportAppContext context);
}
