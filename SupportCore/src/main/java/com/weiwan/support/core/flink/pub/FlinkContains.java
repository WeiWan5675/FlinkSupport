package com.weiwan.support.core.flink.pub;

import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * @Author: xiaozhennan
 * @Date: 2020/4/29 1:42
 * @Package: com.hopson.dc.realtime.java.init
 * @ClassName: FlinkContains
 * @Description:
 **/
public class FlinkContains {
    public static final Class<StreamExecutionEnvironment> JAVA_STREAM_ENV = StreamExecutionEnvironment.class;
    public static final Class<ExecutionEnvironment> JAVA_BATCH_ENV = ExecutionEnvironment.class;


    public static final String FLINK_CONF_FILE_NAME = "flink.conf";
    public static final String FLINK_PROP_CONF_SUFFIX = "properties";
    public static final String FLINK_YAML_CONF_SUFFIX = "yaml";
    public static final String FLINK_CONF_FILE_DEFAULT = "flink-conf.yaml";
    public static final String FLINK_TASK_NAME = "flink.task.name";
    public static final String FLINK_TASK_NAME_DEFAULT_PREFIX = "FlinkApplication-";


    /**
     * Flink配置参数Key -> Common
     */
    public static final String FLINK_TASK_COMMON_PARALLELISM_KEY = "flink.task.common.parallelism";
    public static final String FLINK_TASK_COMMON_RESTART_MODE_KEY = "flink.task.common.restartMode";
    public static final String FLINK_TASK_COMMON_RESTART_NUM_KEY = "flink.task.common.restartNum";
    public static final String FLINK_TASK_COMMON_RESTART_INTERVAL_KEY = "flink.task.common.restartInterval";
    public static final String FLINK_TASK_COMMON_RESTART_FAIL_MAX_KEY = "flink.task.common.restartFailMaxNum";

    /**
     * Flink配置参数Key -> checkpoint
     */
    public static final String FLINK_TASK_CHECKPOINT_ENABLE_KEY = "flink.task.checkpoint.enable";
    public static final String FLINK_TASK_CHECKPOINT_INTERVAL_KEY = "flink.task.checkpoint.interval";
    public static final String FLINK_TASK_CHECKPOINT_TIMEOUT_KEY = "flink.task.checkpoint.timeout";
    public static final String FLINK_TASK_CHECKPOINT_MODE_KEY = "flink.task.checkpoint.mode";
    public static final String FLINK_TASK_CHECKPOINT_MIN_INTERVAL_KEY = "flink.task.checkpoint.minInterval";
    public static final String FLINK_TASK_CHECKPOINT_MAX_CONCURRENT_KEY = "flink.task.checkpoint.maxConcurrent";
    public static final String FLINK_TASK_CHECKPOINT_EXTERNALIZED_ENABLE_KEY = "flink.task.checkpoint.externalized.enable";
    public static final String FLINK_TASK_CHECKPOINT_EXTERNALIZED_CLEANUP_KEY = "flink.task.checkpoint.externalized.cleanUp";
    public static final String FLINK_TASK_CHECKPOINT_ON_FAIL_KEY = "flink.task.checkpoint.onFail";

    /**
     * Flink配置参数Key -> state
     */

    public static final String FLINK_TASK_STATE_BACKEND_TYPE_KEY = "flink.task.stateBackend.type";
    public static final String FLINK_TASK_STATE_BACKEND_ASYNC_KEY = "flink.task.stateBackend.async";
    public static final String FLINK_TASK_STATE_BACKEND_PATH_KEY = "flink.task.stateBackend.path";
    public static final String FLINK_TASK_STREAM_TIME_CHARACTERISTIC_KEY = "flink.task.stream.timeCharacteristic";



    /**
     * Flink配置参数Key -> Batch
     */


    /**
     * Flink配置参数Key -> Stream
     */


}
