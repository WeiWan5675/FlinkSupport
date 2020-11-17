package com.weiwan.support.utils.flink.conf;

import org.apache.flink.streaming.api.CheckpointingMode;

/**
 * @Author: xiaozhennan
 * @Date: 2020/7/6 15:14
 * @Package: com.hopson.dc.flink.common.pub
 * @ClassName: FlinkDefault
 * @Description:
 **/
public class FlinkDefault {


    /**
     * 检查点默认参数
     */
    public static final long CHECKPOINT_INTERVAL_DEFAULT = 1000L;
    public static final CheckpointingMode CHECKPOINT_MODE = CheckpointingMode.EXACTLY_ONCE;
    public static final String EXTERNALIZED_ENABLE_DEFAULT = "false";
    public static final String EXTERNALIZED_CLEANUP_DEFAULT = "DELETE_ON_CANCELLATION";
    public static final String CHECKPOINT_TIMEOUT_DEFAULT = "600000";
    public static final String CHECKPOINT_MIN_PAUSE_BETWEEN_DEFAULT = "500";
    public static final String CHECKPOINT_MAX_CONCURRENT_DEFAULT = "1";
    public static final String ON_FAIL_DEFAULT = "true";


    /**
     * 默认状态后端参数
     */
    public static final String BACKEND_TYPE_DEFAULT = "FileSystem";
    public static final String BACKEND_PATH_DEFAULT = "hdfs:///flink/checkpoints";
    public static final String BACKEND_ASYNC_DEFAULT = "true";


    public static final String FLINK_TASK_COMMON_PARALLELISM_DEFAULT = "1";
    public static final String FLINK_TASK_COMMON_RESTART_MODE_DEFAULT = "fixed-delay";
    public static final String FLINK_TASK_COMMON_RESTART_NUM_DEFAILT = "3";
    public static final String FLINK_TASK_COMMON_RESTART_INTERVAL_DEFAULT = "30000";
    public static final String FLINK_TASK_COMMON_RESTART_FAIL_MAX_DEFAULT = "3";


    public static final String FLINK_TASK_STREAM_TIME_CHARACTERISTIC_KEY = "ProcessingTime";

}
