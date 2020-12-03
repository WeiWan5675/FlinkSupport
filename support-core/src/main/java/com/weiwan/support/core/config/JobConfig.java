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
package com.weiwan.support.core.config;

import com.weiwan.support.common.config.AbstractConfig;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: xiaozhennan
 * @Date: 2020/7/17 16:38
 * @Package: com.weiwan.support.core.pub.config
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
            if (key.startsWith("etl.reader")) {
                readerConfig.setVal(key, value);
            } else if (key.startsWith("etl.writer")) {
                writerConfig.setVal(key, value);
            } else if (key.startsWith("etl.processer")) {
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
