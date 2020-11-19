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
package com.weiwan.support.utils.flink.conf;

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
    public static final String FLINK_CONF_FILE_DEFAULT = "flink-conf.yaml";
    public static final String FLINK_TASK_NAME = "flink.task.name";
    public static final String FLINK_TASK_TYPE = "flink.task.type";
    public static final String FLINK_TASK_NAME_DEFAULT_PREFIX = "FlinkApplication-";


    /**
     * Flink配置参数Key -> Common
     */
    public static final String FLINK_TASK_COMMON_PARALLELISM_KEY = "flink.task.common.parallelism";
    public static final String FLINK_TASK_COMMON_RESTART_MODE_KEY = "flink.task.common.restartMode";
    public static final String FLINK_TASK_COMMON_RESTART_NUM_KEY = "flink.task.common.restartNum";
    public static final String FLINK_TASK_COMMON_RESTART_INTERVAL_KEY = "flink.task.common.restartInterval";
    public static final String FLINK_TASK_COMMON_RESTART_FAIL_MAX_KEY = "flink.task.common.restartFailMaxNum";
    public static final String FLINK_TASK_COMMON_QUEUE_KEY = "flink.task.common.queue";

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
