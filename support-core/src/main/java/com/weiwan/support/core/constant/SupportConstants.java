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
package com.weiwan.support.core.constant;

import com.weiwan.support.common.constant.Constans;


/**
 * @Author: xiaozhennan
 * @Date: 2020/10/9 15:39
 * @Package: com.weiwan.support.core.constant.SupportConstants
 * @ClassName: SupportConstants
 * @Description:
 **/
public class SupportConstants {

    //APPLICATION
    public static final String SUPPORT_ENTER_CLASSNAME = "com.weiwan.support.runtime.SupportAppEnter";
    public static final String ETL_BASE_APP_CLASSNAME = "com.weiwan.support.etl.framework.app.StreamETLBaseApp";
    public static final String SQL_BASE_APP_CLASSNAME = "com.weiwan.support.sql.framework.app.StreamSqlBaseApp";

    //HOME KEY
    public static final String KEY_SUPPORT_HOME = "FLINK_SUPPORT_HOME";
    public static final String KEY_FLINK_HOME = "FLINK_HOME";
    public static final String KEY_HADOOP_HOME = "HADOOP_HOME";
    public static final String FLINK_VERSION = "FLINK_VERSION";
    public static final String SCALA_VERSION = "SCALA_VERSION";

    //PLACEHOLDER
    public static final String FLINK_VERSION_PLACEHOLDER = "${" + FLINK_VERSION + "}";
    public static final String SCALA_VERSION_PLACEHOLDER = "${" + SCALA_VERSION + "}";
    //SUPPORT HDFS SPACE
    public static final String SUPPORT_HDFS_WORKSPACE = "hdfs://nameservice1/flink_support";
    public static final String SUPPORT_HDFS_LIB_DIR = SUPPORT_HDFS_WORKSPACE + Constans.SIGN_SLASH + "lib";
    public static final String SUPPORT_HDFS_PLUGIN_DIR = SUPPORT_HDFS_WORKSPACE + Constans.SIGN_SLASH + "plugin";
    public static final String SUPPORT_HDFS_RESOURCES_DIR = SUPPORT_HDFS_WORKSPACE + Constans.SIGN_SLASH + "resources";
    public static final String SUPPORT_HDFS_CONF_DIR = SUPPORT_HDFS_WORKSPACE + Constans.SIGN_SLASH + "conf";
    public static final String FLINKS = "flinks";
    public static final String FLINK_HDFS_HOME = SUPPORT_HDFS_WORKSPACE +
            Constans.SIGN_SLASH + FLINKS +
            Constans.SIGN_SLASH + FLINK_VERSION_PLACEHOLDER;


    //SUPPORT LOCAL
    public static final String SUPPORT_LOCAL_LIB_DIR = "lib";
    public static final String SUPPORT_LOCAL_CONF_DIR = "conf";
    public static final String SUPPORT_LOCAL_PLUGINS_DIR = "plugin";
    public static final String SUPPORT_LOCAL_EXTLIB_DIR = "extlib";
    public static final String SUPPORT_CORE_CONF_FILE = "support-core.yaml";
    public static final String SUPPORT_ETL_CONF_FILE = "support-etl.yaml";
    public static final String SUPPORT_SQL_CONF_FILE = "support-sql.yaml";


    //FLINK
    public static final String FLINK_DIST_JAR = "flink-dist_" + SCALA_VERSION_PLACEHOLDER + "-" + FLINK_VERSION_PLACEHOLDER + ".jar";
    public static final String FLINK_CONF_DIR = "conf";
    public static final String FLINK_LIB_DIR = "lib";
    public static final String FLINK_PLUGINS_DIR = "plugins";
    public static final String FLINK_CONF_FILE = "flink-conf.yaml";
    public static final String FLINK_LOG_CONF_FILE = "log4j.properties";

    //HADOOP
    public static final String HADOOP_USER_NAME = "HADOOP_USER_NAME";
    public static final String HADOOP_CONF_DIR = "etc/hadoop";
    public static final String HADOOP_CONF_CORE_SITE = "core-site.xml";
    public static final String HADOOP_CONF_HDFS_SITE = "hdfs-site.xml";

    //KEYS
    public static final String KEY_LOCAL_FLINK_CONF = "local.flink.conf.flink-conf";
    public static final String KEY_LOCAL_HADOOP_CORE_SITE_CONF = "local.hadoop.conf.core-site";
    public static final String KEY_LOCAL_HADOOP_HDFS_SITE_CONF = "local.hadoop.conf.hdfs-site";
    public static final String KEY_LOCAL_YARN_SITE_CONF = "local.yarn.conf.yarn-site";

    public static final String KEY_FLINK_CONFIGURATION = "flinkConfiguration";
    public static final String KEY_HADOOP_CONFIGURATION = "hadoopConfiguration";


    //JOB
    public static final String JOB_NAME_PLACEHOLDER = "${jobName}";
    public static final String JOB_RESOURCES_MD5_KEY_PLACEHOLDER = "${jobResourcesMD5}";
    public static final String JOB_RESOURCES_DIR = SUPPORT_HDFS_WORKSPACE + Constans.SIGN_SLASH + "resources/${jobResourcesMD5}";
    public static final String KEY_YARN_CONFIGURATION = "yarnConfiguration";
    public static final String SUPPORT_STREAM_TYPE = "Flink Stream Support";

    public static final String SUPPORT_BATCH_TYPE = "Flink Batch Support";
    public static final String SUPPORT_RUMTIME_JAR = SUPPORT_HDFS_LIB_DIR + Constans.SIGN_SLASH + "support-runtime-1.0.jar";


    //DEFAULT LOG
    public static final String DEFAULT_SUPPORT_TASK_LOGDIR = "/tmp/flink_support/logs";
    public static final String JM_LOG_FILE_NAME = "jobmanager.log";
    public static final String TM_LOG_FILE_NAME = "taskmanager.log";
}
