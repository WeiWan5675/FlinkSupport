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
package com.weiwan.support.core.coprocessor;

import com.weiwan.support.core.SupportContext;
import com.weiwan.support.core.SupportContextHolder;
import com.weiwan.support.core.annotation.Checkpoint;
import com.weiwan.support.core.annotation.Parallelism;
import com.weiwan.support.core.annotation.Support;
import com.weiwan.support.core.annotation.SupportStateBackend;
import com.weiwan.support.core.api.SupportDataFlow;
import org.apache.flink.streaming.api.CheckpointingMode;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author: xiaozhennan
 * @Date: 2020/11/12 22:25
 * @Package: com.weiwan.support.core
 * @ClassName: StreamClassCoprocessor
 * @Description:
 **/
public class StreamClassCoprocessor extends SupportCoprocessor {
    public StreamClassCoprocessor(SupportContext context) {
        super(context);
    }

    private static final Logger logger = LoggerFactory.getLogger(StreamClassCoprocessor.class);
    private StreamExecutionEnvironment env;

    @Override
    public <E, S1, S2> Object process(E _env, SupportDataFlow<E, S1, S2> dataFlow, Object obj) throws Exception {
        Class<? extends SupportDataFlow> userClass = dataFlow.getClass();

        if (_env != null && _env instanceof StreamExecutionEnvironment) {
            this.env = (StreamExecutionEnvironment) _env;
        }
        SupportContext context = SupportContextHolder.getContext();



        /**
         * 如果在类上,就是env的并行度
         * 如果在方法上,就是该方法返回的stream的并行度
         * 如果在字段上,就是该stream字段的注解
         */
        //启用注解支持
        Support enabelSupport = userClass.getAnnotation(Support.class);
        if (enabelSupport != null && !enabelSupport.enable()) {

            //检查点支持
            Checkpoint checkpoint = userClass.getAnnotation(Checkpoint.class);
            if (checkpoint != null) {
                if (checkpoint.enableAutoConfigura()) {
                    //自动配置checkpoin,优先配置文件,入股配置文件没有,使用默认的,加了这个注解,就相当于开启了checkpoint
                } else {
                    //使用用户的注解进行checkpoint设置
                    long interval = checkpoint.interval();
                    int maxConcurrent = checkpoint.maxConcurrent();
                    CheckpointingMode mode = checkpoint.mode();
                }
            }

            //设置状态后端
            SupportStateBackend stateBackend = userClass.getAnnotation(SupportStateBackend.class);
            if(stateBackend != null){

            }
            //设置并行度
            Parallelism parallelism = userClass.getAnnotation(Parallelism.class);
            if (parallelism != null) {
                env.setParallelism(parallelism.num());
            }

        }

        return nextProcess((E) env, dataFlow, obj);
    }
}
