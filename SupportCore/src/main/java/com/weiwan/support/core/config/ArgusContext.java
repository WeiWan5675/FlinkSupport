package com.weiwan.support.core.config;

import com.weiwan.support.core.start.StartOptions;

import java.io.Serializable;

/**
 * @Author: xiaozhennan
 * @Date: 2020/7/17 16:43
 * @Package: com.weiwan.core.pub.config
 * @ClassName: ArgusContextConfig
 * @Description:
 **/
public class ArgusContext implements Serializable {

    private JobConfig JobConfig;
    private FlinkEnvConfig flinkEnvConfig;
    private StartOptions startOptions;

    public ArgusContext(StartOptions startOptions) {
        this.startOptions = startOptions;
    }

    public ArgusContext() {

    }


    public JobConfig getJobConfig() {
        return JobConfig;
    }

    public void setJobConfig(JobConfig jobConfig) {
        JobConfig = jobConfig;
    }

    public FlinkEnvConfig getFlinkEnvConfig() {
        return flinkEnvConfig;
    }

    public void setFlinkEnvConfig(FlinkEnvConfig flinkEnvConfig) {
        this.flinkEnvConfig = flinkEnvConfig;
    }

    public StartOptions getStartOptions() {
        return startOptions;
    }

    public void setStartOptions(StartOptions startOptions) {
        this.startOptions = startOptions;
    }
}
