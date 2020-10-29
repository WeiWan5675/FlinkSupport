package com.weiwan.support.core.api;

import com.weiwan.support.core.SupportAppContext;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSink;

/**
 * @Author: xiaozhennan
 * @Date: 2020/9/28 15:56
 * @Package: com.weiwan.support.core.api.FlinkSupport
 * @ClassName: FlinkSupport
 * @Description:
 **/
public interface FlinkSupport<T> {

    default void addReader(Reader reader) {

    }

    default void addProcess(Processer porcesser) {
    }

    default void addWriter(Writer writer) {

    }

    public T getEnv();

    public SupportAppContext getContext();

    TaskResult submitFlinkTask(T env);

}
