package com.weiwan.support.launcher.envs;

import com.alibaba.fastjson.JSONObject;
import com.weiwan.support.common.constant.Constans;
import com.weiwan.support.common.exception.SupportException;
import com.weiwan.support.common.options.OptionParser;
import com.weiwan.support.common.utils.*;
import com.weiwan.support.common.utils.FileUtil;
import com.weiwan.support.core.constant.SupportConstants;
import com.weiwan.support.core.constant.SupportKey;
import com.weiwan.support.core.start.RunOptions;
import com.weiwan.support.launcher.cluster.ClusterJobUtil;
import com.weiwan.support.launcher.cluster.JobSubmitInfo;
import com.weiwan.support.launcher.cluster.JobSubmiter;
import com.weiwan.support.launcher.cluster.JobSubmiterFactory;
import com.weiwan.support.launcher.enums.JobType;
import com.weiwan.support.launcher.enums.ResourceMode;
import com.weiwan.support.launcher.options.GenericRunOption;
import com.weiwan.support.launcher.options.JobRunOption;
import com.weiwan.support.utils.flink.conf.FlinkContains;
import com.weiwan.support.utils.hadoop.HadoopUtil;
import com.weiwan.support.utils.hadoop.HdfsUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.yarn.conf.YarnConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;

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
    private String userResourceRemoteDir;
    private String applicationName;

    private UserJobConf userJobConf;

    private Configuration hadoopConfiguration;
    private org.apache.flink.configuration.Configuration flinkConfiguration;
    private YarnConfiguration yarnConfiguration;


    public JobApplicationProcessor(String[] args) {
        super(args);
        super.genericRunOption = optionParser.parse(JobRunOption.class);
    }


    @Override
    public void init(GenericRunOption genericRunOption) throws IOException {
        this.option = (JobRunOption) genericRunOption;
        this.hadoopConfiguration = (Configuration) supportCoreConf.getVal(SupportConstants.KEY_HADOOP_CONFIGURATION);
        this.flinkConfiguration = (org.apache.flink.configuration.Configuration) supportCoreConf.getVal(SupportConstants.KEY_FLINK_CONFIGURATION);
        this.yarnConfiguration = (YarnConfiguration) supportCoreConf.getVal(SupportConstants.KEY_YARN_CONFIGURATION);
        this.fileSystem = HadoopUtil.getFileSystem(hadoopConfiguration);
        //解析用户配置文件
        String jobConfPath = option.getJobConf();
        //读取用户配置文件
        this.userJobConf = readUserJobConf(jobConfPath);
        //获取远程flinkHome,
        String flink_version = supportCoreConf.getStringVal(SupportConstants.FLINK_VERSION);
        String scala_version = supportCoreConf.getStringVal(SupportConstants.SCALA_VERSION);

        String flinkHdfsHome = SupportConstants.FLINK_HDFS_HOME.replace(SupportConstants.FLINK_VERSION_PLACEHOLDER, flink_version);

        flinkLibDir = flinkHdfsHome + Constans.SIGN_SLASH + SupportConstants.FLINK_LIB_DIR;
        //获取该文件夹下所有的文件,排除dist
        flinkDistJar = flinkLibDir + Constans.SIGN_SLASH + SupportConstants.FLINK_DIST_JAR
                .replace(SupportConstants.SCALA_VERSION_PLACEHOLDER, scala_version)
                .replace(SupportConstants.FLINK_VERSION_PLACEHOLDER, flink_version);

        //远程配置文件,设置相关的启动参数,设置相关变量
        if (ResourceMode.HDFS == resourceMode) {
            //远程配置文件,设置相关的启动参数,设置相关变量
            userResourceRemoteDir = option.getJobConf().substring(0, jobConfPath.lastIndexOf(File.separator)).replace("hdfs:", ((Configuration) supportCoreConf.getVal(SupportConstants.KEY_HADOOP_CONFIGURATION)).get(HadoopUtil.KEY_HA_DEFAULT_FS));
            ;
            //判断远程资源目录是否存在
            Path resourcePath = new Path(userResourceRemoteDir);

            if (!checkRemoteResourceDirExists(userResourceRemoteDir) || checkRemoteResourceDirEmpty(userResourceRemoteDir)) {
                //远程资源目录为空,需要处理
                throw new SupportException(String.format("The remote resource directory %s is empty, please check", resourcePath.toString()));
            }

        } else if (ResourceMode.LOCAL == resourceMode) {
            String resourcesDir = option.getResources();

            //如果资源目录参数为空或者资源目录不存在,抛出异常
            if (VariableCheckTool.checkNullOrEmpty(option.getResources()) || !FileUtil.existsDir(option.getResources())) {
                logger.error("job resources parameter is empty");
                throw SupportException.generateParameterEmptyException("In local mode, you need to use [--resources] to specify the resource path");
            }

            //目录下所有资源的名称拼接后按字母顺序排序后取MD5(就是Job的ID)
            //support_xxxxxxxxxxxxxxxxx_job  资源文件夹下,所有的资源名称排序后md5视作一个整体,如果资源和appName都相同,就相当于是一个应用
            userResourceRemoteDir = generateJobResourcesDir(resourcesDir);
            ;
            //hdfs://flink_support_space/resources/support_${jobName}_${jobResourcesMD5}_job
            if (!checkRemoteResourceDirExists(userResourceRemoteDir) || checkRemoteResourceDirEmpty(userResourceRemoteDir)) {
                //上传
                uploadUserResources(resourcesDir, userResourceRemoteDir, true);
            } else {
                //判断是否需要重新上传,需要就重新上传
                if (option.isOverwriteResource()) {
                    uploadUserResources(resourcesDir, userResourceRemoteDir, true);
                }
            }
        }

        //获取App名称
        applicationName = option.getAppName();
        if (StringUtils.isEmpty(applicationName)) {
            applicationName = userJobConf.getStringVal(SupportKey.APP_NAME);
            if (StringUtils.isEmpty(applicationName)) {
                applicationName = supportCoreConf.getStringVal(SupportKey.APP_NAME);
            }
        }

    }

    private void uploadUserResources(String resourcesDir, String userResourceRemoteDir, boolean overwrite) throws IOException {
        Path remotePath = new Path(userResourceRemoteDir);
        if (overwrite) {
            HdfsUtil.drop(fileSystem, remotePath, true);
        }
        HdfsUtil.uploadFiles(fileSystem, resourcesDir, remotePath);
    }

    private UserJobConf readUserJobConf(String jobConfPath) throws IOException {
        UserJobConf userJobConf = new UserJobConf();
        String configContent = null;
        if (resourceMode == ResourceMode.LOCAL) {
            //本地读取
            configContent = FileUtil.readFileContent(jobConfPath);
        }
        if (resourceMode == ResourceMode.HDFS) {
            configContent = HdfsUtil.readFileContent(fileSystem, new Path(jobConfPath));
        }
        Map<String, String> userVarMap = YamlUtils.loadYamlStr(configContent);
        userJobConf.addAll(userVarMap);
        return userJobConf;
    }

    private boolean checkRemoteResourceDirExists(String userResourceDir) {
        if (StringUtils.isNotEmpty(userResourceDir)) {
            if (userResourceDir.startsWith(Constans.PROTOCOL_HDFS)) {
                //是合法路径
                Path path = new Path(userResourceDir);
                return HdfsUtil.existsDir(fileSystem, path);
            }
        }
        return false;
    }


    private boolean checkRemoteResourceDirEmpty(String userResourceDir) {
        if (StringUtils.isNotEmpty(userResourceDir)) {
            if (userResourceDir.startsWith(Constans.PROTOCOL_HDFS)) {
                //是合法路径
                Path path = new Path(userResourceDir);
                return HdfsUtil.dirIsEmpty(fileSystem, path);
            }
        }
        return true;
    }

    private String generateJobResourcesDir(String resourcesDir) {
        File file = new File(resourcesDir);
        File[] files = file.listFiles();
        if (files == null || files.length < 1) {
            throw SupportException.generateParameterEmptyException("the resource directory is empty please check");
        }
        StringBuffer sb = new StringBuffer();
        for (File subFile : files) {
            sb.append(subFile.getName());
        }
        String sortJobKey = StringUtil.sortStrByDict(sb.toString());
        return SupportConstants.JOB_RESOURCES_DIR
                .replace(SupportConstants.JOB_NAME_PLACEHOLDER,
                        userJobConf.getStringVal(FlinkContains.FLINK_TASK_NAME,
                                supportCoreConf.getStringVal(FlinkContains.FLINK_TASK_NAME)))
                .replace(SupportConstants.JOB_RESOURCES_MD5_KEY_PLACEHOLDER, MD5Utils.md5(sortJobKey));
    }

    @Override
    public boolean process() {
        logger.info("The Job application handler starts and starts processing the job submission work!");


        //准备提交任务
        /**
         * 1. 准备依赖
         * 2. 准备参数
         * 3.
         */

        RunOptions runOptions = convertCmdToRunOption(option);


        //获取job类型
        JobType jobType = JobType.getType(userJobConf.getStringVal(SupportKey.APP_TYPE, "stream").toUpperCase());
        if (JobType.BATCH == jobType) {
            runOptions.setBatch(true);
        } else {
            runOptions.setStream(true);
            //处理流任务的其它状态
            if (userJobConf.getBooleanVal(SupportKey.ETL_MODE, false)) {
                runOptions.setEtl(true);
                //TODO 这里要兼容其它模式
            } else if (userJobConf.getBooleanVal(SupportKey.SQL_MODE, false)) {
                //sql模式
                runOptions.setTable(true);
            } else {
                //普通模式
            }
        }

        //JobDescJson
        runOptions.setJobDescJson(Base64Util.encode(JSONObject.toJSONString(mergeConfigToAll())));
        String queueName = option.getQueueName();
        if (StringUtils.isEmpty(queueName)) {
            String userQueue = userJobConf.getStringVal(FlinkContains.FLINK_TASK_COMMON_QUEUE_KEY);
            queueName = StringUtils.isNotEmpty(userQueue) ? userQueue : supportCoreConf.getStringVal(FlinkContains.FLINK_TASK_COMMON_QUEUE_KEY);
        }


        String[] args = null;
        try {
            args = OptionParser.optionToArgs(runOptions);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //coreJar
        String coreJar = "hdfs://nameservice1/flink_support_space/lib/support-runtime-1.0.jar";

        Set<String> flinkClassPaths = new HashSet<>();
        flinkClassPaths.add(flinkLibDir);
        flinkClassPaths.add(SupportConstants.SUPPORT_HDFS_LIB_DIR);
        flinkClassPaths.add(userResourceRemoteDir);
        URL.setURLStreamHandlerFactory(new FsUrlStreamHandlerFactory());
        System.out.println("classpath:" + flinkClassPaths.toString());
        //组装了任务信息
        JobSubmitInfo submitInfo = JobSubmitInfo.newBuilder().appArgs(args)
                .appClassName(SupportConstants.SUPPORT_ENTER_CLASSNAME)
                .appName(applicationName)
                .appType(jobType.getType())
                .clusterSpecification(ClusterJobUtil.createClusterSpecification(option.getParams()))
                .flinkConfiguration(flinkConfiguration)
                .hadoopConfiguration(hadoopConfiguration)
                .yarnConfiguration(yarnConfiguration)
                .yarnQueue(queueName)
                .flinkDistJar(flinkDistJar)
                .flinkLibs(Collections.singletonList(flinkLibDir))
                .savePointPath(option.getSavePointPath())
                .userJars(Collections.singletonList(coreJar))
                .userClasspath(new ArrayList<>(flinkClassPaths))
                .build();

        StringBuffer sb = new StringBuffer();

        for (String arg : args) {
            sb.append(arg);
            sb.append("{}{}");
        }

        logger.info("启动参数: {}", sb.toString());
        JobSubmiter submiter = JobSubmiterFactory.createYarnSubmiter(ClusterJobUtil.getYarnClient(yarnConfiguration));
        submiter.submitJob(submitInfo);


//        try {
//            Path userResourceDir = new Path(new URI(userResourcesDir));
//            FileStatus[] fileStatuses = fileSystem.listStatus(userResourceDir);
//            for (FileStatus fileStatus : fileStatuses) {
//                userJars.append(fileStatus.getPath().toUri().toURL().toString());
//            }
//        } catch (URISyntaxException e) {
//            e.printStackTrace();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
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

    private Map<String, Object> mergeConfigToAll() {
        Map<String, Object> all = supportCoreConf.getAll();
        all.putAll(supportETLConf.getAll());
        all.putAll(supportSqlConf.getAll());
        all.putAll(userJobConf.getAll());
        return all;
    }

    private RunOptions convertCmdToRunOption(JobRunOption option) {
        RunOptions runOptions = new RunOptions();
        runOptions.setLogLevel(option.getLogLevel());
        runOptions.setParams(option.getParams());
        return runOptions;
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
                throw new SupportException("No resource path specified, please use [-resources] to specify local resources");
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
