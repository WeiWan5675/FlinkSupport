package com.weiwan.support.launcher.submit;

/**
 * @Author: xiaozhennan
 * @Date: 2020/11/2 15:23
 * @Package: com.weiwan.support.launcher.submit.KubernetesJobSubmiter
 * @ClassName: KubernetesJobSubmiter
 * @Description:
 **/
public class KubernetesJobSubmiter implements JobSubmiter {
    @Override
    public Object submitJob(JobSubmitInfo jobSubmitInfo) {

        return null;
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
