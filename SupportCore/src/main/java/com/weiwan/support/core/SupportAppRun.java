package com.weiwan.support.core;

import com.alibaba.fastjson.JSONObject;
import com.weiwan.support.common.options.OptionParser;
import com.weiwan.support.common.utils.CommonUtil;
import com.weiwan.support.core.api.AppType;
import com.weiwan.support.core.api.FlinkSupport;
import com.weiwan.support.core.config.FlinkEnvConfig;
import com.weiwan.support.core.config.JobConfig;
import com.weiwan.support.core.flink.pub.FlinkContext;
import com.weiwan.support.core.flink.utils.FlinkContextUtil;
import com.weiwan.support.core.start.RunOptions;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: xiaozhennan
 * @Date: 2020/9/28 13:09
 * @Package: com.weiwan.flink.support.core.SupportAppRun
 * @ClassName: SupportAppRun
 * @Description: 核心启动类，也就是Flink程序的入口
 **/
public class SupportAppRun {

    private static final Logger logger = LoggerFactory.getLogger(SupportAppRun.class);


    public static void main(String[] args) throws Exception {


        OptionParser optionParser = new OptionParser(args);
        RunOptions options = optionParser.parse(RunOptions.class);
        CommonUtil.useCommandLogLevel(options.getLogLevel());
        Map<String, Object> optionToMap = OptionParser.optionToMap(options);
        //读取job描述文件 json
        String jobContent = options.getJobDescJson();
//        Map<String, String> jobMap = YamlUtils.loadYamlStr(jobConfContent);
        Map<String, String> jobMap = JSONObject.parseObject(jobContent, Map.class);
        printEnvInfo(optionToMap, jobMap);
        Map<String, Object> tmpObj = new HashMap<>();
        tmpObj.putAll(jobMap);
        tmpObj.putAll(optionToMap);
        SupportAppContext context = convertOptionsToContext(options, tmpObj);
        FlinkSupport flinkSupport = null;
        if (options.isStream()) {
            //流模式
            //判断是否是etl模式
            FlinkContext<StreamExecutionEnvironment> streamContext = FlinkContextUtil.getStreamContext(context.getFlinkEnvConfig());
            StreamExecutionEnvironment streamEnv = streamContext.getEnv();
            flinkSupport = new StreamAppSupport(streamEnv, context);

        } else {
            //批模式
            ExecutionEnvironment bathcEnv = ExecutionEnvironment.getExecutionEnvironment();
            flinkSupport = new BatchAppSupport(bathcEnv, context);
        }


        SupportAppContext supportAppContext = new SupportAppContext();

        AppType appType = AppType.ETL_SUPPORT;

        if (AppType.ETL_SUPPORT == appType) {
            //etl_support模式

        } else {
            //批或者流模式
        }

        //设置属性去


    }

    private static void printEnvInfo(Map<String, Object> optionToMap, Map<String, String> jobMap) {
        logger.debug("Environmental Startup Parameters");
        logger.debug("==============================================================");
        for (String key : optionToMap.keySet()) {
            logger.debug(String.format("key: [%s], value: [%s]", key, optionToMap.get(key)));
        }
        logger.debug("Job Deploy Parameters");
        logger.debug("==============================================================");
        for (String key : jobMap.keySet()) {
            logger.debug(String.format("key: [%s], value: [%s]", key, jobMap.get(key)));
        }
    }


    private static SupportAppContext convertOptionsToContext(RunOptions options, Map<String, Object> taskObj) {
        SupportAppContext supportAppContext = new SupportAppContext(options);
        FlinkEnvConfig flinkEnvConfig = new FlinkEnvConfig(new HashMap<>());
        JobConfig jobConfig = new JobConfig(taskObj);

        for (String taskKey : taskObj.keySet()) {
            if (taskKey.startsWith("flink.task")) {
                //flink的配置
                flinkEnvConfig.setVal(taskKey, taskObj.get(taskKey));
            }
        }
        supportAppContext.setFlinkEnvConfig(flinkEnvConfig);
        supportAppContext.setJobConfig(jobConfig);
        return supportAppContext;
    }
}
