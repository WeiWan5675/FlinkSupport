package com.weiwan.support.core.flink.pub;

import com.weiwan.support.core.flink.utils.FlinkContextUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.LocalEnvironment;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.runtime.state.StateBackend;
import org.apache.flink.runtime.state.memory.MemoryStateBackend;
import org.apache.flink.streaming.api.CheckpointingMode;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.environment.CheckpointConfig;
import org.apache.flink.streaming.api.environment.LocalStreamEnvironment;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: xiaozhennan
 * @Date: 2020/4/29 10:54
 * @Package: com.hopson.dc.realtime.java.init
 * @ClassName: JavaEnvIniter
 * @Description: 初始化javaEnv环境
 **/
public class JavaEnvIniter extends BaseEnvIniter implements EnvIniter<FlinkContext<ExecutionEnvironment>, FlinkContext<StreamExecutionEnvironment>> {

    public static final FlinkLogger logger = FlinkContextUtil.getLogger();

    public void initBatch(FlinkContext<ExecutionEnvironment> context) {
        logger.info("initialize flinkContext for Batch-Env!");
        String confFilePath = getConfFilePath(context, isLocalBatch(context));
        //合并配置文件
        ParameterTool mergeTool = mergeEnvConfig(context, confFilePath);
        //最终配置放入到env环境中
        context.getEnv().getConfig().setGlobalJobParameters(mergeTool);
        //初始化通用配置
        initFlinkCommon(context);

        initFlinkBatch(context);
    }

    public void initStream(FlinkContext<StreamExecutionEnvironment> context) throws IOException {
        logger.info("initialize flinkContext for Stream-Env");
        ParameterTool mergeTool = mergeContentConfig(context);
        context.getEnv().getConfig().setGlobalJobParameters(mergeTool);
        logger.debug("read flinkcontext configuration information completed");

        //2. 初始化Flink-Common配置
        initFlinkCommon(context);
        //3. 初始化Flink-Stream配置
        initFlinkStream(context);
        //4. 初始化检查点配置
        initCheckpoint(context);
        logger.debug("initializes a checkpoint for the flinkContext of the stream-Env");
        //5. 初始化状态后端
        initStateBackend(context);
        logger.debug("initializes a stateBackend for the flinkContext of the stream-Env");
        logger.info("the flinkContext initialization of Stream-Env is completed");
    }

    private ParameterTool mergeContentConfig(FlinkContext context) {
        /**
         * 获得配置文件数据,将配置文件|args|system 三个合并 放入到flink env的全局parameters中
         */
        Map<String, Object> contentMap = context.getFlinkEnvConfig();

        Map<String, String> cMap = new HashMap<>();

        for (String key : contentMap.keySet()) {
            cMap.put(key, String.valueOf(contentMap.get(key)));
        }
        ParameterTool parameterTool =
                ParameterTool.fromMap(cMap)
                        .mergeWith(ParameterTool.fromSystemProperties());
        context.addFlinkConfig(parameterTool.toMap());
        return parameterTool;
    }

    private void initFlinkStream(FlinkContext<StreamExecutionEnvironment> context) {
        FlinkConfig<String, String> flinkConfig = context.getFlinkConfig();
        StreamExecutionEnvironment env = context.getEnv();
        String orDefault = flinkConfig.getOrDefault(FlinkContains.FLINK_TASK_STREAM_TIME_CHARACTERISTIC_KEY, FlinkDefault.FLINK_TASK_STREAM_TIME_CHARACTERISTIC_KEY);
        env.setStreamTimeCharacteristic(TimeCharacteristic.valueOf(orDefault));
    }

    private void initFlinkBatch(FlinkContext<ExecutionEnvironment> context) {
        ExecutionEnvironment env = context.getEnv();
//        env.setSessionTimeout(1);
    }

