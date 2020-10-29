package com.weiwan.support.launcher.envs;

import com.weiwan.support.common.options.OptionParser;
import com.weiwan.support.launcher.options.ApiRunOption;
import com.weiwan.support.launcher.options.GenericRunOption;
import com.weiwan.support.launcher.options.JobRunOption;

import java.io.IOException;

/**
 * @Author: xiaozhennan
 * @Date: 2020/9/30 10:46
 * @Package: com.weiwan.support.launcher.envs.ApiApplicationProcessor
 * @ClassName: ApiApplicationProcessor
 * @Description:
 **/
public class ApiApplicationProcessor extends ApplicationEnv {

    public ApiApplicationProcessor(String[] args) {
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
