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
}
