package com.weiwan.support.launcher.envs.processer;

import com.weiwan.support.common.constant.Constans;
import com.weiwan.support.common.exception.SupportException;
import com.weiwan.support.common.utils.FileUtil;
import com.weiwan.support.core.constant.SupportConstants;
import com.weiwan.support.launcher.envs.ApplicationEnv;
import com.weiwan.support.launcher.options.GenericRunOption;
import com.weiwan.support.utils.hadoop.HadoopUtil;
import com.weiwan.support.utils.hadoop.HdfsUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

/**
 * @Author: xiaozhennan
 * @Date: 2020/11/19 16:34
 * @Package: com.weiwan.support.launcher.envs.processer.InitProcesser
 * @ClassName: InitProcesser
 * @Description:
 **/
public class InitProcesser extends ApplicationEnv {

    private static final Logger logger = LoggerFactory.getLogger(InitProcesser.class);

    public InitProcesser(String[] args) {
        super(args);
    }

    private FileSystem fileSystem;

    @Override
    public void init(GenericRunOption genericRunOption) throws IOException {
        logger.info("Start the initialization of the Flink Support program");
        fileSystem = HadoopUtil.getFileSystem((Configuration) supportCoreConf.getVal(SupportConstants.KEY_HADOOP_CONFIGURATION));

        logger.info("Start to create a working directory for Flink Support on HDFS");
        //检查远程目录是否存在
        if (!HdfsUtil.existsDir(fileSystem, new Path(SupportConstants.SUPPORT_HDFS_WORKSPACE))) {
            logger.warn("The FlinkSupport workspace directory does not exist");
            HdfsUtil.mkdir(fileSystem, new Path(SupportConstants.SUPPORT_HDFS_WORKSPACE), true);
            logger.info("Create a Flink Support working directory in [HDFS] : {}", SupportConstants.SUPPORT_HDFS_WORKSPACE);
        }

        //创建 lib flinks conf plugins
        String[] dirs = new String[]{"lib", "flinks", "conf", "plugins"};
        for (int i = 0; i < dirs.length; i++) {
            Path remotePath = new Path(SupportConstants.SUPPORT_HDFS_WORKSPACE + Constans.SIGN_SLASH + dirs[i]);
            logger.info("create a remote directory on hdfs: {}", remotePath.toUri());
            HdfsUtil.mkdir(fileSystem, remotePath, true);
        }

        logger.info("Create Flink Support working directory Successed on HDFS");
    }

