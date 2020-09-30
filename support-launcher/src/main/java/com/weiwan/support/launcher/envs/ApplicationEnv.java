package com.weiwan.support.launcher.envs;

import com.weiwan.support.common.options.OptionParser;
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

    public ApplicationEnv(String[] args) {
        this.args = args;
        this.optionParser = new OptionParser(args);
    }


    public abstract boolean process();

    public boolean enter() {
        return process();
    }

    public void shutdown() {
        stop();
    }

    protected abstract void stop();
}
