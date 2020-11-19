/*
 *      Copyright [2020] [xiaozhennan1995@gmail.com]
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 *      http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.weiwan.support.launcher.envs;

import com.weiwan.support.common.exception.SupportException;
import com.weiwan.support.common.options.OptionParser;
import com.weiwan.support.common.utils.*;
import com.weiwan.support.core.config.SupportCoreConf;
import com.weiwan.support.core.config.SupportETLConf;
import com.weiwan.support.core.config.SupportSqlConf;
import com.weiwan.support.core.constant.SupportConstants;
import com.weiwan.support.launcher.SupportAppClient;
import com.weiwan.support.launcher.enums.ResourceMode;
import com.weiwan.support.launcher.enums.RunMode;
import com.weiwan.support.launcher.envs.processer.EnvProcess;
import com.weiwan.support.launcher.options.GenericRunOption;
import com.weiwan.support.utils.cluster.ClusterConfigLoader;
import com.weiwan.support.utils.hadoop.HdfsUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.flink.configuration.GlobalConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

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

    protected String flinkConfDir;

    protected ResourceMode resourceMode;
    protected RunMode runMode;

    private boolean inited;

    public ApplicationEnv(String[] args) {
        this.args = args;
        this.optionParser = new OptionParser(this.args);
    }


    public boolean enter(RunMode runMode) {
        this.runMode = runMode;
        if (!inited) {
            try {
                //设置客户端日志级别
                LogUtil.useCommandLogLevel(genericRunOption.getLogLevel());

                //参数校验,子类重写两个方法
                try {
                    emptyParameterCheck(genericRunOption);
                    illegalParameterCheck(genericRunOption);
                } catch (SupportException exception) {
                    LOGGER.error("Parameter verification failed, please check the parameter settings", exception);
                    throw exception;
                }

                //SupportHome
                setSupportHomePath(genericRunOption);
                //SupportDefaultVariables
                setSupportDefault(genericRunOption);
                //Bigdata Env Variables
                setEnvironmentVariables(genericRunOption);
                //用户初始化
                this.init(genericRunOption);
                inited = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        if (!process()) {
            //处理失败
            return false;
        }
        //处理后进行一些特殊处理
        return true;
    }

    private void supportHdfsEnvCheck(GenericRunOption genericRunOption) {
    }

    private void setSupportDefault(GenericRunOption option) {
        String myHome = option.getMyHome();
        String coreConfFile = myHome + File.separator + "conf" + File.separator + SupportConstants.SUPPORT_CORE_CONF_FILE;
        String etlConfFile = myHome + File.separator + "conf" + File.separator + SupportConstants.SUPPORT_ETL_CONF_FILE;
        String sqlConfFile = myHome + File.separator + "conf" + File.separator + SupportConstants.SUPPORT_SQL_CONF_FILE;
        supportCoreConf = new SupportCoreConf(YamlUtils.getYamlByFileName(coreConfFile));
        supportETLConf = new SupportETLConf(YamlUtils.getYamlByFileName(etlConfFile));
        supportSqlConf = new SupportSqlConf(YamlUtils.getYamlByFileName(sqlConfFile));
    }

    private void setEnvironmentVariables(GenericRunOption option) throws IOException {
        //FlinkHome
        setFlinkDefault(option);
        //HadoopHome
        setHadoopDefault(option);

        setSystemEnvVariables(option);
    }

    private void setSystemEnvVariables(GenericRunOption option) {
        if (VariableCheckTool.checkNullOrEmpty(supportCoreConf.getStringVal(SupportConstants.HADOOP_USER_NAME))) {
            supportCoreConf.setVal(SupportConstants.HADOOP_USER_NAME, System.getProperty("USER"));
        }
        System.setProperty(SupportConstants.HADOOP_USER_NAME, supportCoreConf.getStringVal(SupportConstants.HADOOP_USER_NAME));
    }

    private void setHadoopDefault(GenericRunOption option) throws IOException {
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
        String confDir = hadoopHome + File.separator + SupportConstants.HADOOP_CONF_DIR;
        String coreSiteFile = hadoopHome + File.separator + SupportConstants.HADOOP_CONF_DIR + File.separator + SupportConstants.HADOOP_CONF_CORE_SITE;
        String hdfsSiteFile = hadoopHome + File.separator + SupportConstants.HADOOP_CONF_DIR + File.separator + SupportConstants.HADOOP_CONF_HDFS_SITE;
//        supportCoreConf.setStringVal(SupportConstants.KEY_LOCAL_HADOOP_CORE_SITE_CONF, FileUtil.readFileContent(coreSiteFile));
//        supportCoreConf.setStringVal(SupportConstants.KEY_LOCAL_HADOOP_HDFS_SITE_CONF, FileUtil.readFileContent(hdfsSiteFile));
        supportCoreConf.setVal(SupportConstants.KEY_HADOOP_CONFIGURATION, ClusterConfigLoader.loadHadoopConfig(confDir));
        supportCoreConf.setVal(SupportConstants.KEY_YARN_CONFIGURATION, ClusterConfigLoader.loadYarnConfig(confDir));
        LOGGER.info("HADOOP_HOME path is : {}", hadoopHome);
    }

    private void setFlinkDefault(GenericRunOption option) throws IOException {
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
        String confDir = flinkHome + File.separator + SupportConstants.FLINK_CONF_DIR;
        String confFile = flinkHome + File.separator + SupportConstants.FLINK_CONF_DIR + File.separator + SupportConstants.FLINK_CONF_FILE;
        this.flinkConfDir = confDir;
//        supportCoreConf.setStringVal(SupportConstants.KEY_LOCAL_FLINK_CONF, FileUtil.readFileContent(confFile));
        supportCoreConf.setVal(SupportConstants.KEY_FLINK_CONFIGURATION, GlobalConfiguration.loadConfiguration(confDir));
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
            LOGGER.warn("use the path of the startup class path as FLINK_SUPPORT_HOME");
            //获得当前启动类jar包得实际地址 $FLINK_SUPPORT_HOME/lib
            String appPath = CommonUtil.getAppPath(SupportAppClient.class);
            File file = new File(appPath);
            myHome = file.getParent();
        }
        option.setMyHome(myHome);
        LOGGER.info(String.format("FLINK_SUPPORT_HOME is [%s]", myHome));
    }
}
