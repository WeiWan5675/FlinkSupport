package com.weiwan.support.core.flink.pub;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author: xiaozhennan
 * @Date: 2020/4/29 9:47
 * @Package: com.hopson.dc.realtime.java.init
 * @ClassName: FlinkLogger
 * @Description:
 **/
public class FlinkLogger {

    private Logger logger;
    private Class<?> loggerClass;

    public FlinkLogger() {
    }

    public <T> FlinkLogger(Class<T> tClass) {
        this.loggerClass = tClass;
        logger = LoggerFactory.getLogger(tClass);
    }

    public FlinkLogger(String className) {
        logger = LoggerFactory.getLogger(className);
    }

    public void info(String msg, Object... vars) {
        logger.info(msg, vars);
    }

    public void info_json(String msg, Object obj) {
        logger.info(msg, JSON.toJSONString(obj));
    }

    public void warn(String msg, Object... vars) {
        logger.warn(msg, vars);
    }

    public void error(String msg, Object... vars) {
        logger.error(msg, vars);
    }

    public void error(String msg, Throwable e) {
        logger.error(msg, e);
    }

    public void debug(String msg, Object... vars) {
        logger.debug(msg, vars);
    }

    public void trace(String msg, Object... vars) {
        logger.trace(msg, vars);
    }

}
