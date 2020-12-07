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
package com.weiwan.support.launcher.submit;

import com.weiwan.support.core.constant.SupportConstants;
import com.weiwan.support.core.constant.SupportKey;
import com.weiwan.support.launcher.cluster.ClusterJobUtil;
import com.weiwan.support.launcher.envs.JOBOptions;
import com.weiwan.support.launcher.envs.JVMOptions;
import org.apache.commons.lang3.StringUtils;
import org.apache.flink.client.deployment.ClusterDeploymentException;
import org.apache.flink.client.deployment.application.ApplicationConfiguration;
import org.apache.flink.client.program.ClusterClient;
import org.apache.flink.client.program.ClusterClientProvider;
import org.apache.flink.configuration.PipelineOptionsInternal;
import org.apache.flink.yarn.YarnClientYarnClusterInformationRetriever;
import org.apache.flink.yarn.YarnClusterDescriptor;
import org.apache.flink.yarn.YarnClusterInformationRetriever;
import org.apache.flink.yarn.configuration.YarnDeploymentTarget;
import org.apache.hadoop.yarn.api.records.ApplicationId;
import org.apache.hadoop.yarn.client.api.YarnClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: xiaozhennan
 * @Date: 2020/11/2 15:23
 * @Package: com.weiwan.support.launcher.submit.YarnJobSubmiter
 * @ClassName: YarnJobSubmiter
 * @Description:
 **/
public class YarnJobSubmiter implements JobSubmiter {

    private static final Logger logger = LoggerFactory.getLogger(YarnJobSubmiter.class);

    private YarnClient yarnClient;

    public YarnJobSubmiter(YarnClient yarnClient) {
        this.yarnClient = yarnClient;
    }

    @Override
    public Object submitJob(JobSubmitInfo jobInfo) {

        org.apache.flink.configuration.Configuration flinkConfiguration = jobInfo.getFlinkConfiguration();
        //checkpoint 恢复
        if (StringUtils.isNotEmpty(jobInfo.getSavePointPath())) {
            flinkConfiguration.set(
                    JOBOptions.SAVEPOINT_PATH,
                    jobInfo.getSavePointPath());
        }

        flinkConfiguration.set(
                JOBOptions.INCREMENTAL_CHECKPOINTS,
                true);
        //核心jar包
        flinkConfiguration.set(
                JOBOptions.JARS,
                jobInfo.getUserJars());

        //设置类加载模式
//        flinkConfiguration.setString("classloader.resolve-order", "parent-first");

        flinkConfiguration.set(
                JOBOptions.PROVIDED_LIB_DIRS,
                jobInfo.getUserClasspath());

        flinkConfiguration.set(
                JOBOptions.FLINK_DIST_JAR,
                jobInfo.getFlinkDistJar());
        //设置为application模式
        flinkConfiguration.set(
                JOBOptions.TARGET,
                YarnDeploymentTarget.APPLICATION.getName());
        //yarn application name
        flinkConfiguration.set(
                JOBOptions.APPLICATION_NAME,
                jobInfo.getAppName());

        flinkConfiguration.set(
                JOBOptions.APPLICATION_TYPE,
                jobInfo.getAppType());

        if (StringUtils.isNotEmpty(jobInfo.getYarnQueue())) {
            flinkConfiguration.set(
                    JOBOptions.APPLICATION_QUEUE,
                    jobInfo.getYarnQueue());
        }



        StringBuffer jmVmDynamic = new StringBuffer();
        StringBuffer tmVmDynamic = new StringBuffer();
        if (jobInfo.getDynamicParameters() != null && jobInfo.getDynamicParameters().size() > 0) {
            Map<String, String> dynamicParameters = jobInfo.getDynamicParameters();
            for (String parameterKey : dynamicParameters.keySet()) {
                String dynamicStr = "-D" + parameterKey + "=" + dynamicParameters.get(parameterKey);
                jmVmDynamic.append(" ");
                jmVmDynamic.append(dynamicStr);
                tmVmDynamic.append(" ");
                tmVmDynamic.append(dynamicStr);
                flinkConfiguration.setString(parameterKey, dynamicParameters.get(parameterKey));
            }
            //处理日志配置文件路径动态地址
            jmVmDynamic.append(" -D" + JVMOptions.LOG_FILE + "=" + jobInfo.getLocalLogDir()
                    + File.separator + dynamicParameters.get(SupportKey.JOB_RESOURCES_ID)
                    + File.separator + SupportConstants.JM_LOG_FILE_NAME);
            tmVmDynamic.append(" -D" + JVMOptions.LOG_FILE + "=" + jobInfo.getLocalLogDir()
                    + File.separator + dynamicParameters.get(SupportKey.JOB_RESOURCES_ID)
                    + File.separator + SupportConstants.TM_LOG_FILE_NAME);
            flinkConfiguration.set(JVMOptions.FLINK_LOG_DIR, jobInfo.getLocalLogDir()
                    + File.separator + dynamicParameters.get(SupportKey.JOB_RESOURCES_ID));
        }

        flinkConfiguration.set(JVMOptions.FLINK_TM_JVM_OPTIONS, tmVmDynamic.toString());
        flinkConfiguration.set(JVMOptions.FLINK_JM_JVM_OPTIONS, jmVmDynamic.toString());
        flinkConfiguration.set(PipelineOptionsInternal.PIPELINE_FIXED_JOB_ID, jobInfo.getJobResourceId());

        //		设置用户jar的参数和主类
        ApplicationConfiguration appConfig = new ApplicationConfiguration(jobInfo.getAppArgs(), jobInfo.getAppClassName());


        YarnClusterInformationRetriever informationRetriever = YarnClientYarnClusterInformationRetriever.create(yarnClient);
        YarnClusterDescriptor yarnClusterDescriptor = new YarnClusterDescriptor(
                flinkConfiguration,
                jobInfo.getYarnConfiguration(),
                yarnClient,
                informationRetriever,
                true);
        ClusterClientProvider<ApplicationId> clusterClientProvider = null;

        printJobSubmitInfo(jobInfo);

        try {
            clusterClientProvider = yarnClusterDescriptor.deployApplicationCluster(jobInfo.getClusterSpecification(), appConfig);
        } catch (ClusterDeploymentException e) {
            e.printStackTrace();
        }

        ClusterClient<ApplicationId> clusterClient = clusterClientProvider.getClusterClient();
        ApplicationId applicationId = clusterClient.getClusterId();
        return applicationId;
    }


