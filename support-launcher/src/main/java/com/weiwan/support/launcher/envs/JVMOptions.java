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
