package com.weiwan.support.core;

import com.weiwan.support.core.api.*;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * @Author: xiaozhennan
 * @Date: 2020/9/28 16:03
 * @Package: com.weiwan.support.core.FlinkSupportAssembly
 * @ClassName: FlinkSupportAssembly
 * @Description:
 **/
public class StreamAppSupport implements FlinkSupport<StreamExecutionEnvironment> {

    private Processer process;
    private Reader reader;
    private Writer writer;
    private StreamExecutionEnvironment env;
    private SupportAppContext context;

    public StreamAppSupport(StreamExecutionEnvironment env, SupportAppContext context) {
        this.env = env;
        this.context = context;
    }


    public void reader(Reader reader) {
        this.reader = reader;
    }

    public void process(Processer process) {
        this.process = process;
    }

    public void writer(Writer writer) {
        this.writer = writer;
    }


    @Override
    public TaskResult submitFlinkTask(StreamExecutionEnvironment env) {
        return null;
    }
}
