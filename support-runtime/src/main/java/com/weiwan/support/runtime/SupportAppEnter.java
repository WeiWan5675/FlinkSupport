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
import com.weiwan.support.api.FlinkSupport;
import com.weiwan.support.api.config.FlinkEnvConfig;
import com.weiwan.support.api.config.JobConfig;
import com.weiwan.support.api.config.SupportContext;
import com.weiwan.support.api.etl.Reader;
import com.weiwan.support.api.options.RunOptions;
import com.weiwan.support.common.exception.SupportException;
import com.weiwan.support.common.options.OptionParser;
import com.weiwan.support.common.utils.Base64Util;
import com.weiwan.support.common.utils.CommonUtil;
import com.weiwan.support.common.utils.ReflectUtil;
import com.weiwan.support.core.*;
import com.weiwan.support.core.start.TaskResult;
import com.weiwan.support.core.constant.SupportConstants;
import com.weiwan.support.core.constant.SupportKey;
import com.weiwan.support.etl.framework.api.reader.BaseInputFormat;
import com.weiwan.support.plugins.jdbc.SqlGenerator;
import com.weiwan.support.utils.flink.env.FlinkContext;
import com.weiwan.support.utils.flink.env.FlinkContextUtil;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
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
            CommonUtil.useCommandLogLevel(options.getLogLevel());
            Map<String, Object> optionToMap = OptionParser.optionToMap(options);
            //读取job描述文件 json
            String jobContent = Base64Util.decode(options.getJobDescJson());
            Map<String, String> jobMap = JSONObject.parseObject(jobContent, Map.class);
            printEnvInfo(optionToMap, jobMap);
            Map<String, Object> parameters = new HashMap<>();
            parameters.putAll(jobMap);
            parameters.putAll(optionToMap);
            SupportContext context = convertOptionsToContext(options, parameters);
            SupportContextHolder.init(context);
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
                flinkSupport = new BatchSupport((ExecutionEnvironment) env, context);
            } else {
                throw new SupportException("Unsupported application mode, please check the operating parameters");
            }


            Class<?> aClass = Class.forName("com.weiwan.support.etl.framework.streaming.SupportOutputFormatSink");
//            Object o = aClass.newInstance();
            logger.info("aClass: " + aClass.toGenericString());

            //这里会找不到类
            Class<?> aClass1 = Class.forName("com.weiwan.support.plugins.reader.ExampleReader");
            logger.info("aClass1" + aClass1.toGenericString());
            Class<?> aClass2 = Class.forName("com.weiwan.support.plugins.reader.ExampleInputFormat");
            logger.info("aClass2" + aClass2.toGenericString());
            Class<?> aClass3 = Class.forName("com.weiwan.support.plugins.jdbc.SqlGeneratorForMysql");
            logger.info("aClass3" + aClass3.toGenericString());

            Reader o = (Reader) aClass1.newInstance();
            BaseInputFormat o1 = (BaseInputFormat) aClass2.newInstance();
            SqlGenerator o2 = (SqlGenerator) aClass3.newInstance();
            flinkSupport.initEnv(env, context, options);
            Method submit = ReflectUtil.getDeclaredMethod(flinkSupport, "submit");
            TaskResult taskResult = (TaskResult) submit.invoke(flinkSupport);
            logger.info("Job: " + taskResult.getJobID() + " run!!!!!!!!!!!!!!!!!!!!!!!");
        } catch (Exception e) {
            logger.error("Error executing Flink Support task!", e);
            e.printStackTrace();
            throw e;
        }
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


    private static SupportContext convertOptionsToContext(RunOptions options, Map<String, Object> taskObj) {
        SupportContext supportContext = new SupportContext(options);
        FlinkEnvConfig flinkEnvConfig = new FlinkEnvConfig();
        flinkEnvConfig.addFlinkTaskConfig(taskObj);
        JobConfig jobConfig = new JobConfig(taskObj);
        supportContext.setFlinkEnvConfig(flinkEnvConfig);
        supportContext.setJobConfig(jobConfig);
        return supportContext;
    }
}
