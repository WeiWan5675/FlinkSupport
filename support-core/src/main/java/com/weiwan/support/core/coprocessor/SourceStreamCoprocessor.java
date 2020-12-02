package com.weiwan.support.core.coprocessor;

import com.weiwan.support.common.utils.ReflectUtil;
import com.weiwan.support.core.SupportContext;
import com.weiwan.support.core.SupportContextHolder;
import com.weiwan.support.core.annotation.SupportSource;
import com.weiwan.support.core.api.Reader;
import com.weiwan.support.core.api.SupportDataFlow;
import com.weiwan.support.core.config.ReaderConfig;
import com.weiwan.support.core.enums.SourceElement;
import org.apache.flink.api.dag.Transformation;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @Author: xiaozhennan
 * @Date: 2020/12/1 16:49
 * @Package: com.weiwan.support.core.coprocessor.ReaderSourceCoprocessor
 * @ClassName: ReaderSourceCoprocessor
 * @Description:
 **/
public class SourceStreamCoprocessor extends SupportCoprocessor {

    private StreamExecutionEnvironment environment;

    public SourceStreamCoprocessor(SupportContext context) {
        super(context);
    }

    @Override
    public <E, S1, S2> Object process(E env, SupportDataFlow<E, S1, S2> dataFlow, Object obj) throws Exception {
        Class<? extends SupportDataFlow> aClass = dataFlow.getClass();
        if (env instanceof StreamExecutionEnvironment) {
            environment = (StreamExecutionEnvironment) env;
        } else {
            throw new RuntimeException("env is not StreamExecutionEnvironment");
        }

        //处理字段上的source
        List<Field> fields = ReflectUtil.getFieldByAnno(aClass, SupportSource.class);
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                SupportSource anno = field.getAnnotation(SupportSource.class);
                DataStream stream = null;
                if (field.getType() == DataStream.class) {
                    stream = (DataStream) field.get(dataFlow);
                }

                for (String var : anno.vars()) {
                    String[] kv = var.split("=");
                    if (kv.length == 2) {
                        ReaderConfig readerConfig = getContext().getJobConfig().getReaderConfig();
                        readerConfig.setStringVal(kv[0], kv[1]);
                    }
                }
                //open之前  stream是null
                if (stream == null) {
                    stream = generateReaderStream(env, anno);
                }
                //设置并行度以及名称
                Transformation transformation = stream.getTransformation();
                transformation.setParallelism(anno.parallelism());
                transformation.setName(anno.name());
                field.set(dataFlow,stream);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                field.setAccessible(false);
            }
        }

        //处理open方法上的SupportSource注解
        Method open = aClass.getMethod("open", StreamExecutionEnvironment.class, SupportContext.class);
        SupportSource annotation = open.getAnnotation(SupportSource.class);
        if (annotation != null) {
            obj = generateReaderStream(env, annotation);
        }
        return nextProcess(env, dataFlow, obj);
    }

    private <E> DataStream generateReaderStream(E env, SupportSource anno) throws InstantiationException, IllegalAccessException {
        DataStream stream;
        Class<? extends Reader> type = anno.type();
        Reader reader = type.newInstance();
        reader.initEnv(env, getContext(), null);
        stream = reader.read((StreamExecutionEnvironment) environment, getContext());
        return stream;
    }

}
