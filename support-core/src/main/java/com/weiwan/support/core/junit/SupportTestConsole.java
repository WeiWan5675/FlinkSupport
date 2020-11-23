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
package com.weiwan.support.core.junit;

import com.weiwan.support.common.utils.FileUtil;
import com.weiwan.support.common.utils.ReflectUtil;
import com.weiwan.support.common.utils.YamlUtils;
import com.weiwan.support.core.BatchAppSupport;
import com.weiwan.support.core.StreamAppSupport;
import com.weiwan.support.core.SupportAppContext;
import com.weiwan.support.core.api.FlinkSupport;
import com.weiwan.support.core.api.TaskResult;
import com.weiwan.support.core.config.JobConfig;
import com.weiwan.support.core.start.RunOptions;
import com.weiwan.support.utils.flink.conf.FlinkEnvConfig;
import com.weiwan.support.utils.flink.env.FlinkContextUtil;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: xiaozhennan
 * @Date: 2020/11/22 15:01
 * @Package: com.weiwan.support.core.junit
 * @ClassName: SupportTestContext
 * @Description:
 **/
public class SupportTestConsole {

    private FlinkSupport flinkSupport;
    private RunOptions options;
    private SupportAppContext context;
    private Object env;
    private String jobFile;
    private Class<? extends FlinkSupport> waitTestClass;

    public SupportTestConsole(Class<? extends FlinkSupport> tClass, Object... args) {
        waitTestClass = tClass;
        SupportTest annotation = waitTestClass.getAnnotation(SupportTest.class);
        try {
            flinkSupport = (FlinkSupport) waitTestClass.newInstance();


            for (Object arg : args) {
                if(arg == null){
                    continue;
                }
                Class<?> aClass = arg.getClass();
                if (aClass == RunOptions.class) {
                    options = (RunOptions) arg;
                    continue;
                }
                if (aClass == SupportAppContext.class) {
                    context = (SupportAppContext) arg;
                    continue;
                }
                if (aClass == StreamExecutionEnvironment.class) {
                    env = (StreamExecutionEnvironment) arg;
                    continue;
                }
                if (aClass == ExecutionEnvironment.class) {
                    env = (ExecutionEnvironment) arg;
                    continue;
                }
                if (aClass == String.class) {
                    jobFile = (String) arg;
                    continue;
                }
            }

            if (annotation != null && jobFile == null) {
                jobFile = annotation.jobFile();
            }


            if (options == null) {
                options = new RunOptions();
            }

            Class<?> superclass = flinkSupport.getClass().getSuperclass();
            if (context == null) {
                //本地读取
                context = new SupportAppContext(options);
                String configContent = FileUtil.readFileContent(jobFile);
                Map<String, String> userVarMap = YamlUtils.loadYamlStr(configContent);
                Map<String, Object> tmpMap = new HashMap<>();
                tmpMap.putAll(userVarMap);
                FlinkEnvConfig flinkEnvConfig = new FlinkEnvConfig();
                flinkEnvConfig.addFlinkTaskConfig(tmpMap);
                JobConfig jobConfig = new JobConfig(tmpMap);
                context.setFlinkEnvConfig(flinkEnvConfig);
                context.setJobConfig(jobConfig);
            }

            if (env == null) {
                if (superclass == StreamAppSupport.class) {
                    env = FlinkContextUtil.getStreamContext(context.getFlinkEnvConfig()).getEnv();
                }
                if (superclass == BatchAppSupport.class) {
                    env = FlinkContextUtil.getBatchContext(context.getFlinkEnvConfig()).getEnv();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private SupportTestConsole(Builder builder) {
        this(builder.waitTestClass, builder.options, builder.context, builder.env, builder.jobFile);
    }

    public static Builder newBuilder() {
        return new Builder();
    }


    public void run() throws Exception {
        flinkSupport.initEnv(env, context, options);
        Method submit = ReflectUtil.getDeclaredMethod(flinkSupport, "submit");
        TaskResult taskResult = (TaskResult) submit.invoke(flinkSupport);
        System.out.println("Job: " + taskResult.getJobID() + " run!!!!!!!!!!!!!!!!!!!!!!!");
    }


    public static final class Builder {
        private RunOptions options;
        private SupportAppContext context;
        private Object env;
        private String jobFile;
        private Class<? extends FlinkSupport> waitTestClass;

        private Builder() {
        }

        public Builder options(RunOptions val) {
            options = val;
            return this;
        }

        public Builder context(SupportAppContext val) {
            context = val;
            return this;
        }

        public Builder env(Object val) {
            env = val;
            return this;
        }

        public Builder jobFile(String val) {
            jobFile = val;
            return this;
        }

        public Builder waitTestClass(Class<? extends FlinkSupport> val) {
            waitTestClass = val;
            return this;
        }

        public SupportTestConsole build() {
            return new SupportTestConsole(this);
        }
    }
}
