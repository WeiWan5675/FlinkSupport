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
package com.weiwan.support.utils.flink.loging;

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
