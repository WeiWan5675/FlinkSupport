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
package com.weiwan.support.launcher.envs;


import org.apache.flink.configuration.ConfigOption;
import org.apache.flink.configuration.CoreOptions;

/**
 * @Author: xiaozhennan
 * @Date: 2020/11/6 16:12
 * @Package: com.weiwan.support.launcher.ConfigOptions
 * @ClassName: ConfigOptions
 * @Description:
 **/
public class JVMOptions {

    public static final ConfigOption FLINK_HS_JVM_OPTIONS = CoreOptions.FLINK_HS_JVM_OPTIONS;
    public static final ConfigOption FLINK_JM_JVM_OPTIONS = CoreOptions.FLINK_JM_JVM_OPTIONS;
    public static final ConfigOption FLINK_TM_JVM_OPTIONS = CoreOptions.FLINK_TM_JVM_OPTIONS;
    public static final ConfigOption FLINK_JVM_OPTIONS = CoreOptions.FLINK_JVM_OPTIONS;
    public static final ConfigOption FLINK_LOG_DIR = CoreOptions.FLINK_LOG_DIR;
    public static final String LOG4J_CONFIG_FILE = "log4j.configurationFile";
    public static final String LOG_FILE = "log.file";
}
