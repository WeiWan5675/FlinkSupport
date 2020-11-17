package com.weiwan.support.utils.flink.env;


import com.weiwan.support.common.constant.Constans;
import com.weiwan.support.common.enums.SupportExceptionEnum;
import com.weiwan.support.common.exception.SupportException;
import com.weiwan.support.common.utils.CommonUtil;
import com.weiwan.support.common.utils.PropertiesUtil;
import com.weiwan.support.common.utils.VariableCheckTool;
import com.weiwan.support.common.utils.YamlUtils;
import com.weiwan.support.utils.flink.conf.*;
import com.weiwan.support.utils.flink.loging.FlinkLogger;
import org.apache.flink.api.common.restartstrategy.RestartStrategies;
import org.apache.flink.api.common.time.Time;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.contrib.streaming.state.RocksDBStateBackend;
import org.apache.flink.runtime.state.StateBackend;
import org.apache.flink.runtime.state.filesystem.FsStateBackend;
import org.apache.flink.runtime.state.memory.MemoryStateBackend;
import org.apache.flink.streaming.api.environment.CheckpointConfig;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * @Author: xiaozhennan
 * @Date: 2020/7/6 16:24
 * @Package: com.hopson.dc.flink.common.pub
 * @ClassName: BaseEnvIniter
 * @Description:
 **/
public abstract class BaseEnvIniter {

    FlinkLogger LOGGER = FlinkContextUtil.getLogger(BaseEnvIniter.class);

    protected StateBackend useStateBackend(FlinkConfig flinkConfig) throws IOException {
        StateBackend stateBackend;
        String backendTypeStr = flinkConfig.getVar(FlinkContains.FLINK_TASK_STATE_BACKEND_TYPE_KEY);
        String backendPathStr = flinkConfig.getVar(FlinkContains.FLINK_TASK_STATE_BACKEND_PATH_KEY);
        String backendAsyncStr = flinkConfig.getVar(FlinkContains.FLINK_TASK_STATE_BACKEND_ASYNC_KEY);
        String backendType = VariableCheckTool.checkNullOrDefault(backendTypeStr, FlinkDefault.BACKEND_TYPE_DEFAULT);
        String backendPath = VariableCheckTool.checkNullOrDefault(backendPathStr, FlinkDefault.BACKEND_PATH_DEFAULT);
        String backendAsync = VariableCheckTool.checkNullOrDefault(backendAsyncStr, FlinkDefault.BACKEND_ASYNC_DEFAULT);
        Boolean async = Boolean.valueOf(backendAsync);
        if (backendType == null) {
            stateBackend = new MemoryStateBackend();
            LOGGER.debug("not configure state backend ,use default backend: [{}]", StateBackendEnum.MEMORY_TYPE);
            return stateBackend;
        }

        if (StateBackendEnum.MEMORY_TYPE.getCode().equalsIgnoreCase(backendType)) {
            stateBackend = new MemoryStateBackend(async);
            LOGGER.debug("use {} state backend", StateBackendEnum.MEMORY_TYPE.getMsg());
            return stateBackend;
        }

        if (StateBackendEnum.FILESYSTEM_TYPE.getCode().equalsIgnoreCase(backendType)) {
            stateBackend = new FsStateBackend(backendPath.replace("hdfs://", "file:///tmp"), async);
            LOGGER.debug("use {} state backend", StateBackendEnum.FILESYSTEM_TYPE.getMsg());
            return stateBackend;
        }

        if (StateBackendEnum.ROCKSDB_TYPE.getCode().equalsIgnoreCase(backendType)) {
            stateBackend = new RocksDBStateBackend(backendPath);
            LOGGER.debug("use {} state backend", StateBackendEnum.ROCKSDB_TYPE.getMsg());
            return stateBackend;
        }

        return null;
    }


