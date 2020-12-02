package com.weiwan.support.core.api;

import com.weiwan.support.core.SupportContext;
import com.weiwan.support.core.start.RunOptions;

/**
 * @Author: xiaozhennan
 * @Date: 2020/11/30 11:36
 * @Package: com.weiwan.support.core.api.Support
 * @ClassName: Support
 * @Description:
 **/
public interface Support<T> {

    SupportContext getContext();

    void setContext(SupportContext context);

    default void initEnv(T env, SupportContext context, RunOptions options) {
        throw new RuntimeException("This method must be implemented by the user before it can be called, otherwise it is regarded as illegal access");
    }

    default T getEnv() {
        throw new RuntimeException("This method must be implemented by the user before it can be called, otherwise it is regarded as illegal access");
    }
}
