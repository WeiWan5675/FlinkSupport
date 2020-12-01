package com.weiwan.support.core.coprocessor;

import com.weiwan.support.core.SupportContext;
import com.weiwan.support.core.annotation.Parallelism;
import com.weiwan.support.core.annotation.SupportSource;
import com.weiwan.support.core.api.Reader;
import com.weiwan.support.core.api.SupportDataFlow;
import com.weiwan.support.core.enums.FieldAnnoEnum;
import com.weiwan.support.core.enums.SourceElement;
import org.apache.flink.api.dag.Transformation;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.source.SourceFunction;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedHashMap;

import static com.weiwan.support.core.enums.FieldAnnoEnum.Parallelism;

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
            for (Annotation anno : annotations) {
                Class<? extends Annotation> annoType = anno.annotationType();
                if (FieldAnnoEnum.Parallelism.clazz.equals(annoType)) {
                    //流并行度
                    Transformation transformation = stream.getTransformation();
                    transformation.setParallelism(((com.weiwan.support.core.annotation.Parallelism) anno).num());
                }
                if (FieldAnnoEnum.PrintStream.clazz.equals(annoType)) {
                    //打印流
                    stream.print();
                }

            }
        } catch (Exception e) {

        } finally {
            field.setAccessible(false);
        }
    }
}
