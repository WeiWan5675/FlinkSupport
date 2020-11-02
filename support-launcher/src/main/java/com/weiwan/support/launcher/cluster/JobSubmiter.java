package com.weiwan.support.launcher.cluster;

import org.apache.hadoop.yarn.api.records.ApplicationId;

/**
 * @Author: xiaozhennan
 * @Date: 2020/11/2 15:28
 * @Package: com.weiwan.support.launcher.cluster.JobSubmiter
 * @ClassName: JobSubmiter
 * @Description:
 **/
public interface JobSubmiter {

    Object submitJob(JobSubmitInfo jobInfo);

    Object stopJob(JobSubmitInfo jobInfo);

    Object getClusterClientForAppId(JobSubmitInfo jobInfo);

}
