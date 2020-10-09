package com.weiwan.support.core;

import com.weiwan.support.core.config.JobConfig;
import com.weiwan.support.core.start.RunOptions;
import com.weiwan.support.utils.flink.conf.FlinkEnvConfig;

import java.io.Serializable;

/**
 * @Author: xiaozhennan
 * @Date: 2020/9/28 16:38
 * @Package: com.weiwan.support.core.SupportAppContext
 * @ClassName: SupportAppContext
 * @Description:
 **/
public class SupportAppContext implements Serializable {

    private JobConfig JobConfig;
    private FlinkEnvConfig flinkEnvConfig;
    private RunOptions options;

    public SupportAppContext(RunOptions options) {
        this.options = options;
    }

    public SupportAppContext() {

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

    public RunOptions getOptions() {
        return options;
    }

    public void setOptions(RunOptions options) {
        this.options = options;
    }
}
