package com.weiwan.support.etl.framework.api.processer;

import com.weiwan.support.core.SupportAppContext;
import com.weiwan.support.core.api.Processer;
import com.weiwan.support.core.config.ProcesserConfig;
import com.weiwan.support.core.config.JobConfig;
import com.weiwan.support.core.pojo.DataRecord;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * @Author: xiaozhennan
 * @Date: 2020/7/19 20:13
 * @Package: org.weiwan.argus.core
 * @ClassName: BaseChannel
 * @Description:
 **/
public abstract class BaseProcesser<IN extends DataRecord, OUT extends DataRecord> implements Processer<IN, OUT> {
    protected JobConfig jobConfig;
    protected StreamExecutionEnvironment env;
    protected String channelName;
    protected SupportAppContext supportAppContext;
    protected ProcesserConfig processerConfig;

    public BaseProcesser(StreamExecutionEnvironment env, SupportAppContext supportAppContext) {
        this.env = env;
        this.supportAppContext = supportAppContext;
        this.jobConfig = supportAppContext.getJobConfig();
        this.processerConfig = supportAppContext.getJobConfig().getProcesserConfig();
        this.channelName = processerConfig.getChannleName();
    }

    public abstract BaseProcessHandler<IN, OUT> getProcessHandler(SupportAppContext argusContext);

    @Override
    public DataStream<OUT> process(DataStream<IN> stream, SupportAppContext context) {
        BaseProcessHandler channelHandler = getProcessHandler(supportAppContext);
        DataStream<OUT> out = stream.map(channelHandler);
        return out;
    }
}