    protected void configureCheckPoint(Map<String, String> conf, CheckpointConfig point) {
        Long checkPointTimeOut = Long.valueOf(VariableCheckTool.checkNullOrDefault(
                conf.get(FlinkContains.FLINK_TASK_CHECKPOINT_TIMEOUT_KEY),
                FlinkDefault.CHECKPOINT_TIMEOUT_DEFAULT));
        Long minPauseBetween = Long.valueOf(VariableCheckTool.checkNullOrDefault(
                conf.get(FlinkContains.FLINK_TASK_CHECKPOINT_MIN_INTERVAL_KEY),
                FlinkDefault.CHECKPOINT_MIN_PAUSE_BETWEEN_DEFAULT));
        Integer maxConcurent = Integer.valueOf(VariableCheckTool.checkNullOrDefault(
                conf.get(FlinkContains.FLINK_TASK_CHECKPOINT_MAX_CONCURRENT_KEY),
                FlinkDefault.CHECKPOINT_MAX_CONCURRENT_DEFAULT));
        boolean onFail = Boolean.getBoolean(VariableCheckTool.checkNullOrDefault(
                conf.get(FlinkContains.FLINK_TASK_CHECKPOINT_ON_FAIL_KEY),
                FlinkDefault.ON_FAIL_DEFAULT));

        point.setCheckpointTimeout(checkPointTimeOut);
        point.setMinPauseBetweenCheckpoints(minPauseBetween);
        point.setMaxConcurrentCheckpoints(maxConcurent);
        point.setFailOnCheckpointingErrors(onFail);

        String extFlag = VariableCheckTool.checkNullOrDefault(
                conf.get(FlinkContains.FLINK_TASK_CHECKPOINT_EXTERNALIZED_ENABLE_KEY),
                FlinkDefault.EXTERNALIZED_ENABLE_DEFAULT);
        String extType = VariableCheckTool.checkNullOrDefault(
                conf.get(FlinkDefault.EXTERNALIZED_CLEANUP_DEFAULT),
                FlinkEnum.CHECKPOINT_DELETE_ON_CANCELLATION.getCode());

        if (Boolean.valueOf(extFlag) && FlinkEnum.CHECKPOINT_DELETE_ON_CANCELLATION.getCode().equalsIgnoreCase(extType)) {
            point.enableExternalizedCheckpoints(
                    CheckpointConfig.ExternalizedCheckpointCleanup.DELETE_ON_CANCELLATION);
        }
        if (Boolean.valueOf(extFlag) && FlinkEnum.CHECKPOINT_RETAIN_ON_CANCELLATION.getCode().equalsIgnoreCase(extType)) {
            point.enableExternalizedCheckpoints(
                    CheckpointConfig.ExternalizedCheckpointCleanup.RETAIN_ON_CANCELLATION);
        }
    }


    /**
     * @param context
     * @param localStream
     * @return 读取配置文件, 配置文件不存在则抛出异常
     */
    protected String getConfFilePath(FlinkContext<?> context, boolean localStream) {

        /**
         * 1. 读取配置文件,配置文件不存在则抛出异常
         */
        //1. 获取原始参数
        ParameterTool argsTool = ParameterTool.fromArgs(context.getArgs());
        //2. 获取配置文件路径
        String conf_file_path = argsTool.get(FlinkContains.FLINK_CONF_FILE_NAME);

        if (localStream && conf_file_path == null) {
            conf_file_path = JavaEnvIniter.class.getResource("/").getPath() + FlinkContains.FLINK_CONF_FILE_DEFAULT;
        } else {
            if (conf_file_path == null || conf_file_path.trim().equalsIgnoreCase("")) {
                LOGGER.error("必须要指定Flink程序启动配置文件!");
                throw new SupportException(SupportExceptionEnum.PARAMETER_EMPTY, "必须要指定Flink程序启动配置文件");
            }
        }
        return conf_file_path;
    }


