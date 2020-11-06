package com.weiwan.support.launcher.submit;

import org.apache.hadoop.yarn.client.api.YarnClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @Author: xiaozhennan
 * @Date: 2020/10/27 11:14
 * @Package: com.weiwan.support.launcher.submit.JobSubmiter
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
