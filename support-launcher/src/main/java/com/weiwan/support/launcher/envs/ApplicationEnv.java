package com.weiwan.support.launcher.envs;

import com.weiwan.support.common.options.OptionParser;
import com.weiwan.support.launcher.options.GenericRunOption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author: xiaozhennan
 * @Date: 2020/9/30 10:44
 * @Package: com.weiwan.support.launcher.envs.ApplicationEnv
 * @ClassName: ApplicationEnv
 * @Description:
 **/
public abstract class ApplicationEnv {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationEnv.class);
    private String[] args;
    protected OptionParser optionParser;
    protected GenericRunOption genericRunOption;

    public ApplicationEnv(String[] args) {
        this.args = args;
        this.optionParser = new OptionParser(this.args);
    }


    public abstract boolean process();

    public boolean enter() {
        //在处理之前,可以在这里进行一些基本操作
        if (!process()) {
            //处理失败
            return false;
        }
        //处理后进行一些特殊处理
        return true;
    }

    public void shutdown() {
        stop();
    }

    protected abstract void stop();
}
