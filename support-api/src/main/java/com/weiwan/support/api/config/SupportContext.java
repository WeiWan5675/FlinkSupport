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
package com.weiwan.support.api.config;


import com.weiwan.support.api.options.RunOptions;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: xiaozhennan
 * @Date: 2020/9/28 16:38
 * @Package: com.weiwan.support.api.common.SupportContext
 * @ClassName: SupportContext
 * @Description:
 **/
public class SupportContext implements Serializable {

    private JobConfig JobConfig;
    private FlinkEnvConfig flinkEnvConfig;
    private RunOptions options;
    private Map<Object, Object> internalCache = new ConcurrentHashMap<>();

    public SupportContext(RunOptions options) {
        this.options = options;
    }

    public SupportContext() {

    }

    public JobConfig getJobConfig() {
        return JobConfig;
    }

    public void setJobConfig(JobConfig jobConfig) {
        JobConfig = jobConfig;
    }

    public FlinkEnvConfig getFlinkEnvConfig() {
        return flinkEnvConfig;
    }

    public void setFlinkEnvConfig(FlinkEnvConfig flinkEnvConfig) {
        this.flinkEnvConfig = flinkEnvConfig;
    }

    public RunOptions getOptions() {
        return options;
    }

    public void setOptions(RunOptions options) {
        this.options = options;
    }


    public void addCache(Object key, Object obj) {
        internalCache.put(key, obj);
    }

    public <T> T getCache(Object key, Class<T> clazz) {
        Object o = internalCache.get(key);
        T val = null;
        if (o.getClass() == clazz) {
            val = (T) o;
        }
        return val;
    }
}
