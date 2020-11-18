package com.weiwan.support.core.config;

import com.weiwan.support.common.config.AbstractConfig;

import java.util.Map;

/**
 * @Author: xiaozhennan
 * @Date: 2020/7/17 16:38
 * @Package: com.weiwan.support.core.pub.config
 * @ClassName: WriterConfig
 * @Description:
 **/
public class WriterConfig extends AbstractConfig {
    public WriterConfig(Map<String, Object> map) {
        super(map);
    }

    public String getWriterName() {
        return null;
    }
}
