package com.weiwan.support.launcher.cluster;

import com.weiwan.support.core.config.SupportCoreConf;
import org.apache.flink.configuration.CheckpointingOptions;
import org.apache.flink.configuration.DeploymentOptions;
import org.apache.flink.configuration.GlobalConfiguration;
import org.apache.flink.configuration.PipelineOptions;
import org.apache.flink.yarn.YarnClientYarnClusterInformationRetriever;
import org.apache.flink.yarn.YarnClusterInformationRetriever;
import org.apache.flink.yarn.configuration.YarnConfigOptions;
import org.apache.flink.yarn.configuration.YarnDeploymentTarget;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.yarn.client.api.YarnClient;
import org.apache.hadoop.yarn.conf.YarnConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Collections;

/**
 * @Author: xiaozhennan
 * @Date: 2020/10/27 11:14
 * @Package: com.weiwan.support.launcher.cluster.JobSubmiter
 * @ClassName: JobSubmiter
 * @Description:
 **/
public class JobSubmiter {

    private static Configuration conf;
    private static Logger logger = LoggerFactory.getLogger(JobSubmiter.class);
    private static JobSubmiter submiter;
    private static YarnConfiguration yarnConfiguration;
    private static Configuration hadoopConfiguration;
    private static org.apache.flink.configuration.Configuration flinkConfiguration;
    private YarnClient yarnClient;


    private JobSubmiter() {
    }

    public static JobSubmiter createSubmiter(YarnClient yarnClient) {
        if (submiter == null) {
            synchronized (JobSubmiter.class) {
                if (submiter == null) {
                    submiter = new JobSubmiter();
                    submiter.init(yarnClient);
                }
            }
        }
        return submiter;
    }


    public void init(YarnClient yarnClient) {
        this.yarnClient = yarnClient;
    }


    public void submitJob(JobSubmitInfo jobInfo) {
        YarnClusterInformationRetriever informationRetriever = YarnClientYarnClusterInformationRetriever.create(yarnClient);

        org.apache.flink.configuration.Configuration flinkConfiguration = jobInfo.getFlinkConfiguration();

//        flinkConfiguration.set(CheckpointingOptions.INCREMENTAL_CHECKPOINTS, true);
//        flinkConfiguration.set(
//                PipelineOptions.JARS,
//                Collections.singletonList(
//                        userJarPath));
//
//        Path remoteLib = new Path(flinkLibs);
//        JobSubmiter.flinkConfiguration.set(
//                YarnConfigOptions.PROVIDED_LIB_DIRS,
//                Collections.singletonList(remoteLib.toString()));
//
//        JobSubmiter.flinkConfiguration.set(
//                YarnConfigOptions.FLINK_DIST_JAR,
//                flinkDistJar);
//        //设置为application模式
//        JobSubmiter.flinkConfiguration.set(
//                DeploymentOptions.TARGET,
//                YarnDeploymentTarget.APPLICATION.getName());
//        //yarn application name
//        JobSubmiter.flinkConfiguration.set(YarnConfigOptions.APPLICATION_NAME, "jobName");

    }


    public void submitJobOnYarnPre() {

    }


    public void submitJobOnYarnSession() {

    }
}
