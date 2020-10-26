package com.weiwan.support.launcher.envs;

import com.weiwan.support.common.options.OptionParser;
import com.weiwan.support.launcher.options.ApiRunOption;
import com.weiwan.support.launcher.options.GenericRunOption;
import com.weiwan.support.launcher.options.JobRunOption;

/**
 * @Author: xiaozhennan
 * @Date: 2020/9/30 10:46
 * @Package: com.weiwan.support.launcher.envs.ApiApplicationProcessor
 * @ClassName: ApiApplicationProcessor
 * @Description:
 **/
public class ApiApplicationProcessor
        extends ApplicationEnv
{

    public ApiApplicationProcessor(String[] args) {
        super(args);
    }

    @Override
    public void init(GenericRunOption option) {

    }

    @Override
    public boolean process() {
        return false;
    }

    @Override
    public void stop() {

    }
}
