package com.weiwan.support.launcher.cluster;

import org.apache.flink.client.deployment.ClusterSpecification;
import org.apache.flink.client.program.PackagedProgram;
import org.apache.hadoop.yarn.client.api.YarnClient;
import org.apache.hadoop.yarn.conf.YarnConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.Map;


/**
 * @Author: xiaozhennan
 * @Date: 2020/9/30 10:44
 * @Package: com.weiwan.support.launcher.cluster.ClusterJobUtil
 * @ClassName: ClusterJobUtil
 * @Description:
 **/
public class ClusterJobUtil {
    private static final Logger LOG = LoggerFactory.getLogger(ClusterJobUtil.class);
    /**
     * Minimum memory requirements, checked by the Client.
     * the minimum memory should be higher than the min heap cutoff
     */
    public final static int MIN_JM_MEMORY = 768;
    public final static int MIN_TM_MEMORY = 768;

    public final static String JOBMANAGER_MEMORY_MB = "jobmanager.memory.mb";
    public final static String TASKMANAGER_MEMORY_MB = "taskmanager.memory.mb";
    public final static String SLOTS_PER_TASKMANAGER = "taskmanager.slots";

    /**
     * @param conf cluster dynamic parameter
     * @return
     */
    public static ClusterSpecification createClusterSpecification(Map conf) {
        int jobmanagerMemoryMb = 768;
        int taskmanagerMemoryMb = 768;
        int slotsPerTaskManager = 1;
        if (conf != null) {
            if (conf.containsKey(JOBMANAGER_MEMORY_MB)) {
                jobmanagerMemoryMb = Math.max(MIN_JM_MEMORY, (Integer) conf.getOrDefault(JOBMANAGER_MEMORY_MB, jobmanagerMemoryMb));
            }
            if (conf.containsKey(TASKMANAGER_MEMORY_MB)) {
                taskmanagerMemoryMb = Math.max(MIN_TM_MEMORY, (Integer) conf.getOrDefault(TASKMANAGER_MEMORY_MB, taskmanagerMemoryMb));
            }
            if (conf.containsKey(SLOTS_PER_TASKMANAGER)) {
                slotsPerTaskManager = (int) conf.getOrDefault(SLOTS_PER_TASKMANAGER, slotsPerTaskManager);
            }
        }
        return new ClusterSpecification.ClusterSpecificationBuilder()
                .setMasterMemoryMB(jobmanagerMemoryMb)
                .setTaskManagerMemoryMB(taskmanagerMemoryMb)
                .setSlotsPerTaskManager(slotsPerTaskManager)
                .createClusterSpecification();
    }


    public static YarnClient getYarnClient(YarnConfiguration configuration) {
        YarnClient yarnClient = YarnClient.createYarnClient();
        yarnClient.init(configuration);
        yarnClient.start();
        return yarnClient;
    }
}