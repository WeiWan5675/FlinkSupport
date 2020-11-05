//package com.weiwan.support.core;
//
//import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
//import org.weiwan.argus.core.classloader.ClassLoaderManager;
//import org.weiwan.argus.core.pub.api.ArgusChannel;
//import org.weiwan.argus.core.pub.api.ArgusReader;
//import org.weiwan.argus.core.pub.api.ArgusWriter;
//import org.weiwan.argus.core.pub.config.ArgusContext;
//
//import java.lang.reflect.Constructor;
//import java.net.URL;
//import java.util.HashSet;
//import java.util.List;
//
///**
// * @Author: xiaozhennan
// * @Date: 2020/7/20 14:18
// * @Package: org.weiwan.argus.core
// * @ClassName: ArgusPluginManager
// * @Description:
// **/
//public class ArgusPluginManager {
//
//    private StreamExecutionEnvironment env;
//    private SupportAppContext context;
//
//
//    public ArgusPluginManager(StreamExecutionEnvironment env, SupportAppContext context) {
//        this.env = env;
//        this.context = context;
//    }
//
//    public <T> T loadPlugin(List<URL> urls, String className, Class<T> tClass) throws Exception {
//        return ClassLoaderManager.newInstance(new HashSet<>(urls), cl -> {
//            Class<?> aClass = cl.loadClass(className);
//            Constructor<?> constructor = aClass.getConstructor(StreamExecutionEnvironment.class, ArgusContext.class);
//            return (T) constructor.newInstance(env, argusContext);
//        });
//    }
//
//
//    public ArgusReader loadReaderPlugin(List<URL> urls, String className) throws Exception {
//        return loadPlugin(urls, className, ArgusReader.class);
//    }
//
//    public ArgusWriter loadWriterPlugin(List<URL> urls, String className) throws Exception {
//        return loadPlugin(urls, className, ArgusWriter.class);
//    }
//
//    public ArgusChannel loadChannelPlugin(List<URL> urls, String className) throws Exception {
//        return loadPlugin(urls, className, ArgusChannel.class);
//    }
//
//
//}
