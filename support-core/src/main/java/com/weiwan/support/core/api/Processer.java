package com.weiwan.support.core.api;

import com.weiwan.support.core.SupportAppContext;
import org.apache.flink.streaming.api.datastream.DataStream;

/**
 * @Author: xiaozhennan
 * @Date: 2020/9/28 15:57
 * @Package: com.weiwan.support.core.api.Processer
 * @ClassName: Processer
 * @Description:
 **/
public interface Processer<IN, OUT> {

    public DataStream<OUT> process(DataStream<IN> inputStream, SupportAppContext context);

}
