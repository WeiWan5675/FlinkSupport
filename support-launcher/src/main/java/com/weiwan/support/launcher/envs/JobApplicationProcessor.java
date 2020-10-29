package com.weiwan.support.launcher.envs;

import com.weiwan.support.common.constant.Constans;
import com.weiwan.support.common.exception.SupportException;
import com.weiwan.support.common.options.OptionParser;
import com.weiwan.support.common.utils.CommonUtil;
import com.weiwan.support.common.utils.FileUtil;
import com.weiwan.support.common.utils.VariableCheckTool;
import com.weiwan.support.core.api.AppType;
import com.weiwan.support.core.constant.SupportConstants;
import com.weiwan.support.launcher.enums.ResourceMode;
import com.weiwan.support.launcher.enums.RunMode;
import com.weiwan.support.launcher.options.GenericRunOption;
import com.weiwan.support.launcher.options.JobRunOption;
import com.weiwan.support.utils.hadoop.HadoopUtil;
import org.apache.flink.configuration.ConfigurationUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hdfs.client.HdfsUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author: xiaozhennan
 * @Date: 2020/9/30 10:46
 * @Package: com.weiwan.support.launcher.envs.JobApplicationProcessor
 * @ClassName: JobApplicationProcessor
 * @Description:
 **/
public class JobApplicationProcessor extends ApplicationEnv {

    private static final Logger logger = LoggerFactory.getLogger(JobApplicationProcessor.class);

    private JobRunOption option;


    private FileSystem fileSystem;

    private String flinkLibDir;
    private String flinkDistJar;
    private String userResourceDir;
    private String applicationName;

    private AppType appType;


    public JobApplicationProcessor(String[] args) {
        super(args);
        super.genericRunOption = optionParser.parse(JobRunOption.class);
    }


    @Override
    public void init(GenericRunOption genericRunOption) throws IOException {
        this.option = (JobRunOption) genericRunOption;
        this.fileSystem = HadoopUtil.getFileSystem((Configuration) supportCoreConf.getVal(SupportConstants.KEY_HADOOP_CONFIGURATION));
        //获取远程flinkHome,
        String flink_version = supportCoreConf.getStringVal(SupportConstants.FLINK_VERSION);
        String scala_version = supportCoreConf.getStringVal(SupportConstants.SCALA_VERSION);

        String flinkHdfsHome = SupportConstants.FLINK_HDFS_HOME.replace(SupportConstants.FLINK_VERSION_PLACEHOLDER, flink_version);
        flinkLibDir = flinkHdfsHome + Constans.SIGN_SLASH + SupportConstants.FLINK_LIB_DIR;
        //获取该文件夹下所有的文件,排除dist
        flinkDistJar = flinkLibDir + Constans.SIGN_SLASH + SupportConstants.FLINK_DIST_JAR
                .replace(SupportConstants.SCALA_VERSION_PLACEHOLDER, scala_version)
                .replace(SupportConstants.FLINK_VERSION_PLACEHOLDER, flink_version);

        //解析用户配置文件
        String jobConfPath = option.getJobConf();
        //远程配置文件,设置相关的启动参数,设置相关变量
        if (ResourceMode.HDFS == resourceMode) {
            //远程配置文件,设置相关的启动参数,设置相关变量
            userResourceDir = option.getJobConf().substring(0, jobConfPath.lastIndexOf(File.separator));
            //判断远程资源目录是否存在
            Path resourcePath = new Path(userResourceDir);


        } else if (ResourceMode.LOCAL == resourceMode) {
            String resourcesDir = option.getResources();

            //如果资源目录参数为空或者资源目录不存在,抛出异常
            if (!VariableCheckTool.checkNullOrEmpty(option.getResources()) || !FileUtil.existsDir(option.getResources())) {
                logger.error("job resources parameter is empty");
                throw SupportException.generateParameterEmptyException("In local mode, you need to use [--resources] to specify the resource path");
            }

            //生成Hdfs上得资源目录ID


            //本地配置文件,上传本地资源
            /**
             * 1. 读取本地配置文件
             * 2. 需要传递到任务里的参数
             *  1. hadoopConf
             *  2. flinkConf
             *  3. jobName
             *  4. 资源池队列
             *  5. 传递默认配置文件(在任务job graph 生成阶段, 合并  default + user)
             *  6. 任务准备完成后,提交任务
             */
        }

    }

    @Override
    public boolean process() {
        logger.info("The Job application handler starts and starts processing the job submission work!");
        String appName = option.getAppName();
        logger.info("job Name is : {}", appName);


        String jobConf = option.getJobConf();
        String userResourcesDir = jobConf.substring(0, jobConf.lastIndexOf(File.separator));
        StringBuffer userJars = new StringBuffer();
        StringBuffer userClassPath = new StringBuffer();

        try {
            Path userResourceDir = new Path(new URI(userResourcesDir));
            FileStatus[] fileStatuses = fileSystem.listStatus(userResourceDir);
            for (FileStatus fileStatus : fileStatuses) {
                userJars.append(fileStatus.getPath().toUri().toURL().toString());
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //读取job配置文件,以及配置文件所在文件夹所有内容

        /**
         * hdfs://flink_support_space/
         * /lib
         * /plugins
         * /resources
         * /flink/flink-1.1.1
         */
        /**
         * 1. 读取配置文件
         * 2. 读取配置文件文件夹下所有内容
         * 3. 设置启动参数
         */

        logger.info("The job handler is processed and the job has been submitted!");
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
        JobRunOption jobRunOption = (JobRunOption) genericRunOption;
        if (VariableCheckTool.checkNullOrEmpty(jobRunOption.getJobConf())) {
            throw new SupportException("The job configuration file cannot be empty, please specify the configuration file!");
        }

    }

    /**
     * 检查通过返回true
     *
     * @param genericRunOption
     * @return
     */
    @Override
    public void illegalParameterCheck(GenericRunOption genericRunOption) {
        JobRunOption jobRunOption = (JobRunOption) genericRunOption;

        //资源地址校验
        if (!jobRunOption.getJobConf().startsWith("hdfs:")) {
            File file = new File(jobRunOption.getJobConf());
            if (!file.exists() && !file.isFile()) {
                //配置文件在本地,但是本地配置文件未找到,抛出异常
                throw new SupportException("The configuration file does not exist, please check the path");
            } else if (VariableCheckTool.checkNullOrEmpty(jobRunOption.getResources())) {
                //是本地模式 TODO 此处可以默认配置文件目录为本地资源目录 ,但是没有指定资源地址,抛出异常
                throw new SupportException("No resource path specified, please use [--resources] to specify local resources");
            }
            resourceMode = ResourceMode.LOCAL;
        } else if (jobRunOption.getJobConf().startsWith("hdfs:")) {
            resourceMode = ResourceMode.HDFS;
        } else {
            throw new SupportException("Unsupported profile protocol!");
        }


    }


    @Override
    public void stop() {

    }

}
