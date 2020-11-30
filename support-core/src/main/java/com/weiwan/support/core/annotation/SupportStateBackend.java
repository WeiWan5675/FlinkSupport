package com.weiwan.support.core.annotation;

import com.weiwan.support.utils.flink.conf.StateBackendEnum;

/**
 * @Author: xiaozhennan
 * @Date: 2020/11/27 15:26
 * @Package: com.weiwan.support.core.annotation.SupportStateBackend
 * @ClassName: SupportStateBackend
 * @Description:
 **/
public @interface SupportStateBackend {

    StateBackendEnum type() default StateBackendEnum.MEMORY_TYPE;

    String path() default "hdfs:///flink/checkpoints";

}
