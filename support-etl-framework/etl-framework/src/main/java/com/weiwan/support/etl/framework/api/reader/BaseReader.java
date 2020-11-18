package com.weiwan.support.etl.framework.api.reader;

import com.weiwan.support.core.SupportAppContext;
import com.weiwan.support.core.api.Reader;
import com.weiwan.support.core.config.JobConfig;
import com.weiwan.support.core.config.ReaderConfig;
import com.weiwan.support.core.pojo.DataRecord;
import com.weiwan.support.etl.framework.streaming.SupportInputFormatSource;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.java.typeutils.TypeExtractor;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;


/**
 * @Author: xiaozhennan
 * @Date: 2020/7/14 17:02
 * @Package: com.weiwan.support.pub.api
 * @ClassName: ReaderBase
 * @Description:
 **/
public abstract class BaseReader<OUT extends DataRecord> implements Reader<OUT> {

    private StreamExecutionEnvironment env;
    private SupportAppContext context;
    private JobConfig jobConfig;
    private ReaderConfig readerConfig;

    private static final String KEY_READER_NAME = "reader.name";
    private static final String KEY_READER_TYPE = "reader.type";
    private static final String KEY_READER_CLASS_NAME = "reader.class";
    private static final String KEY_READER_PARALLELISM = "reader.parallelism";

    protected String readerName;
    protected String readerType;
    protected String readerClassName;
    protected Integer readerParallelism;

    public BaseReader(StreamExecutionEnvironment env, SupportAppContext context) {
        this.env = env;
        this.context = context;
        this.jobConfig = context.getJobConfig();
        this.readerConfig = context.getJobConfig().getReaderConfig();
        this.readerName = readerConfig.getStringVal(KEY_READER_NAME, "ArugsReader");
        this.readerType = readerConfig.getStringVal(KEY_READER_TYPE);
        this.readerClassName = readerConfig.getStringVal(KEY_READER_CLASS_NAME);
        this.readerParallelism = readerConfig.getIntVal(KEY_READER_PARALLELISM, 1);
    }

    public abstract BaseInputFormat getInputFormat(SupportAppContext context);

    public abstract void readRequire(SupportAppContext context);

    @Override
    public DataStream<OUT> read(StreamExecutionEnvironment env, SupportAppContext context) {
        BaseInputFormat<OUT, BaseInputSpliter> inputFormat = getInputFormat(context);
        TypeInformation<OUT> inputFormatTypes = TypeExtractor.getInputFormatTypes(inputFormat);
        SupportInputFormatSource<OUT> supportInputFormatSource = new SupportInputFormatSource<>(inputFormat, inputFormatTypes);
        DataStreamSource<OUT> stream = env.addSource(supportInputFormatSource, readerName, inputFormatTypes);
        //单独设置Source的并行度,默认Reader的并行度都是1 env的并行度并不会影响reader的并行度
        DataStreamSource<OUT> streamSource = stream.setParallelism(readerParallelism);
        //进行reading后处理,保留口子,如果需要处理可以重写该方法
        DataStream<OUT> afterStream = afterReading(streamSource, context);
        return afterStream;
    }

    /**
     * <p> 方便某些自定义reader进行一些后处理工作
     * 保留口子,方便一些自定义reader在进行数据处理时,特殊处理一些内容
     *
     * @param stream 输入是 {@link DataStream<DataRecord>}
     * @return 输出也是 {@link DataStream<DataRecord>}
     */
    protected DataStream<OUT> afterReading(DataStream<OUT> stream, SupportAppContext context) {
        //do nothing
        return stream;
    }

    ;
}
