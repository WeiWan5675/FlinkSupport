package com.weiwan.support.core.coprocessor;

import com.weiwan.support.core.SupportAppContext;
import com.weiwan.support.core.api.Processer;
import com.weiwan.support.core.api.Reader;
import com.weiwan.support.core.api.SupportDataFlow;
import com.weiwan.support.core.api.Writer;
import com.weiwan.support.core.config.ChannelConfig;
import com.weiwan.support.core.config.JobConfig;
import com.weiwan.support.core.config.ReaderConfig;
import com.weiwan.support.core.config.WriterConfig;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSink;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import java.lang.reflect.Constructor;

/**
 * @Author: xiaozhennan
 * @Date: 2020/11/11 15:44
 * @Package: com.weiwan.support.core.coprocessor.EtlCoprocessor
 * @ClassName: EtlCoprocessor
 * @Description:
 **/
public class EtlCoprocessor extends SupportCoprocessor {

    public EtlCoprocessor(SupportAppContext context) {
        super(context);
    }

    @Override
    public <E, S1, S2> Object process(E env, SupportDataFlow<E, S1, S2> dataFlow, Object obj) throws Exception {

        JobConfig jobConfig = context.getJobConfig();
        ReaderConfig readerConfig = jobConfig.getReaderConfig();
        ChannelConfig channelConfig = jobConfig.getChannelConfig();
        WriterConfig writerConfig = jobConfig.getWriterConfig();

        String readerClassName = readerConfig.getStringVal("reader.class");
        Class<?> readerClass = Class.forName(readerClassName);
        Constructor<?> readerConstructor = readerClass.getConstructor(env.getClass(), SupportAppContext.class);
        Reader<S1> reader = (Reader<S1>) readerConstructor.newInstance(env, context);
        DataStream<S1> s1 = reader.read((StreamExecutionEnvironment) env, context);


        String processerClassName = channelConfig.getStringVal("processer.class");
        Class<?> processerClass = Class.forName(processerClassName);
        Constructor<?> processerConstructor = processerClass.getConstructor(env.getClass(), SupportAppContext.class);
        Processer<S1, S2> processer = (Processer<S1, S2>) processerConstructor.newInstance(env, context);
        DataStream<S2> s2 = processer.process(s1, context);


        String writerClassName = writerConfig.getStringVal("writer.class");
        Class<?> writerClass = Class.forName(writerClassName);
        Constructor<?> writerConstructor = writerClass.getConstructor(env.getClass(), SupportAppContext.class);
        Writer writer = (Writer) writerConstructor.newInstance(env, context);
        DataStreamSink sink = writer.write(s2, context);
        return nextProcess(env, dataFlow, sink);
    }
}
