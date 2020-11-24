package com.weiwan.support.core.enums;

import com.weiwan.support.core.sink.OpLogSink;

/**
 * @Author: xiaozhennan
 * @Date: 2020/11/24 17:18
 * @Package: com.weiwan.support.core.enums.SinkElement
 * @ClassName: SinkElement
 * @Description:
 **/
public enum SinkElement {

    MysqlSink(com.weiwan.support.core.sink.MysqlSink.class),
    FileSink(com.weiwan.support.core.sink.FileSink.class),
    HdfsSink(com.weiwan.support.core.sink.HdfsSink.class),
    KafkaSink(com.weiwan.support.core.sink.KafkaSink.class),
    MongoSink(com.weiwan.support.core.sink.MongoSink.class),
    OplogSink(com.weiwan.support.core.sink.OpLogSink.class),
    BinlogSink(com.weiwan.support.core.sink.BinlogSink.class),
    HiveSink(com.weiwan.support.core.sink.HiveSink.class);

    private final Class<?> sinkClass;

    SinkElement(Class<?> sinkClass) {
        this.sinkClass = sinkClass;
    }
}
