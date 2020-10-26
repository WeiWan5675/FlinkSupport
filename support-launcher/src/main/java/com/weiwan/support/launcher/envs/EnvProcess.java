package com.weiwan.support.launcher.envs;

import com.weiwan.support.launcher.options.GenericRunOption;

/**
 * @Author: xiaozhennan
 * @Date: 2020/10/26 14:56
 * @Package: com.weiwan.support.launcher.envs.EnvProcess
 * @ClassName: EnvProcess
 * @Description:
 **/
public interface EnvProcess {


    void init(GenericRunOption option);


    boolean process();


    /**
     * 检查通过返回true
     *
     * @param genericRunOption
     * @return
     */
    default boolean emptyParameterCheck(GenericRunOption genericRunOption) {
        return true;
    }

    /**
     * 检查通过返回true
     *
     * @param genericRunOption
     * @return
     */
    default boolean illegalParameterCheck(GenericRunOption genericRunOption) {
        return true;
    }


    void stop();
}
