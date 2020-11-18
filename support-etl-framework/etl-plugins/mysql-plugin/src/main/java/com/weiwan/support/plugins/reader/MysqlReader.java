package com.weiwan.support.plugins.reader;

import com.weiwan.support.core.SupportAppContext;
import com.weiwan.support.core.pojo.DataRecord;
import com.weiwan.support.core.pojo.DataRow;
import com.weiwan.support.etl.framework.api.reader.BaseInputFormat;
import com.weiwan.support.etl.framework.api.reader.BaseReader;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * @Author: xiaozhennan
 * @Date: 2020/7/14 17:49
 * @Package: org.weiwan.argus.reader.mysql
 * @ClassName: MysqlReader
 * @Description:
 **/
public class MysqlReader extends BaseReader<DataRecord<DataRow>> {


    public MysqlReader(StreamExecutionEnvironment env, SupportAppContext argusContext) {
        super(env, argusContext);
    }


    @Override
    public void readRequire(SupportAppContext context) {

    }

    @Override
    public BaseInputFormat getInputFormat(SupportAppContext context) {
        MysqlInputFormat mysqlInputFormat = new MysqlInputFormat(context);
        return mysqlInputFormat;
    }


}
