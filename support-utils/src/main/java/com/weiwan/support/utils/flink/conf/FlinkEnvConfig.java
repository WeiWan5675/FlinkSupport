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
package com.weiwan.support.utils.flink.conf;

import com.weiwan.support.common.config.AbstractConfig;

import java.util.Map;

/**
 * @Author: xiaozhennan
 * @Date: 2020/7/19 19:50
 * @Package: com.weiwan.support.core.pub.config
 * @ClassName: FlinkEnvConfig
 * @Description:
 **/
public class FlinkEnvConfig extends AbstractConfig {
    public FlinkEnvConfig(Map<String, Object> map) {
        super(map);
    }
    public FlinkEnvConfig() {
        super();
    }


    public void addFlinkTaskConfig(Map<String,Object> varMap){
        for (String taskKey : varMap.keySet()) {
            if (taskKey.startsWith("flink.task")) {
                //flink的配置
                this.setVal(taskKey, varMap.get(taskKey));
            }
        }
    }
}
