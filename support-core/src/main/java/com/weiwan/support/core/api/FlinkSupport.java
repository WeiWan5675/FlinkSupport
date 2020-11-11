package com.weiwan.support.core.api;

import com.weiwan.support.core.SupportAppContext;
import com.weiwan.support.core.start.RunOptions;

/**
 * @Author: xiaozhennan
 * @Date: 2020/9/28 15:56
 * @Package: com.weiwan.support.core.api.FlinkSupport
 * @ClassName: FlinkSupport
 * @Description:
 **/
public interface FlinkSupport<T> {

    void initEnv(T t, SupportAppContext context, RunOptions options);

    T getEnv();

    SupportAppContext getContext();

    TaskResult executeTask() throws Exception;
}
