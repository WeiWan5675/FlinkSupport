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
package com.weiwan.support.core;

import com.weiwan.support.core.api.*;
import com.weiwan.support.core.start.RunOptions;
import org.apache.flink.api.java.ExecutionEnvironment;

/**
 * @Author: xiaozhennan
 * @Date: 2020/9/28 16:25
 * @Package: com.weiwan.support.core.BatchAppSupport
 * @ClassName: BatchAppSupport
 * @Description:
 **/
public class BatchAppSupport<IN, OIN> implements FlinkSupport<ExecutionEnvironment> {


    private ExecutionEnvironment environment;
    private SupportAppContext appContext;

    public BatchAppSupport(ExecutionEnvironment environment, SupportAppContext appContext) {
        this.environment = environment;
        this.appContext = appContext;
    }

    @Override
    public void initEnv(ExecutionEnvironment executionEnvironment, SupportAppContext context, RunOptions options) {

    }

    @Override
    public ExecutionEnvironment getEnv() {
        return this.environment;
    }

    @Override
    public SupportAppContext getContext() {
        return this.appContext;
    }

    @Override
    public TaskResult executeTask() throws Exception {
        return null;
    }

    private TaskResult submit() {
        return null;
    }
}
