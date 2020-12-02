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

import com.weiwan.support.common.utils.ReflectUtil;
import com.weiwan.support.core.SupportContext;
import com.weiwan.support.core.annotation.SupportSink;
import com.weiwan.support.core.api.SupportDataFlow;
import com.weiwan.support.core.api.Writer;
import com.weiwan.support.core.config.WriterConfig;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSink;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import java.lang.reflect.Field;
import java.util.List;

/**
 * @Author: xiaozhennan
 * @Date: 2020/12/1 17:09
 * @Package: com.weiwan.support.core.coprocessor.SinkStreamCoprocessor
 * @ClassName: SinkStreamCoprocessor
 * @Description: {@code OutputStreamCoprocessor} 之后调用
 * 解析output上的sink注解以及成员变量上的{@code SupportSink}
 **/
public class SinkStreamCoprocessor extends SupportCoprocessor {
    private StreamExecutionEnvironment environment;

    public SinkStreamCoprocessor(SupportContext context) {
        super(context);
    }

    @Override
    public <E, S1, S2> Object process(E env, SupportDataFlow<E, S1, S2> dataFlow, Object obj) throws Exception {
        //在lastCoprocessor之前处理
        Class<? extends SupportDataFlow> aClass = dataFlow.getClass();
        if (env instanceof StreamExecutionEnvironment) {
            environment = (StreamExecutionEnvironment) env;
        } else {
            throw new RuntimeException("env is not StreamExecutionEnvironment");
        }
        List<Field> fields = ReflectUtil.getFieldByAnno(aClass, SupportSink.class);

        //设置在字段上,直接把字段流输出到writer
        //设置在方法上,直接把方法入参输出到writer
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                SupportSink anno = field.getAnnotation(SupportSink.class);
                DataStream stream = null;
                if (field.getType() == DataStream.class) {
                    stream = (DataStream) field.get(dataFlow);
                }
                for (String var : anno.vars()) {
                    String[] kv = var.split("=");
                    if (kv.length == 2) {
                        WriterConfig writerConfig = getContext().getJobConfig().getWriterConfig();
                        writerConfig.setStringVal(kv[0], kv[1]);
                    }
                }

                //output后,stream必须不能为空才可以写出
                if (stream != null) {
                    writeStreamByAnnotation(stream, env, anno);
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw e;
            } finally {
                field.setAccessible(false);
            }
        }


        //SupportSink加在方法上 TODO 此处先不支持,后边改用动态代理重构整个处理器链
//        Method output = aClass.getDeclaredMethod("output", DataStream.class);
//        output.setAccessible(true);
//        if(output.getAnnotation(SupportSink.class) != null && obj instanceof DataStream){
//            //
//
//            return nextProcess(env,dataFlow, )
//        }
        return nextProcess(env,dataFlow,obj);
    }

    private <E> DataStreamSink writeStreamByAnnotation(DataStream stream, E env, SupportSink anno) throws IllegalAccessException, InstantiationException {
        Class<? extends Writer> type = anno.type();
        Writer writer = type.newInstance();
        writer.initEnv(env,getContext(),null);
        DataStreamSink dataStreamSink = writer.write(stream);
        dataStreamSink.name(anno.name());
        dataStreamSink.setParallelism(anno.parallelism());
        return dataStreamSink;
    }


}
