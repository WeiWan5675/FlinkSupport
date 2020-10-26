package com.weiwan.support.launcher.envs;

import com.weiwan.support.common.exception.SupportException;
import com.weiwan.support.common.options.OptionParser;
import com.weiwan.support.common.utils.CommonUtil;
import com.weiwan.support.common.utils.LogUtil;
import com.weiwan.support.common.utils.SystemUtil;
import com.weiwan.support.common.utils.YamlUtils;
import com.weiwan.support.core.config.SupportCoreConf;
import com.weiwan.support.core.config.SupportETLConf;
import com.weiwan.support.core.config.SupportSqlConf;
import com.weiwan.support.core.constant.SupportConstants;
import com.weiwan.support.launcher.SupportAppClient;
import com.weiwan.support.launcher.enums.ResourceMode;
import com.weiwan.support.launcher.enums.RunMode;
import com.weiwan.support.launcher.options.GenericRunOption;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Map;

/**
 * @Author: xiaozhennan
 * @Date: 2020/9/30 10:44
 * @Package: com.weiwan.support.launcher.envs.ApplicationEnv
 * @ClassName: ApplicationEnv
 * @Description:
 **/
public abstract class ApplicationEnv implements EnvProcess {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationEnv.class);
    private String[] args;
    protected OptionParser optionParser;
    protected GenericRunOption genericRunOption;

    protected SupportCoreConf supportCoreConf;
    protected SupportETLConf supportETLConf;
    protected SupportSqlConf supportSqlConf;

    protected ResourceMode resourceMode;
    protected RunMode runMode;

    private boolean inited;

    public ApplicationEnv(String[] args) {
        this.args = args;
        this.optionParser = new OptionParser(this.args);
    }


    public boolean enter(RunMode runMode) {
        this.runMode = runMode;
        if (inited) {
            //设置客户端日志级别
            LogUtil.useCommandLogLevel(genericRunOption.getLogLevel());

            //参数校验,子类重写两个方法
            try {
                if (!emptyParameterCheck(genericRunOption)) {
                    LOGGER.error("dry run parameter verification failed");
                    throw SupportException.generateParameterEmptyException("Flink support operating parameters are empty");
                }
                if (!illegalParameterCheck(genericRunOption)) {
                    LOGGER.error("illegal parameter verification failed");
                    throw SupportException.generateParameterIllegalException("Illegal operating parameters of flink support");
                }
            } catch (SupportException exception) {
                LOGGER.error("Parameter verification failed, please check the parameter settings");
                throw exception;
            }

            //SupportHome
            setSupportHomePath(genericRunOption);

            findSupportDefault(genericRunOption);
            //设置组件Home
            setEnvironmentVariables(genericRunOption);


            this.init(genericRunOption);


            inited = true;
        }


        if (!process()) {
            //处理失败
            return false;
        }
        //处理后进行一些特殊处理
        return true;
    }

    private void findSupportDefault(GenericRunOption option) {
        String myHome = option.getMyHome();
        String coreConfFile = myHome + File.separator + SupportConstants.SUPPORT_CONF_DIR + SupportConstants.SUPPORT_CORE_CONF_FILE;
        String etlConfFile = myHome + File.separator + SupportConstants.SUPPORT_CONF_DIR + SupportConstants.SUPPORT_ETL_CONF_FILE;
        String sqlConfFile = myHome + File.separator + SupportConstants.SUPPORT_CONF_DIR + SupportConstants.SUPPORT_SQL_CONF_FILE;
        supportCoreConf = new SupportCoreConf(YamlUtils.getYamlByFileName(coreConfFile));
        supportETLConf = new SupportETLConf(YamlUtils.getYamlByFileName(etlConfFile));
        supportSqlConf = new SupportSqlConf(YamlUtils.getYamlByFileName(sqlConfFile));
    }

    private void setEnvironmentVariables(GenericRunOption option) {
        //FlinkHome
        setFlinkHomePath(option);
        //HadoopHome
        setHadoopHomePath(option);

        //
    }

    private void setHadoopHomePath(GenericRunOption option) {
        String hadoopHome = option.getHadoopHome();
        if (StringUtils.isEmpty(hadoopHome)) {
            //读取系统环境变量
            hadoopHome = SystemUtil.getSystemVar(SupportConstants.KEY_HADOOP_HOME);
        }
        if (StringUtils.isEmpty(hadoopHome)) {
            //从默认配置文件中读取
            hadoopHome = supportCoreConf.getStringVal(SupportConstants.KEY_HADOOP_HOME);
        } else {
            if (StringUtils.isEmpty(supportCoreConf.getStringVal(SupportConstants.KEY_HADOOP_HOME))) {
                supportCoreConf.setStringVal(SupportConstants.KEY_HADOOP_HOME, hadoopHome);
            }
        }
        option.setHadoopHome(hadoopHome);
        LOGGER.info("FLINK_HOME path is : {}", hadoopHome);
    }

    private void setFlinkHomePath(GenericRunOption option) {
        String flinkHome = option.getFlinkHome();
        if (StringUtils.isEmpty(flinkHome)) {
            //读取系统环境变量
            flinkHome = SystemUtil.getSystemVar(SupportConstants.KEY_FLINK_HOME);
        }
        if (StringUtils.isEmpty(flinkHome)) {
            //从默认配置文件中读取
            flinkHome = supportCoreConf.getStringVal(SupportConstants.KEY_FLINK_HOME);
        } else {
            if (StringUtils.isEmpty(supportCoreConf.getStringVal(SupportConstants.KEY_FLINK_HOME))) {
                supportCoreConf.setStringVal(SupportConstants.KEY_FLINK_HOME, flinkHome);
            }
        }
        option.setFlinkHome(flinkHome);
        LOGGER.info("FLINK_HOME path is : {}", flinkHome);
    }


    public void shutdown() {
        stop();
    }


    public void setSupportHomePath(GenericRunOption option) {
        String myHome = option.getMyHome();
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
        option.setMyHome(myHome);
        LOGGER.info(String.format("FLINK_SUPPORT_HOME is [%s]", myHome));
    }
}