    public static void printJobSubmitInfo(JobSubmitInfo info) {
        logger.info("Support Job Submission Information:");
        logger.info("============================INFO============================");
        logger.info("Job Name: {}", info.getAppName());
        logger.info("-------------------------------------------------------------");
        logger.info("Job Args: {}", info.getAppArgs());
        logger.info("-------------------------------------------------------------");
        logger.info("Job Class: {}", info.getAppClassName());
        logger.info("-------------------------------------------------------------");
        logger.info("Job Type: {}", info.getAppType());
        logger.info("-------------------------------------------------------------");
        logger.info("Job Queue: {}", info.getYarnQueue());
        logger.info("-------------------------------------------------------------");
        logger.info("Job ClusterInfo: {}", info.getClusterSpecification());
        logger.info("-------------------------------------------------------------");
        logger.info("Job Jar: {}", info.getUserJars());
        logger.info("-------------------------------------------------------------");
        logger.info("Job Classpath: {}", info.getUserClasspath());
        logger.info("-------------------------------------------------------------");
        logger.info("Job Id: {}", info.getJobResourceId());
        logger.info("-------------------------------------------------------------");
        logger.info("Job SavePoint: {}", StringUtils.isEmpty(info.getSavePointPath()) ? info.getSavePointPath() : "不适用");
        logger.info("-------------------------------------------------------------");
        logger.info("Flink DistJar: {}", info.getFlinkDistJar());
        logger.info("-------------------------------------------------------------");
        logger.info("Job Dynamic Parameters: {}", info.getDynamicParameters());
        logger.info("============================================================\n\n");
    }

    @Override
    public Object stopJob(JobSubmitInfo jobInfo) {
        return null;
    }

    @Override
    public Object getClusterClientForAppId(JobSubmitInfo jobInfo) {
        return null;
    }

    public static void main(String[] args) {
        ArrayList<String> classPath = new ArrayList<>();
        classPath.add("/flink_support/resources/1829dw9aijdadkauhdkadu");
        ArrayList<String> userJars = new ArrayList<>();
        userJars.add("/flink_support/lib/support-runtime-1.0.jar");
        JobSubmitInfo build = JobSubmitInfo.newBuilder().appArgs(new String[]{"-logLevel", "Info", "-x", "-jobConf", "waduhawkjdhakiwdjhnwaiudhuiakwuwjhdn"})
                .appClassName("com.weiwan.Tester")
                .appName("TestApp")
                .appType("Support Stream Application")
                .clusterSpecification(ClusterJobUtil.createClusterSpecification(new HashMap<>()))
                .dynamicParameters(new HashMap<>())
                .flinkDistJar("/flink_support/flinks/1.11.1/lib/flink-dist.jar")
                .jobResourceId("1829dw9aijdadkauhdkadu")
                .yarnQueue("root.users.easylife")
                .userClasspath(classPath)
                .userJars(userJars)
                .build();

        printJobSubmitInfo(build);
    }
}
