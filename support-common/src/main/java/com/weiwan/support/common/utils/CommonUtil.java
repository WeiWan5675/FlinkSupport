package com.weiwan.support.common.utils;

import ch.qos.logback.classic.Level;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Map;


public class CommonUtil {
    public static boolean isNullOrEmpty(Object obj) {
        if (obj == null)
            return true;

        if (obj instanceof CharSequence)
            return ((CharSequence) obj).length() == 0;

        if (obj instanceof Collection)
            return ((Collection) obj).isEmpty();

        if (obj instanceof Map)
            return ((Map) obj).isEmpty();

        if (obj instanceof Object[]) {
            Object[] object = (Object[]) obj;
            if (object.length == 0) {
                return true;
            }
            boolean empty = true;
            for (int i = 0; i < object.length; i++) {
                if (!isNullOrEmpty(object[i])) {
                    empty = false;
                    break;
                }
            }
            return empty;
        }
        return false;
    }


    /**
     * @param var
     * @param defaultVar
     * @param <P>
     * @return val
     */
    public static <P> P getNotNullOrDefault(P var, P defaultVar) {
        P val = null;
        if (var != null) {
            val = var;
        } else {
            val = defaultVar;
        }
        return val;
    }


    /**
     * 可以闯进来各种不同的对象,参数 都不能为空 为空返回true
     *
     * @param obj
     * @return 为空 true
     */
    public static boolean checkParameterIsNull(Object... obj) {
        for (Object o : obj) {
            if (isNullOrEmpty(o)) {
                return true;
            }
        }
        return false;
    }


    /**
     * -----------------------------------------------------------------------
     * getAppPath需要一个当前程序使用的Java类的class属性参数，它可以返回打包过的
     * Java可执行文件（jar，war）所处的系统目录名或非打包Java程序所处的目录
     *
     * @param
     * @return 返回值为该类所在的Java程序运行的目录
     * -------------------------------------------------------------------------
     */
    public static String getAppPath(Class cls) {
        //检查用户传入的参数是否为空
        if (cls == null)
            throw new java.lang.IllegalArgumentException("参数不能为空！");
        ClassLoader loader = cls.getClassLoader();
        //获得类的全名，包括包名
        String clsName = cls.getName() + ".class";
        //获得传入参数所在的包
        Package pack = cls.getPackage();
        String path = "";
        //如果不是匿名包，将包名转化为路径
        if (pack != null) {
            String packName = pack.getName();
            //此处简单判定是否是Java基础类库，防止用户传入JDK内置的类库
            if (packName.startsWith("java.") || packName.startsWith("javax."))
                throw new java.lang.IllegalArgumentException("不要传送系统类！");
            //在类的名称中，去掉包名的部分，获得类的文件名
            clsName = clsName.substring(packName.length() + 1);
            //判定包名是否是简单包名，如果是，则直接将包名转换为路径，
            if (packName.indexOf(".") < 0) path = packName + "/";
            else {//否则按照包名的组成部分，将包名转换为路径
                int start = 0, end = 0;
                end = packName.indexOf(".");
                while (end != -1) {
                    path = path + packName.substring(start, end) + "/";
                    start = end + 1;
                    end = packName.indexOf(".", start);
                }
                path = path + packName.substring(start) + "/";
            }
        }
        //调用ClassLoader的getResource方法，传入包含路径信息的类文件名
        java.net.URL url = loader.getResource(path + clsName);
        //从URL对象中获取路径信息
        String realPath = url.getPath();
        //去掉路径信息中的协议名"file:"
        int pos = realPath.indexOf("file:");
        if (pos > -1) realPath = realPath.substring(pos + 5);
        //去掉路径信息最后包含类文件信息的部分，得到类所在的路径
        pos = realPath.indexOf(path + clsName);
        realPath = realPath.substring(0, pos - 1);
        //如果类文件被打包到JAR等文件中时，去掉对应的JAR等打包文件名
        if (realPath.endsWith("!"))
            realPath = realPath.substring(0, realPath.lastIndexOf("/"));
      /*------------------------------------------------------------
       ClassLoader的getResource方法使用了utf-8对路径信息进行了编码，当路径
        中存在中文和空格时，他会对这些字符进行转换，这样，得到的往往不是我们想要
        的真实路径，在此，调用了URLDecoder的decode方法进行解码，以便得到原始的
        中文及空格路径
      -------------------------------------------------------------*/
        try {
            realPath = java.net.URLDecoder.decode(realPath, "utf-8");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return realPath;
    }//getAppPath定义结束
    //-----------------------------------------------------------------


    public static String useCommandLogLevel(String logLevel) {
        ch.qos.logback.classic.LoggerContext loggerContext = (ch.qos.logback.classic.LoggerContext) LoggerFactory.getILoggerFactory();
        //设置全局日志级别
        ch.qos.logback.classic.Logger _Log = loggerContext.getLogger("root");
        Level level = _Log.getLevel();
        if (StringUtils.isNotEmpty(logLevel) && !level.levelStr.equalsIgnoreCase(logLevel)) {
            Logger logger = (Logger) _Log;
            _Log.setLevel(Level.valueOf(logLevel));
            logger.debug("useing user custom loglevel override log profile setting");
            return logLevel;
        } else if (StringUtils.isEmpty(logLevel)) {
            return level.levelStr;
        }
        return logLevel;
    }
}
