package com.weiwan.support.core;

import com.alibaba.fastjson.JSONObject;
import com.weiwan.support.common.exception.SupportException;
import com.weiwan.support.common.options.OptionParser;
import com.weiwan.support.common.utils.Base64Util;
import com.weiwan.support.common.utils.CommonUtil;
import com.weiwan.support.common.utils.StringCompressUtil;
import com.weiwan.support.core.api.FlinkSupport;
import com.weiwan.support.core.api.TaskResult;
import com.weiwan.support.core.config.JobConfig;
import com.weiwan.support.core.constant.SupportConstants;
import com.weiwan.support.core.constant.SupportKey;
import com.weiwan.support.core.start.RunOptions;
import com.weiwan.support.utils.flink.conf.FlinkEnvConfig;
import com.weiwan.support.utils.flink.env.FlinkContext;
import com.weiwan.support.utils.flink.env.FlinkContextUtil;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.TypeVariable;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: xiaozhennan
 * @Date: 2020/9/28 13:09
 * @Package: com.weiwan.flink.support.core.SupportAppEnter
 * @ClassName: SupportAppEnter
 * @Description: 核心启动类，也就是Flink程序的入口
 **/
public class SupportAppEnter {

    private static final Logger logger = LoggerFactory.getLogger(SupportAppEnter.class);


    public static void main(String[] args) throws Exception {

        try {
            OptionParser optionParser = new OptionParser(args);
            RunOptions options = optionParser.parse(RunOptions.class);
            System.out.println("========================================");
            CommonUtil.useCommandLogLevel(options.getLogLevel());
            System.out.println("============================================");
            Map<String, Object> optionToMap = OptionParser.optionToMap(options);
            //读取job描述文件 json
            String jobContent = Base64Util.decode(options.getJobDescJson());
//        Map<String, String> jobMap = YamlUtils.loadYamlStr(jobConfContent);
            Map<String, String> jobMap = JSONObject.parseObject(jobContent, Map.class);

            if (jobMap != null) {
                logger.info(jobMap.toString());
            }
            System.out.println(jobMap);
            printEnvInfo(optionToMap, jobMap);
            Map<String, Object> tmpObj = new HashMap<>();
            tmpObj.putAll(jobMap);
            tmpObj.putAll(optionToMap);
            SupportAppContext context = convertOptionsToContext(options, tmpObj);
            FlinkSupport flinkSupport = null;
            Object env = null;
            if (options.isStream()) {
                FlinkContext<StreamExecutionEnvironment> streamContext = FlinkContextUtil.getStreamContext(context.getFlinkEnvConfig());
                env = streamContext.getEnv();
                //流模式
                String appClassName = context.getJobConfig().getStringVal(SupportKey.APP_ENTER_CLASS);
                //判断是否是etl模式
                if (options.isEtl()) {
                    //动态加载etl框架,如果是etl模式,实际上这个类名是固定的:
                    //com.weiwan.support.etl.framework.app.ETLStreamBaseApp
                    Class<?> aClass = Class.forName(SupportConstants.ETL_BASE_APP_CLASSNAME);
                    Constructor<?> constructor = aClass.getDeclaredConstructor(StreamExecutionEnvironment.class, SupportAppContext.class);
                    flinkSupport = (FlinkSupport) constructor.newInstance((StreamExecutionEnvironment) env, context);
                    flinkSupport.addReader(null);
                    flinkSupport.addProcess(null);
                    flinkSupport.addWriter(null);
                } else if (options.isTable()) {
                    throw new SupportException("features not yet supported stay tuned!");
                } else {
                    Class<?> aClass = Class.forName(appClassName);
                    System.out.println(aClass.getName());

                    System.out.println(aClass.getTypeParameters().toString());
                    Constructor<?> declaredConstructor = aClass.getConstructor(StreamExecutionEnvironment.class, SupportAppContext.class);

                    declaredConstructor.setAccessible(true);
                    flinkSupport = (FlinkSupport) declaredConstructor.newInstance((StreamExecutionEnvironment) env, context);
                }
            } else if (options.isBatch()) {
                //批模式
                env = ExecutionEnvironment.getExecutionEnvironment();
                flinkSupport = new BatchAppSupport((ExecutionEnvironment) env, context);
            } else {
                throw new SupportException("Unsupported application mode, please check the operating parameters");
            }


            TaskResult taskResult = flinkSupport.submitFlinkTask(env);


        } catch (Exception e) {
            System.err.println("报错拉+++++++++++++++++++++++");
            e.printStackTrace();
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
