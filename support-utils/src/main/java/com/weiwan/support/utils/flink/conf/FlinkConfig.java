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

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: xiaozhennan
 * @Date: 2020/6/24 10:52
 * @Package: com.hopson.dc.realtime.flink.common.pub
 * @ClassName: FlinkConfig
 * @Description:
 **/
public class FlinkConfig<K, V> extends HashMap<K, V> {

    public FlinkConfig(Map<K, V> m) {
        super(m);
    }

    public String getVar(String key) {
        if (this.get(key) != null) {
            return (String) this.get(key);
        }
        return null;
    }

    public Long getLongVar(String key) {
        if (this.get(key) != null) {
            return Long.valueOf((String) this.get(key));
        }
        return null;
    }

    public Integer getIntVar(String key) {
        if (this.get(key) != null) {
            return Integer.valueOf((String) this.get(key));
        }
        return null;
    }
}