    private void initFlinkCommon(FlinkContext<?> context) {
        FlinkConfig<String, String> conf = context.getFlinkConfig();
        String parallelism = conf.getOrDefault(FlinkContains.FLINK_TASK_COMMON_PARALLELISM_KEY, FlinkDefault.FLINK_TASK_COMMON_PARALLELISM_DEFAULT);
        if (isStream(context)) {
            StreamExecutionEnvironment env = (StreamExecutionEnvironment) context.getEnv();
            //设置并行度
            env.setParallelism(Integer.parseInt(parallelism));
            //选择重启策略
            env.setRestartStrategy(chooseARestartStrategy(context));
        }
        if (isBatch(context)) {
            ExecutionEnvironment env = (ExecutionEnvironment) context.getEnv();
            //设置并行度
            env.setParallelism(Integer.valueOf(parallelism));
            //选择重启策略
            env.setRestartStrategy(chooseARestartStrategy(context));
        }
    }


    private void initStateBackend(FlinkContext<StreamExecutionEnvironment> context) {
        StreamExecutionEnvironment env = context.getEnv();
        FlinkConfig flinkConfig = context.getFlinkConfig();
        if (env instanceof LocalStreamEnvironment) {
            //本地的话 使用内存后端就可以了
            StateBackend stateBackend = new MemoryStateBackend();
            env.setStateBackend(stateBackend);
        }
        try {
            //如果配置了其它后端,使用配置的,本地如果不想使用自定义状态后端,可以在配置文件中注释掉
            env.setStateBackend(useStateBackend(flinkConfig));
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("init state backend failed, please check the config file !", e);
        }
    }


    private void initCheckpoint(FlinkContext<StreamExecutionEnvironment> context) throws IOException {
        StreamExecutionEnvironment env = context.getEnv();
        FlinkConfig flinkConfig = context.getFlinkConfig();
        String checkPointEnable = flinkConfig.getVar(FlinkContains.FLINK_TASK_CHECKPOINT_ENABLE_KEY);
        if (!StringUtils.isEmpty(checkPointEnable) && Boolean.valueOf(checkPointEnable)) {
            //默认值设置
            checkPointDefault(env);
            //启用checkpoint
            String checkPointInterval = flinkConfig.getVar(FlinkContains.FLINK_TASK_CHECKPOINT_INTERVAL_KEY);
            String checkPointMode = flinkConfig.getVar(FlinkContains.FLINK_TASK_CHECKPOINT_MODE_KEY);

            boolean notEmpty = StringUtils.isNoneEmpty(checkPointEnable, checkPointInterval, checkPointMode);
            if (notEmpty) {
                env.enableCheckpointing(Long.valueOf(checkPointInterval), CheckpointingMode.valueOf(checkPointMode));
                CheckpointConfig point = env.getCheckpointConfig();
                configureCheckPoint(flinkConfig, point);
            }
        } else {
            logger.debug("not enable checkpoint, skip init checkpoint!");
        }

    }


    /**
     * 设置checkpoint默认值
     *
     * @param env
     */
    private void checkPointDefault(StreamExecutionEnvironment env) {
        env.enableCheckpointing(FlinkDefault.CHECKPOINT_INTERVAL_DEFAULT, FlinkDefault.CHECKPOINT_MODE);
        CheckpointConfig point = env.getCheckpointConfig();
        point.setCheckpointTimeout(Long.valueOf(FlinkDefault.CHECKPOINT_TIMEOUT_DEFAULT));
        point.setMinPauseBetweenCheckpoints(Long.valueOf(FlinkDefault.CHECKPOINT_MIN_PAUSE_BETWEEN_DEFAULT));
        point.setMaxConcurrentCheckpoints(Integer.valueOf(FlinkDefault.CHECKPOINT_MAX_CONCURRENT_DEFAULT));
        point.setFailOnCheckpointingErrors(Boolean.valueOf(FlinkDefault.ON_FAIL_DEFAULT));
        if (Boolean.valueOf(FlinkDefault.EXTERNALIZED_ENABLE_DEFAULT))
            point.enableExternalizedCheckpoints(CheckpointConfig.ExternalizedCheckpointCleanup.DELETE_ON_CANCELLATION);

    }


    public boolean isLocalStream(FlinkContext<StreamExecutionEnvironment> context) {
        return context.getEnv() instanceof LocalStreamEnvironment;
    }

    public boolean isLocalBatch(FlinkContext<ExecutionEnvironment> context) {
        return context.getEnv() instanceof LocalEnvironment;
    }


}
