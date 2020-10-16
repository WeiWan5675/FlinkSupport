package com.weiwan.support.launcher.envs;

import com.weiwan.support.common.constant.Constans;
import com.weiwan.support.common.enums.SupportExceptionEnum;
import com.weiwan.support.common.exception.SupportException;
import com.weiwan.support.common.options.OptionParser;
import com.weiwan.support.common.utils.CommonUtil;
import com.weiwan.support.common.utils.LogUtil;
import com.weiwan.support.common.utils.SystemUtil;
import com.weiwan.support.core.constant.SupportConstants;
import com.weiwan.support.launcher.SupportAppClient;
import com.weiwan.support.launcher.enums.ResourceMode;
import com.weiwan.support.launcher.enums.RunMode;
import com.weiwan.support.launcher.options.GenericRunOption;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

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

    protected ResourceMode resourceMode;
    protected RunMode runMode;

    public ApplicationEnv(String[] args) {
        this.args = args;
        this.optionParser = new OptionParser(this.args);
    }


    public abstract boolean process();

    public boolean enter(RunMode runMode) {
        this.runMode = runMode;
        //在处理之前,可以在这里进行一些基本操作


        if (!emptyParameterCheck(genericRunOption)) {
            throw SupportException.generateParameterEmptyException("Flink support operating parameters are empty");
        }
        if (!illegalParameterCheck(genericRunOption)) {
            throw SupportException.generateParameterIllegalException("Illegal operating parameters of flink support");
        }

        //处理日志
        LogUtil.useCommandLogLevel(genericRunOption.getLogLevel());
        //设置组件Home
        findSupportHomePath();
//        findFlinkHomePath();
//        findHadoopHomePath();
//        readDefaultConfigFile();
        //

        if (!process()) {
            //处理失败
            return false;
        }
        //处理后进行一些特殊处理
        return true;
    }

    /**
     * 检查通过返回true
     *
     * @param genericRunOption
     * @return
     */
    public abstract boolean emptyParameterCheck(GenericRunOption genericRunOption);

    /**
     * 检查通过返回true
     *
     * @param genericRunOption
     * @return
     */
    public abstract boolean illegalParameterCheck(GenericRunOption genericRunOption);

    public void shutdown() {
        stop();
    }

    protected abstract void stop();


    public void findSupportHomePath() {
        String myHome = genericRunOption.getMyHome();
        if (StringUtils.isEmpty(myHome)) {
            myHome = SystemUtil.getSystemVar(SupportConstants.KEY_SUPPORT_HOME);
        }
        if (StringUtils.isEmpty(myHome)) {
            LOGGER.warn("the FLINK_SUPPORT_HOME environment variable was not found, use the launcher root directory!");
            LOGGER.warn("use the path of the startup class path as ARGUS_HOME");
            //获得当前启动类jar包得实际地址 $ARGUS_`HOME/lib
            String appPath = CommonUtil.getAppPath(SupportAppClient.class);
            File file = new File(appPath);
            myHome = file.getParent();
        }
        genericRunOption.setMyHome(myHome);
        LOGGER.info(String.format("FLINK_SUPPORT_HOME is [%s]", myHome));
    }
}
