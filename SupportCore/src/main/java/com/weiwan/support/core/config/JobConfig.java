package com.weiwan.support.core.config;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: xiaozhennan
 * @Date: 2020/7/17 16:38
 * @Package: com.weiwan.core.pub.config
 * @ClassName: JobConfig
 * @Description:
 **/
public class JobConfig extends AbstractConfig {

    public static final String reader = "reader";

    public static final String writer = "writer";


    private ReaderConfig readerConfig;

    private WriterConfig writerConfig;

    private ChannelConfig channelConfig;

    public JobConfig(Map<String, Object> map) {
        super(map);
        this.readerConfig = new ReaderConfig(new HashMap<>());
        this.writerConfig = new WriterConfig(new HashMap<>());
        this.channelConfig = new ChannelConfig(new HashMap<>());
        for (String key : map.keySet()) {
            Object value = map.get(key);
            if (key.startsWith("reader")) {
                readerConfig.setVal(key, value);
            } else if (key.startsWith("writer")) {
                writerConfig.setVal(key, value);
            } else if (key.startsWith("channel")) {
                channelConfig.setVal(key, value);
            } else {
                this.setVal(key, value);
            }
        }
    }


    public ReaderConfig getReaderConfig() {
        return readerConfig;
    }

    public void setReaderConfig(ReaderConfig readerConfig) {
        this.readerConfig = readerConfig;
    }

    public WriterConfig getWriterConfig() {
        return writerConfig;
    }

    public void setWriterConfig(WriterConfig writerConfig) {
        this.writerConfig = writerConfig;
    }


    public ChannelConfig getChannelConfig() {
        return channelConfig;
    }

    public void setChannelConfig(ChannelConfig channelConfig) {
        this.channelConfig = channelConfig;
    }
}
