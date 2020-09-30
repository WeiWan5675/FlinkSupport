package com.weiwan.support.launcher.envs;

import com.weiwan.support.common.options.OptionParser;
import com.weiwan.support.launcher.options.ApiRunOption;
import com.weiwan.support.launcher.options.ReplRunOption;

/**
 * @Author: xiaozhennan
 * @Date: 2020/9/30 10:45
 * @Package: com.weiwan.support.launcher.envs.ReplApplicationProcessor
 * @ClassName: ReplApplicationProcessor
 * @Description:
 **/
public class ReplApplicationProcessor extends ApplicationEnv {
    private ReplRunOption option;

    public ReplApplicationProcessor(String[] args) {
        super(args);
        OptionParser optionParser = new OptionParser(args);
        option = optionParser.parse(ReplRunOption.class);
    }

    @Override
    public boolean process() {
        System.out.println("选择? Y or N");
        return false;
    }

    @Override
    protected void stop() {
        System.out.println("repl shutdown hook run!");
    }
}
