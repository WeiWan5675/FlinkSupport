package com.weiwan.support.core.config;

import com.weiwan.support.common.config.AbstractConfig;

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

    private ProcesserConfig processerConfig;

    public JobConfig(Map<String, Object> map) {
        super(map);
        this.readerConfig = new ReaderConfig(new HashMap<>());
        this.writerConfig = new WriterConfig(new HashMap<>());
        this.processerConfig = new ProcesserConfig(new HashMap<>());
        for (String key : map.keySet()) {
            Object value = map.get(key);
            if (key.startsWith("reader")) {
                readerConfig.setVal(key, value);
            } else if (key.startsWith("writer")) {
                writerConfig.setVal(key, value);
            } else if (key.startsWith("channel")) {
                processerConfig.setVal(key, value);
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


    public ProcesserConfig getProcesserConfig() {
        return processerConfig;
    }

    public void setProcesserConfig(ProcesserConfig processerConfig) {
        this.processerConfig = processerConfig;
    }
}