    @Override
    public boolean process() {

        try { //上传
            String supportHome = supportCoreConf.getStringVal(SupportConstants.KEY_SUPPORT_HOME);
            String _localLibDir = supportHome + File.separator + SupportConstants.SUPPORT_LOCAL_LIB_DIR;
            File localLibDir = new File(_localLibDir);
            if (localLibDir.exists() && localLibDir.isDirectory()) {
                //获取下边所有的jar, 然后做过滤 , 然后上传
                Path remoteLibDir = new Path(SupportConstants.SUPPORT_HDFS_LIB_DIR);
                HdfsUtil.uploadFiles(fileSystem, localLibDir.getAbsolutePath(), remoteLibDir);
                logger.info("Upload Flink Support local dependencies to remote Support Lib directory: {}", remoteLibDir.toUri());
            }


            String _localConfDir = supportHome + File.separator + SupportConstants.SUPPORT_LOCAL_CONF_DIR;
            File localConfDir = new File(_localConfDir);
            if (localConfDir.exists() && localConfDir.isDirectory()) {
                //获取下边所有的jar, 然后做过滤 , 然后上传
                Path remoteConfDir = new Path(SupportConstants.SUPPORT_HDFS_CONF_DIR);
                HdfsUtil.uploadFiles(fileSystem, localConfDir.getAbsolutePath(), remoteConfDir);
                logger.info("Upload Flink Support local configuration directory to remote Support Conf directory: {}", remoteConfDir.toUri());
            }

            String _localPluginsDir = supportHome + File.separator + SupportConstants.SUPPORT_LOCAL_PLUGINS_DIR;
            File localPluginsDir = new File(_localPluginsDir);
            if (localPluginsDir.exists() && localPluginsDir.isDirectory()) {
                //获取下边所有的jar, 然后做过滤 , 然后上传
                Path remotePluginsDir = new Path(SupportConstants.SUPPORT_HDFS_CONF_DIR);
                HdfsUtil.uploadFiles(fileSystem, localPluginsDir.getAbsolutePath(), remotePluginsDir);
                logger.info("Upload Flink Support local plugin directory to remote Support Plugins directory: {}", remotePluginsDir.toUri());
            }


            //上传FlinkDist 以及Flink Plugins
            String flinkHome = supportCoreConf.getStringVal(SupportConstants.KEY_FLINK_HOME);
            String flinkVerson = flinkHome.substring(flinkHome.indexOf("-") + 1, flinkHome.length());
            String flinkRemoteHome = SupportConstants.FLINK_HDFS_HOME.replace(SupportConstants.FLINK_VERSION_PLACEHOLDER, flinkVerson);
            String _localFlinkLibDir = flinkHome + File.separator + "lib";
            File localFlinkLibDir = new File(_localFlinkLibDir);
            if (localFlinkLibDir.exists() && localFlinkLibDir.isDirectory()) {
                //获取下边所有的jar, 然后做过滤 , 然后上传
                Path remoteFlinkLibPath = new Path(flinkRemoteHome + Constans.SIGN_SLASH + "lib");
                if (!HdfsUtil.existsDir(fileSystem, remoteFlinkLibPath)) {
                    HdfsUtil.mkdir(fileSystem, remoteFlinkLibPath, true);
                }
                HdfsUtil.uploadFiles(fileSystem, localFlinkLibDir.getAbsolutePath(), remoteFlinkLibPath);
            } else {
                throw new SupportException("Please check the lib directory in FLINK_HOME!");
            }

            //上传FlinkDist 以及Flink Plugins
            String _localFlinkPluginsDir = flinkHome + File.separator + "plugins";
            File localFlinkPluginsDir = new File(_localFlinkPluginsDir);
            if (localFlinkPluginsDir.exists() && localFlinkPluginsDir.isDirectory()) {
                //获取下边所有的jar, 然后做过滤 , 然后上传
                Path remoteFlinkPLuginsPath = new Path(flinkRemoteHome + Constans.SIGN_SLASH + "plugins");
                if (!HdfsUtil.existsDir(fileSystem, remoteFlinkPLuginsPath)) {
                    HdfsUtil.mkdir(fileSystem, remoteFlinkPLuginsPath, true);
                }
                HdfsUtil.uploadFiles(fileSystem, localFlinkPluginsDir.getAbsolutePath(), remoteFlinkPLuginsPath);
            } else {
                throw new SupportException("Please check the plugins directory in FLINK_HOME!");
            }
            logger.info("The following is the result of initialization:");
            logger.info("=======================Flink Support=======================");
            logger.info("SUPPORT_HDFS_WORKSPACE:{}", SupportConstants.SUPPORT_HDFS_WORKSPACE);
            logger.info("SUPPORT_HDFS_LIB:{}", SupportConstants.SUPPORT_HDFS_LIB_DIR);
            logger.info("SUPPORT_HDFS_CONF:{}", SupportConstants.SUPPORT_HDFS_CONF_DIR);
            logger.info("SUPPORT_HDFS_PLUGINS:{}", SupportConstants.SUPPORT_HDFS_PLUGINS_DIR);
            logger.info("=======================Apache Flink=========================");
            logger.info("FLINK_HDFS_HOME:{}", flinkRemoteHome);
            logger.info("FLINK_VERSION:{}", "Apache Flink " + flinkVerson);
            logger.info("Flink Support init Successed!");
        } catch (IOException e) {
            e.printStackTrace();
        }
        //是否覆盖

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
        String flinkHomeDir = supportCoreConf.getStringVal(SupportConstants.KEY_FLINK_HOME);
        if (StringUtils.isEmpty(flinkHomeDir) && FileUtil.existsDir(flinkHomeDir)) {
            throw new SupportException("Please check if the FLINK_HOME environment variable exists");
        }

        String hadoopHomeDir = supportCoreConf.getStringVal(SupportConstants.KEY_HADOOP_HOME);
        if (StringUtils.isEmpty(hadoopHomeDir) && FileUtil.existsDir(hadoopHomeDir)) {
            throw new SupportException("Please check if the HADOOP_HOME environment variable exists");
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

    }

    @Override
    public void stop() {

    }
}
