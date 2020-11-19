package com.weiwan.support.launcher.envs.processer;

import com.weiwan.support.launcher.envs.ApplicationEnv;
import com.weiwan.support.launcher.options.GenericRunOption;

import java.io.IOException;

/**
 * @Author: xiaozhennan
 * @Date: 2020/11/19 16:34
 * @Package: com.weiwan.support.launcher.envs.processer.LocalApplicationProcesser
 * @ClassName: LocalApplicationProcesser
 * @Description:
 **/
public class LocalApplicationProcesser extends ApplicationEnv {
    public LocalApplicationProcesser(String[] args) {
        super(args);
    }

    @Override
    public void init(GenericRunOption genericRunOption) throws IOException {

    }

    @Override
    public boolean process() {
        return false;
    }

    /**
     * 检查通过返回true
     *
     * @param genericRunOption
     * @return
     */
    @Override
    public void emptyParameterCheck(GenericRunOption genericRunOption) {

    }

    /**
     * 检查通过返回true
     *
     * @param genericRunOption
     * @return
     */
    @Override
    public void illegalParameterCheck(GenericRunOption genericRunOption) {

    }

    @Override
    public void stop() {

    }
}
