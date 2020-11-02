package com.weiwan.support.launcher.cluster;

import org.apache.commons.lang3.StringUtils;
import org.apache.flink.client.deployment.ClusterDeploymentException;
import org.apache.flink.client.deployment.application.ApplicationConfiguration;
import org.apache.flink.client.program.ClusterClient;
import org.apache.flink.client.program.ClusterClientProvider;
import org.apache.flink.configuration.CheckpointingOptions;
import org.apache.flink.configuration.DeploymentOptions;
import org.apache.flink.configuration.PipelineOptions;
import org.apache.flink.runtime.jobgraph.SavepointConfigOptions;
import org.apache.flink.yarn.YarnClientYarnClusterInformationRetriever;
import org.apache.flink.yarn.YarnClusterDescriptor;
import org.apache.flink.yarn.YarnClusterInformationRetriever;
import org.apache.flink.yarn.configuration.YarnConfigOptions;
import org.apache.flink.yarn.configuration.YarnDeploymentTarget;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.yarn.api.records.ApplicationId;
import org.apache.hadoop.yarn.client.api.YarnClient;

import java.util.Collections;

/**
 * @Author: xiaozhennan
 * @Date: 2020/11/2 15:23
 * @Package: com.weiwan.support.launcher.cluster.YarnJobSubmiter
 * @ClassName: YarnJobSubmiter
 * @Description:
 **/
public class YarnJobSubmiter implements JobSubmiter {

    private YarnClient yarnClient;

    public YarnJobSubmiter(YarnClient yarnClient) {
        this.yarnClient = yarnClient;
    }

    @Override
    public Object submitJob(JobSubmitInfo jobInfo) {
        YarnClusterInformationRetriever informationRetriever = YarnClientYarnClusterInformationRetriever.create(yarnClient);
        org.apache.flink.configuration.Configuration flinkConfiguration = jobInfo.getFlinkConfiguration();

        //checkpoint 恢复
        if (StringUtils.isNotEmpty(jobInfo.getSavePointPath())) {
            flinkConfiguration.set(
                    SavepointConfigOptions.SAVEPOINT_PATH,
                    jobInfo.getSavePointPath());
        }

        flinkConfiguration.set(
                CheckpointingOptions.INCREMENTAL_CHECKPOINTS,
                true);
        //核心jar包
        flinkConfiguration.set(
                PipelineOptions.JARS,
                jobInfo.getUserJars());

        flinkConfiguration.set(
                PipelineOptions.CLASSPATHS,
                jobInfo.getUserClasspath());

        flinkConfiguration.set(
                YarnConfigOptions.PROVIDED_LIB_DIRS,
                jobInfo.getFlinkLibs());

        flinkConfiguration.set(
                YarnConfigOptions.FLINK_DIST_JAR,
                jobInfo.getFlinkDistJar());
        //设置为application模式
        flinkConfiguration.set(
                DeploymentOptions.TARGET,
                YarnDeploymentTarget.APPLICATION.getName());
        //yarn application name
        flinkConfiguration.set(
                YarnConfigOptions.APPLICATION_NAME,
                jobInfo.getAppName());

        flinkConfiguration.set(
                YarnConfigOptions.APPLICATION_TYPE,
                jobInfo.getAppType());

        if (StringUtils.isNotEmpty(jobInfo.getYarnQueue())) {
            flinkConfiguration.set(
                    YarnConfigOptions.APPLICATION_QUEUE,
                    jobInfo.getYarnQueue());
        }

        //		设置用户jar的参数和主类
        ApplicationConfiguration appConfig = new ApplicationConfiguration(jobInfo.getAppArgs(), jobInfo.getAppClassName());

        YarnClusterDescriptor yarnClusterDescriptor = new YarnClusterDescriptor(
                flinkConfiguration,
                jobInfo.getYarnConfiguration(),
                yarnClient,
                informationRetriever,
                true);
        ClusterClientProvider<ApplicationId> clusterClientProvider = null;

        try {
            clusterClientProvider = yarnClusterDescriptor.deployApplicationCluster(jobInfo.getClusterSpecification(), appConfig);
        } catch (ClusterDeploymentException e) {
            e.printStackTrace();
        }

        ClusterClient<ApplicationId> clusterClient = clusterClientProvider.getClusterClient();
        ApplicationId applicationId = clusterClient.getClusterId();
        System.out.println(applicationId);
        return applicationId;
    }

    @Override
    public Object stopJob(JobSubmitInfo jobInfo) {
        return null;
    }

    @Override
    public Object getClusterClientForAppId(JobSubmitInfo jobInfo) {
        return null;
    }
}
