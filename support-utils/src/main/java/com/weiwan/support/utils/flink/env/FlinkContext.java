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
package com.weiwan.support.utils.flink.env;


import com.weiwan.support.common.enums.SupportExceptionEnum;
import com.weiwan.support.common.exception.SupportException;
import com.weiwan.support.common.utils.DateUtils;
import com.weiwan.support.utils.flink.conf.FlinkConfig;
import com.weiwan.support.utils.flink.conf.FlinkContains;
import com.weiwan.support.utils.flink.conf.FlinkEnvConfig;
import com.weiwan.support.utils.flink.loging.FlinkLogger;
import org.apache.commons.lang3.StringUtils;
import org.apache.flink.api.common.JobExecutionResult;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import java.util.Date;
import java.util.Map;

/**
 * @Author: xiaozhennan
 * @Date: 2020/4/29 1:43
 * @Package: com.hopson.dc.realtime.java.init
 * @ClassName: FlinkContext
 * @Description:
 **/
public class FlinkContext<T> {

    public static final FlinkLogger logger = FlinkContextUtil.getLogger(FlinkContext.class);

    private T env;
    private String[] args;
    private Class<T> envClass;
    private String contextContent;
    private FlinkConfig<String, String> flinkConfig;
    private FlinkEnvConfig flinkEnvConfig;

    public FlinkContext() {

    }

    public FlinkContext(T executionEnvironment, Class<T> envTClass, String[] args) {
        this.env = executionEnvironment;
        this.envClass = envTClass;
        this.args = args;
    }

    public FlinkContext(T executionEnvironment) {
    }

    public FlinkContext(T executionEnvironment, Class<T> streamExecutionEnvironmentClass, String contextArgs) {

    }

    public FlinkContext(T executionEnvironment, FlinkEnvConfig flinkEnvConfig) {
        this.env = executionEnvironment;
        this.flinkEnvConfig = flinkEnvConfig;
    }

    public T getEnv() {
        return (T) env;
    }

    public void setArgs(String[] args) {
        this.args = args;
    }

    public String[] getArgs() {
        return args;
    }


    public Class<T> getEnvClass() {
        return envClass;
    }

    public <E> void addFlinkConfig(final Map<String, String> toMap) {
        this.flinkConfig = new FlinkConfig(toMap);
    }

    public FlinkConfig<String, String> getFlinkConfig() {
        return this.flinkConfig;
    }


    public JobExecutionResult execute(final String taskName) throws Exception {
        try {
            return executeTask(taskName);
        } catch (Exception e) {
            logger.error("启动Flink程序失败! 请检查日志!", e);
        }
        return null;
    }

    public JobExecutionResult execute() throws Exception {
        try {
            String taskName = flinkConfig.getVar(FlinkContains.FLINK_TASK_NAME);
            if (StringUtils.isEmpty(taskName)) {
                taskName = FlinkContains.FLINK_TASK_NAME_DEFAULT_PREFIX + DateUtils.getDateStr(new Date());
            }
            return executeTask(taskName);
        } catch (Exception e) {
            logger.error("启动Flink程序失败! 请检查日志!", e);
            throw e;
        }
    }

    private JobExecutionResult executeTask(final String taskName) throws Exception {
        if (FlinkContains.JAVA_STREAM_ENV == this.envClass) {
            StreamExecutionEnvironment waitEnv = (StreamExecutionEnvironment) env;
            return waitEnv.execute(taskName);
        }
        if (FlinkContains.JAVA_BATCH_ENV == this.envClass) {
            ExecutionEnvironment waitEnv = (ExecutionEnvironment) env;
            return waitEnv.execute(taskName);
        }
        throw new SupportException(SupportExceptionEnum.FAILED);
    }


    public String getContextContent() {
        return contextContent;
    }

    public void setContextContent(String contextContent) {
        this.contextContent = contextContent;
    }

    public Map<String, Object> getFlinkEnvConfig() {
        return flinkEnvConfig.getAll();
    }
}
