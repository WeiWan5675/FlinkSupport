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
package com.weiwan.support.launcher.envs;

import org.apache.flink.configuration.*;
import org.apache.flink.runtime.jobgraph.SavepointConfigOptions;
import org.apache.flink.yarn.configuration.YarnConfigOptions;

import java.util.List;

/**
 * @Author: xiaozhennan
 * @Date: 2020/11/6 16:14
 * @Package: com.weiwan.support.launcher.envs.JOBOptions
 * @ClassName: JOBOptions
 * @Description:
 **/
public class JOBOptions {

    //保存点路径,从检查点启动时可用
    public static final ConfigOption<String> SAVEPOINT_PATH = SavepointConfigOptions.SAVEPOINT_PATH;
    //是否开启增量检查点
    public static final ConfigOption<Boolean> INCREMENTAL_CHECKPOINTS = CheckpointingOptions.INCREMENTAL_CHECKPOINTS;
    //用户JAR
    public static final ConfigOption<List<String>> JARS = PipelineOptions.JARS;
    //依赖目录,包括FLINK_HOME/lib FLINK_HOME/plugins FLINK_SUPPORT_HOME/lib USER_RESOURCE
    public static final ConfigOption<List<String>> PROVIDED_LIB_DIRS = YarnConfigOptions.PROVIDED_LIB_DIRS;
    //Flink核心jar
    public static final ConfigOption<String> FLINK_DIST_JAR = YarnConfigOptions.FLINK_DIST_JAR;
    //部署模式
    public static final ConfigOption<String> TARGET = DeploymentOptions.TARGET;
    //应用名称
    public static final ConfigOption<String> APPLICATION_NAME = YarnConfigOptions.APPLICATION_NAME;
    //应用类型
    public static final ConfigOption<String> APPLICATION_TYPE = YarnConfigOptions.APPLICATION_TYPE;
    //应用资源队列
    public static final ConfigOption<String> APPLICATION_QUEUE = YarnConfigOptions.APPLICATION_QUEUE;
    //是否是依附模式,默认false
    public static final ConfigOption<Boolean> ATTACHED = DeploymentOptions.ATTACHED;
    //依附模式下ctrl+c后最大努力stop任务
    public static final ConfigOption<Boolean> SHUTDOWN_IF_ATTACHED = DeploymentOptions.SHUTDOWN_IF_ATTACHED;

    //类加载模式
    public static final ConfigOption<String> CLASSLOADER_RESOLVE_ORDER = CoreOptions.CLASSLOADER_RESOLVE_ORDER;
}
