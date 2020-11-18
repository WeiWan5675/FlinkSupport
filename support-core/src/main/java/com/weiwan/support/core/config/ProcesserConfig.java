package com.weiwan.support.core.config;

import com.weiwan.support.common.config.AbstractConfig;

import java.util.Map;

/**
 * @Author: xiaozhennan
 * @Date: 2020/7/17 16:38
 * @Package: com.weiwan.core.pub.config
 * @ClassName: ProcesserConfig
 * @Description:
 **/
public class ProcesserConfig extends AbstractConfig {
    public ProcesserConfig(Map<String, Object> map) {
        super(map);
    }

    public String getChannleName() {
        return null;
    }
}
