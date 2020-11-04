package com.weiwan.test.easylife;

import com.weiwan.support.core.SupportAppContext;
import com.weiwan.support.core.annotation.Support;
import com.weiwan.support.core.api.FlinkSupport;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Set;

/**
 * @Author: xiaozhennan
 * @Date: 2020/11/4 17:23
 * @Package: com.weiwan.test.easylife.Test
 * @ClassName: Test
 * @Description:
 **/
public class Test {

    public static void main(String[] args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {

//or using ConfigurationBuilder
        Reflections reflections = new Reflections(new ConfigurationBuilder()
                .setUrls(ClasspathHelper.forPackage("com.weiwan"))
                .setScanners(new SubTypesScanner(),
                        new TypeAnnotationsScanner()));

        Set<Class<?>> typesAnnotatedWith = reflections.getTypesAnnotatedWith(Support.class);
        FlinkSupport o = null;
        for (Class<?> allType : typesAnnotatedWith) {
            Constructor<?> constructor = allType.getConstructor(StreamExecutionEnvironment.class, SupportAppContext.class);
            o = (FlinkSupport) constructor.newInstance(null, null);
        }
    }
}
