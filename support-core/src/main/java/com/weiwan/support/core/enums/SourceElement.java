package com.weiwan.support.core.enums;

import com.weiwan.support.core.source.*;

/**
 * @Author: xiaozhennan
 * @Date: 2020/11/24 17:09
 * @Package: com.weiwan.support.core.enums.SourceElement
 * @ClassName: SourceElement
 * @Description:
 **/
public enum SourceElement {

    MysqlSource(com.weiwan.support.core.source.MysqlSource.class),
    FileSource(com.weiwan.support.core.source.FileSource.class),
    HdfsSource(com.weiwan.support.core.source.HdfsSource.class),
    KafkaSource(com.weiwan.support.core.source.KafkaSource.class),
    MongoSource(com.weiwan.support.core.source.MongoSource.class),
    OplogSource(com.weiwan.support.core.source.OpLogSource.class),
    BinlogSource(com.weiwan.support.core.source.BinlogSource.class),
    HiveSource(com.weiwan.support.core.source.HiveSource.class);

    private Class<?> source;

    SourceElement(Class<?> source) {
        this.source = source;
    }
}
