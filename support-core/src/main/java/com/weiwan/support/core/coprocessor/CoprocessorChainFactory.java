package com.weiwan.support.core.coprocessor;

import com.weiwan.support.core.api.SupportDataFlow;
import com.weiwan.support.core.start.RunOptions;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * @Author: xiaozhennan
 * @Date: 2020/12/2 11:37
 * @Package: com.weiwan.support.core.coprocessor.CoprocessorChainFactory
 * @ClassName: CoprocessorChainFactory
 * @Description:
 **/
public class CoprocessorChainFactory {


    /**
     * 创建流应用的协处理器链
     * @param env 流环境
     * @param dataFlow 数据流对象(JOB对象)
     * @param options (启动参数)
     * @param <E> (StreamEnv{@link StreamExecutionEnvironment})
     * @param <S1> (JOB的open输出类型{@link com.weiwan.support.core.StreamSupport#open})
     * @param <S2> (Job的output输入类型{@link com.weiwan.support.core.StreamSupport#output})
     * @return
     */
    public static final <E, S1, S2> CoprocessorChain<E, S1, S2> createStreamCoprocessorChain(E env, SupportDataFlow<E, S1, S2> dataFlow, RunOptions options) {
        //TODO 这里应该把StreamRunMode单独作为一个枚举
        if (options.isEtl()) {
            //插件etl模式
            return new EtlStreamCoprocessorChain(env, dataFlow, null);
        } else if (options.isTable()) {
            //table模式
            return new TableStreamCoprocessorChain(env, dataFlow, null);
        } else {
            //用户程序
            return new GenericStreamCoprocessorChain(env, dataFlow, null);
        }
    }


    public static final <E, S1, S2> CoprocessorChain<E, S1, S2> createBatchCoprocessorChain(E env, SupportDataFlow<E, S1, S2> dataFlow, RunOptions options) {
        throw new RuntimeException("Not currently supported");
    }
}
