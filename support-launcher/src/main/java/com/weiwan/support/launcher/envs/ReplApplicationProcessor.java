package com.weiwan.support.launcher.envs;

import com.weiwan.support.common.options.OptionParser;
import com.weiwan.support.launcher.options.ApiRunOption;
import com.weiwan.support.launcher.options.GenericRunOption;
import com.weiwan.support.launcher.options.ReplRunOption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author: xiaozhennan
 * @Date: 2020/9/30 10:45
 * @Package: com.weiwan.support.launcher.envs.ReplApplicationProcessor
 * @ClassName: ReplApplicationProcessor
 * @Description:
 **/
public class ReplApplicationProcessor extends ApplicationEnv {
    private ReplRunOption option;

    private static final Logger logger = LoggerFactory.getLogger(ReplApplicationProcessor.class);

    public ReplApplicationProcessor(String[] args) {
        super(args);
        OptionParser optionParser = new OptionParser(args);
        option = optionParser.parse(ReplRunOption.class);
    }

    @Override
    public void init(GenericRunOption option) {

    }

    @Override
    public boolean process() {
        System.out.println("选择? Y or N");
        return true;
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
