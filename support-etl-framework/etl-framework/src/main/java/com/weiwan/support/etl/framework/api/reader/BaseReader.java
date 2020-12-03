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
package com.weiwan.support.etl.framework.api.reader;

import com.weiwan.support.core.SupportContext;
import com.weiwan.support.core.api.Reader;
import com.weiwan.support.core.config.JobConfig;
import com.weiwan.support.core.config.ReaderConfig;
import com.weiwan.support.core.pojo.DataRecord;
import com.weiwan.support.core.start.RunOptions;
import com.weiwan.support.etl.framework.streaming.SupportInputFormatSource;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.java.typeutils.TypeExtractor;
import org.apache.flink.core.io.InputSplit;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;


/**
 * @Author: xiaozhennan
 * @Date: 2020/7/14 17:02
 * @Package: com.weiwan.support.etl.framework.api.reader
 * @ClassName: ReaderBase
 * @Description:
 **/
public abstract class BaseReader<OUT> implements Reader<OUT> {


    private static final String KEY_READER_NAME = "etl.reader.name";
    private static final String KEY_READER_TYPE = "etl.reader.type";
    private static final String KEY_READER_CLASS_NAME = "etl.reader.class";
    private static final String KEY_READER_PARALLELISM = "etl.reader.parallelism";

    private StreamExecutionEnvironment env;
    private SupportContext context;
    private JobConfig jobConfig;
    private RunOptions runOptions;

    protected ReaderConfig readerConfig;
    protected String readerName;
    protected String readerType;
    protected String readerClassName;
    protected Integer readerParallelism;


    @Override
    public void initEnv(StreamExecutionEnvironment env, SupportContext context, RunOptions options) {
        this.env = env;
        this.context = context;
        this.runOptions = options;
        this.jobConfig = context.getJobConfig();
        this.readerConfig = context.getJobConfig().getReaderConfig();
        this.readerName = readerConfig.getStringVal(KEY_READER_NAME, "SupportReader");
        this.readerType = readerConfig.getStringVal(KEY_READER_TYPE);
        this.readerClassName = readerConfig.getStringVal(KEY_READER_CLASS_NAME);
        this.readerParallelism = readerConfig.getIntVal(KEY_READER_PARALLELISM, 1);
    }

    @Override
    public StreamExecutionEnvironment getEnv() {
        return env;
    }

    public BaseReader() {

    }

    public abstract void require(SupportContext context);

    public abstract <IS extends InputSplit> BaseInputFormat<OUT, IS> getInputFormat(SupportContext context);

    @Override
    public DataStream<OUT> read(StreamExecutionEnvironment env, SupportContext context) {
        require(context);
        BaseInputFormat<OUT, InputSplit> inputFormat = getInputFormat(context);
        TypeInformation<OUT> inputFormatTypes = TypeExtractor.getInputFormatTypes(inputFormat);
        SupportInputFormatSource<OUT> supportInputFormatSource = new SupportInputFormatSource<>(inputFormat, inputFormatTypes);
        DataStreamSource<OUT> stream = env.addSource(supportInputFormatSource, readerName, inputFormatTypes);
        //单独设置Source的并行度,默认Reader的并行度都是1 env的并行度并不会影响reader的并行度
        DataStreamSource<OUT> streamSource = stream.setParallelism(readerParallelism);
        //进行reading后处理,保留口子,如果需要处理可以重写该方法
        return streamSource;
    }


    @Override
    public SupportContext getContext() {
        return this.context;
    }

    @Override
    public void setContext(SupportContext context) {
        this.context = context;
    }
}
