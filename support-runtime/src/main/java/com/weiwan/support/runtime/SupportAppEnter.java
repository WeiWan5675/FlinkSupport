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
package com.weiwan.support.runtime;

import com.alibaba.fastjson.JSONObject;
import com.weiwan.support.common.exception.SupportException;
import com.weiwan.support.common.options.OptionParser;
import com.weiwan.support.common.utils.Base64Util;
import com.weiwan.support.common.utils.CommonUtil;
import com.weiwan.support.common.utils.ReflectUtil;
import com.weiwan.support.core.*;
import com.weiwan.support.core.annotation.Support;
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
import sun.reflect.annotation.AnnotationParser;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
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

            //输出重定向到日志文件
//            StdoutRedirect.redirectSystemOutAndErrToLog();
            OptionParser optionParser = new OptionParser(args);
            RunOptions options = optionParser.parse(RunOptions.class);
            CommonUtil.useCommandLogLevel(options.getLogLevel());
            Map<String, Object> optionToMap = OptionParser.optionToMap(options);
            //读取job描述文件 json
            String jobContent = Base64Util.decode(options.getJobDescJson());
            Map<String, String> jobMap = JSONObject.parseObject(jobContent, Map.class);
            printEnvInfo(optionToMap, jobMap);
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
                    Class<?> etlAppClass = Class.forName(SupportConstants.ETL_BASE_APP_CLASSNAME);
                    flinkSupport = (FlinkSupport) etlAppClass.newInstance();
                } else if (options.isTable()) {
                    throw new SupportException("features not yet supported stay tuned!");
                } else {
                    Class<?> userAppClass = Class.forName(appClassName);
                    flinkSupport = (FlinkSupport) userAppClass.newInstance();
                }


            } else if (options.isBatch()) {
                //批模式
                env = ExecutionEnvironment.getExecutionEnvironment();
                flinkSupport = new BatchAppSupport((ExecutionEnvironment) env, context);
            } else {
                throw new SupportException("Unsupported application mode, please check the operating parameters");
            }


            Class<? extends FlinkSupport> aClass = flinkSupport.getClass();
            Support annotation = aClass.getDeclaredAnnotation(Support.class);
            if(annotation != null){
                options.setEnableAnnotation(true);
            }
            flinkSupport.initEnv(env, context, options);
            Method submit = ReflectUtil.getDeclaredMethod(flinkSupport, "submit");
            TaskResult taskResult = (TaskResult) submit.invoke(flinkSupport);
            System.out.println("Job: " + taskResult.getJobID() + " run!!!!!!!!!!!!!!!!!!!!!!!");
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
