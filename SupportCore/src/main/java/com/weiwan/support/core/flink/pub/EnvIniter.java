package com.weiwan.support.core.flink.pub;

import java.io.IOException;

/**
 * @Author: xiaozhennan
 * @Date: 2020/4/30 18:25
 * @Package: com.hopson.dc.realtime.flink.common.pub
 * @ClassName: EnvInter
 * @Description:
 **/
public interface EnvIniter<K, V> {

    void initBatch(K context);

    void initStream(V context) throws IOException;

    boolean isLocalStream(V k);

    boolean isLocalBatch(K v);

}
