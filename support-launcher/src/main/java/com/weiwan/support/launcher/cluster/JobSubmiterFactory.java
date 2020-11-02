package com.weiwan.support.launcher.cluster;

import org.apache.commons.lang3.StringUtils;
import org.apache.flink.client.deployment.ClusterDeploymentException;
import org.apache.flink.client.deployment.application.ApplicationConfiguration;
import org.apache.flink.client.deployment.executors.RemoteExecutor;
import org.apache.flink.client.program.ClusterClient;
import org.apache.flink.client.program.ClusterClientProvider;
import org.apache.flink.configuration.*;
import org.apache.flink.runtime.jobgraph.SavepointConfigOptions;
import org.apache.flink.yarn.YarnClientYarnClusterInformationRetriever;
import org.apache.flink.yarn.YarnClusterDescriptor;
import org.apache.flink.yarn.YarnClusterInformationRetriever;
import org.apache.flink.yarn.configuration.YarnConfigOptions;
import org.apache.flink.yarn.configuration.YarnDeploymentTarget;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.yarn.api.records.ApplicationId;
import org.apache.hadoop.yarn.client.api.YarnClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;


/**
 * @Author: xiaozhennan
 * @Date: 2020/10/27 11:14
 * @Package: com.weiwan.support.launcher.cluster.JobSubmiter
 * @ClassName: JobSubmiter
 * @Description:
 **/
public class JobSubmiterFactory {
    private static Logger logger = LoggerFactory.getLogger(JobSubmiterFactory.class);

    public static JobSubmiter createYarnSubmiter(YarnClient yarnClient) {
        return new YarnJobSubmiter(yarnClient);
    }

    public static JobSubmiter createKubernetesSubmiter() {
        return new KubernetesJobSubmiter();
    }

}
