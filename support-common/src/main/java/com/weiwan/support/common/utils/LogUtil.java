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
package com.weiwan.support.common.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;

import java.io.File;

/**
 * @Author: xiaozhennan
 * @Date: 2020/10/12 11:36
 * @Package: com.weiwan.support.common.utils.LogUtils
 * @ClassName: LogUtils
 * @Description:
 **/
public class LogUtil {


    public static void reloadLogConfig(String configFilePath) {
        File file = new File(configFilePath);
        try {
            LoggerContext context = (LoggerContext) LogManager.getContext(false);

            context.setConfigLocation(file.toURI());
            //重新初始化Log4j2的配置上下文
            context.reconfigure();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String useCommandLogLevel(String logLevel) {
        LoggerContext loggerContext = (LoggerContext) LogManager.getContext(true);
        org.apache.logging.log4j.core.Logger _Log = loggerContext.getLogger("root");
        org.apache.logging.log4j.Level level = _Log.getLevel();
        if (StringUtils.isNotEmpty(logLevel) && !level.name().equalsIgnoreCase(logLevel)) {
            Logger logger = (Logger) _Log;
            _Log.setLevel(Level.valueOf(logLevel));
            logger.debug("useing user custom loglevel override log profile setting");
            return logLevel;
        } else if (StringUtils.isEmpty(logLevel)) {
            return level.name();
        }
        return logLevel;
    }

}
