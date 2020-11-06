package com.weiwan.support.launcher.envs.processer;

import com.weiwan.support.launcher.options.GenericRunOption;

import java.io.IOException;

/**
 * @Author: xiaozhennan
 * @Date: 2020/10/26 14:56
 * @Package: com.weiwan.support.launcher.envs.processer.EnvProcess
 * @ClassName: EnvProcess
 * @Description:
 **/
public interface EnvProcess {


    void init(GenericRunOption genericRunOption) throws IOException;


    boolean process();


    /**
     * 检查通过返回true
     *
     * @param genericRunOption
     * @return
     */
    void emptyParameterCheck(GenericRunOption genericRunOption);

    /**
     * 检查通过返回true
     *
     * @param genericRunOption
     * @return
     */
    void illegalParameterCheck(GenericRunOption genericRunOption);


    void stop();
}