    /**
     * @param context
     * @param conf_file_path
     * @return 合并后的ParameterTool
     * 获得配置文件数据, 将配置文件|args|system 三个合并 放入到flink env的全局parameters中
     **/
    protected ParameterTool mergeEnvConfig(FlinkContext<?> context, String conf_file_path) {
        /**
         * 获得配置文件数据,将配置文件|args|system 三个合并 放入到flink env的全局parameters中
         */
        Map<String, String> flinkConfig = readFlinkConfig(conf_file_path);
        ParameterTool parameterTool =
                ParameterTool.fromMap(flinkConfig)
                        .mergeWith(ParameterTool.fromArgs(context.getArgs())
                                .mergeWith(ParameterTool.fromSystemProperties()));
        context.addFlinkConfig(parameterTool.toMap());
        return parameterTool;
    }


    protected Map<String, String> readFlinkConfig(String confPath) {

        Map<String, String> flinkConfig = null;
        if (confPath.endsWith(Constans.PROPERTIES_FILE_SUFFIX)) {
            //是properties配置文件
            LOGGER.info("加载Properties类型配置文件:{}", confPath);
            flinkConfig = PropertiesUtil.loadProperties(new File(confPath).getAbsolutePath());
        } else if (confPath.endsWith(Constans.YAML_FILE_SUFFIX) || confPath.endsWith(Constans.YML_FILE_SUFFIX)) {
            //是yaml配置文件
            flinkConfig = YamlUtils.getYamlByFileName(new File(confPath).getAbsolutePath());
            LOGGER.info("加载Yaml类型配置文件:{}", confPath);
        } else {
            LOGGER.error("未知的配置文件类型,请检查启动参数!");
            throw new SupportException(SupportExceptionEnum.PARAMETER_ILLEGAL);
        }
        return flinkConfig;
    }

    public RestartStrategies.RestartStrategyConfiguration chooseARestartStrategy(FlinkContext<?> context) {
        //重启策略
//            restart-mode: fixed-delay #fixed-delay | failure-rate | none  默认fixed-delay
//            restart-num: 3  #重启次数  默认3
//            restart-time: 30000  #重启延迟  默认30S
        FlinkConfig conf = context.getFlinkConfig();
        String restartMode = (String) conf.getOrDefault(FlinkContains.FLINK_TASK_COMMON_RESTART_MODE_KEY, FlinkDefault.FLINK_TASK_COMMON_RESTART_MODE_DEFAULT);
        String restartNum = (String) conf.getOrDefault(FlinkContains.FLINK_TASK_COMMON_RESTART_NUM_KEY, FlinkDefault.FLINK_TASK_COMMON_RESTART_NUM_DEFAILT);
        String restartInterval = (String) conf.getOrDefault(FlinkContains.FLINK_TASK_COMMON_RESTART_INTERVAL_KEY, FlinkDefault.FLINK_TASK_COMMON_RESTART_INTERVAL_DEFAULT);
        String restartMaxFailNum = (String) conf.getOrDefault(FlinkContains.FLINK_TASK_COMMON_RESTART_FAIL_MAX_KEY, FlinkDefault.FLINK_TASK_COMMON_RESTART_FAIL_MAX_DEFAULT);

        if (FlinkEnum.valueOfCode(restartMode) == FlinkEnum.TASK_RESTART_MODE_FIXED_DELAY) {
            return RestartStrategies.fixedDelayRestart(
                    Integer.valueOf(restartNum),
                    Time.milliseconds(Long.valueOf(restartInterval)));
        }
        if (FlinkEnum.valueOfCode(restartMode) == FlinkEnum.TASK_RESTART_MODE_FAILURE_RATE) {
            return RestartStrategies.failureRateRestart(
                    Integer.parseInt(restartMaxFailNum),
                    Time.minutes(1),
                    Time.minutes(3));
        }
        if (FlinkEnum.valueOfCode(restartMode) == FlinkEnum.TASK_RESTART_MODE_NONE) {
            return RestartStrategies.noRestart();
        }
        return null;
    }

    protected boolean isStream(FlinkContext<?> context) {
        return context.getEnv() instanceof StreamExecutionEnvironment;
    }

    protected boolean isBatch(FlinkContext<?> context) {
        return context.getEnv() instanceof ExecutionEnvironment;
    }

}
