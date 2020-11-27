package com.weiwan.support.launcher;

import com.weiwan.support.launcher.envs.ApplicationEnv;
import com.weiwan.support.launcher.options.GenericRunOption;

import java.io.IOException;

/**
 * @Author: xiaozhennan
 * @Date: 2020/11/27 10:42
 * @Package: com.weiwan.support.launcher.K8sJobApplicationProcessor
 * @ClassName: K8sJobApplicationProcessor
 * @Description:
 **/
public class K8sJobApplicationProcessor extends ApplicationEnv {
    public K8sJobApplicationProcessor(String[] args) {
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
