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
