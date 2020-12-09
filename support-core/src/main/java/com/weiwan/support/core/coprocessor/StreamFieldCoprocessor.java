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

import com.weiwan.support.api.SupportDataFlow;
import com.weiwan.support.api.config.SupportContext;
import com.weiwan.support.core.annotation.PrintStream;
import org.apache.flink.api.dag.Transformation;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.LinkedHashMap;

/**
 * @Author: xiaozhennan
 * @Date: 2020/12/1 14:56
 * @Package: com.weiwan.support.core.coprocessor.StreamFieldCoprocessor
 * @ClassName: FieldAnnoCoprocessor
 * @Description:
 **/
public class StreamFieldCoprocessor extends SupportCoprocessor {
    public StreamFieldCoprocessor(SupportContext context) {
        super(context);
    }

    private StreamExecutionEnvironment environment;

    @Override
    public <E, S1, S2> Object process(E env, SupportDataFlow<E, S1, S2> dataFlow, Object obj) throws Exception {
        Class<? extends SupportDataFlow> aClass = dataFlow.getClass();
        Field[] declaredFields =
                aClass.getDeclaredFields();
        if (env instanceof StreamExecutionEnvironment) {
            environment = (StreamExecutionEnvironment) env;
        } else {
            throw new RuntimeException("env is not StreamExecutionEnvironment");
        }
        LinkedHashMap<String, Field> fields = new LinkedHashMap<>();
        for (int i = 0; i < declaredFields.length; i++) {
            if (declaredFields[i] != null && declaredFields[i].getType() == DataStream.class) {
                fields.put(declaredFields[i].getName(), declaredFields[i]);
            }
        }
        for (String key : fields.keySet()) {
            Field field = fields.get(key);
            fieldAnnotationsResolve(field, dataFlow);
        }
        return nextProcess(env, dataFlow, obj);
    }

    private void fieldAnnotationsResolve(Field field, SupportDataFlow dataFlow) {
        try {
            field.setAccessible(true);
            Annotation[] annotations = field.getAnnotations();
            DataStream stream = (DataStream) field.get(dataFlow);
            if (stream != null) {
                for (Annotation anno : annotations) {
                    Class<? extends Annotation> annoType = anno.annotationType();
                    if (com.weiwan.support.core.annotation.Parallelism.class.equals(annoType)) {
                        //流并行度
                        Transformation transformation = stream.getTransformation();
                        transformation.setParallelism(((com.weiwan.support.core.annotation.Parallelism) anno).num());
                    }
                    if (PrintStream.class.equals(annoType)) {
                        //打印流
                        stream.print();
                    }

                }
            }
        } catch (Exception e) {

        } finally {
            field.setAccessible(false);
        }
    }
}
