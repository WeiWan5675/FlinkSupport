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

    private ApiRunOption option;

    public ApiApplicationProcessor(String[] args) {
        super(args);
        OptionParser optionParser = new OptionParser(args);
        option = optionParser.parse(ApiRunOption.class);
    }

    @Override
    public boolean process() {
        return true;
    }

    /**
     * 检查通过返回true
     *
     * @param genericRunOption
     * @return
     */
    @Override
    public boolean emptyParameterCheck(GenericRunOption genericRunOption) {
        return false;
    }

    /**
     * 检查通过返回true
     *
     * @param genericRunOption
     * @return
     */
    @Override
    public boolean illegalParameterCheck(GenericRunOption genericRunOption) {
        return false;
    }


    @Override
    protected void stop() {
        System.out.println("api process shutdown is run");
    }
}
