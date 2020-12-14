/*
 *      Copyright [2020] [xiaozhennan1995@gmail.com]
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 *      http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.weiwan.support.core.coprocessor;
import com.weiwan.support.api.config.*;
import com.weiwan.support.api.etl.Processer;
import com.weiwan.support.api.etl.Reader;
import com.weiwan.support.api.etl.Writer;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSink;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import com.weiwan.support.api.SupportDataFlow;

/**
 * @Author: xiaozhennan
 * @Date: 2020/11/11 15:44
 * @Package: com.weiwan.support.core.coprocessor.EtlCoprocessor
 * @ClassName: EtlCoprocessor
 * @Description:
 **/
public class EtlCoprocessor extends SupportCoprocessor {

    public EtlCoprocessor(SupportContext context) {
        super(context);
    }

    @Override
    public <E, S1, S2> Object process(E env, SupportDataFlow<E, S1, S2> dataFlow, Object obj) throws Exception {
        SupportContext context = getContext();
        JobConfig jobConfig = context.getJobConfig();
        ReaderConfig readerConfig = jobConfig.getReaderConfig();
        ProcesserConfig processerConfig = jobConfig.getProcesserConfig();
        WriterConfig writerConfig = jobConfig.getWriterConfig();

        String readerClassName = readerConfig.getStringVal("etl.reader.class");
        Class<?> readerClass = Class.forName(readerClassName);
        Reader<S1> reader = (Reader<S1>) readerClass.newInstance();
        reader.initEnv((StreamExecutionEnvironment) env, context, null);
        DataStream<S1> s1 = reader.read((StreamExecutionEnvironment) env, context);


        String processerClassName = processerConfig.getStringVal("etl.processer.class");
        Class<?> processerClass = Class.forName(processerClassName);
        Processer<S1, S2> processer = (Processer<S1, S2>) processerClass.newInstance();
        processer.initEnv((StreamExecutionEnvironment) env, context, null);
        DataStream<S2> s2 = processer.process(s1);


        String writerClassName = writerConfig.getStringVal("etl.writer.class");
        Class<?> writerClass = Class.forName(writerClassName);
        Writer<S2> writer = (Writer) writerClass.newInstance();
        writer.initEnv((StreamExecutionEnvironment) env, context, null);
        DataStreamSink sink = writer.write(s2);
        return nextProcess(env, dataFlow, sink);
    }
}
