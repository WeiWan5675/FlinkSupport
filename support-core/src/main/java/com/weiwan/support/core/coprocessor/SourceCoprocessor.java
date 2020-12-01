package com.weiwan.support.core.coprocessor;

import com.weiwan.support.common.utils.ReflectUtil;
import com.weiwan.support.core.SupportContext;
import com.weiwan.support.core.SupportContextHolder;
import com.weiwan.support.core.annotation.SupportSource;
import com.weiwan.support.core.api.Reader;
import com.weiwan.support.core.api.SupportDataFlow;
import com.weiwan.support.core.config.ReaderConfig;
import com.weiwan.support.core.enums.SourceElement;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
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
public class SourceCoprocessor extends SupportCoprocessor {

    private StreamExecutionEnvironment environment;

    public SourceCoprocessor(SupportContext context) {
        super(context);
    }

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
        List<Field> fields = ReflectUtil.getFieldByAnno(aClass, SupportSource.class);
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                SupportSource anno = field.getAnnotation(SupportSource.class);
                DataStream stream = null;
                if (field.getType() == DataStream.class) {
                    stream = (DataStream) field.get(dataFlow);
                }
                SourceElement type = anno.type();
                String[] vars = anno.vars();
                for (String var : vars) {
                    String[] kv = var.split("=");
                    if (kv.length == 2) {
                        ReaderConfig readerConfig = getContext().getJobConfig().getReaderConfig();
                        readerConfig.setStringVal(kv[0], kv[1]);
                    }
                }
                String sourceType = type.getSourceType();
                Class<?> readerClass = Class.forName(sourceType);
                Constructor<?> readerConstructor = readerClass.getConstructor(environment.getClass(), SupportContext.class);
                Reader reader = (Reader) readerConstructor.newInstance(environment, getContext());
                stream = reader.read((StreamExecutionEnvironment) environment, getContext());
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                field.setAccessible(false);
            }
        }

        return nextProcess(env, dataFlow, obj);
    }

}
