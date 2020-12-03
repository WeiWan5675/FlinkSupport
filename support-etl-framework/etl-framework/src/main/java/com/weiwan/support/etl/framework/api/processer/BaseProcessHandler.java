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
package com.weiwan.support.etl.framework.api.processer;

import com.weiwan.support.core.SupportContext;
import com.weiwan.support.core.config.ProcesserConfig;
import com.weiwan.support.core.config.JobConfig;
import com.weiwan.support.core.pojo.DataRecord;
import org.apache.flink.api.common.functions.RichMapFunction;
import org.apache.flink.runtime.state.FunctionInitializationContext;
import org.apache.flink.runtime.state.FunctionSnapshotContext;
import org.apache.flink.streaming.api.checkpoint.CheckpointedFunction;

/**
 * @Author: xiaozhennan
 * @Date: 2020/11/18 15:36
 * @Package: com.weiwan.support.etl.framework.api.processer.BaseProcessHandler
 * @ClassName: BaseProcessHandler
 * @Description:
 **/
public abstract class BaseProcessHandler<IN, OUT> extends RichMapFunction<IN, OUT> implements CheckpointedFunction {

    protected SupportContext context;
    protected JobConfig jobConfig;
    protected ProcesserConfig processerConfig;

    public BaseProcessHandler(SupportContext context) {
        this.context = context;
        this.jobConfig = context.getJobConfig();
        this.processerConfig = context.getJobConfig().getProcesserConfig();
    }

    public BaseProcessHandler() {
    }

    @Override
    public OUT map(IN value) throws Exception {
        return process(value);
    }


    @Override
    public void snapshotState(FunctionSnapshotContext context) throws Exception {

    }

    @Override
    public void initializeState(FunctionInitializationContext context) throws Exception {

    }


    public abstract OUT process(IN value);
}
