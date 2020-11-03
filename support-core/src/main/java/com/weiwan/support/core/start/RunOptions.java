package com.weiwan.support.core.start;

import com.beust.jcommander.Parameter;

/**
 * @Author: xiaozhennan
 * @Date: 2020/9/28 17:20
 * @Package: com.weiwan.support.core.start.RunOptions
 * @ClassName: RunOptions
 * @Description:
 **/
public class RunOptions {


    @Parameter(names = {"-x", "-stream"}, description = "stream mode")
    private boolean isStream;

    @Parameter(names = {"-t", "-table"}, description = "stream table mode")
    private boolean isTable;

    @Parameter(names = {"-e", "-etl", "-etlMode"}, description = "running etl mode")
    private boolean isEtl;

    @Parameter(names = {"-logLevel"}, description = "setting log level")
    private String logLevel;

    @Parameter(names = {"-jobConf", "-jc"}, description = "job desc json content")
    private String jobDescJson;

    public boolean isStream() {
        return isStream;
    }

    public void setStream(boolean stream) {
        isStream = stream;
    }


    public boolean isEtl() {
        return isEtl;
    }

    public void setEtl(boolean etl) {
        isEtl = etl;
    }

    public String getLogLevel() {
        return logLevel;
    }

    public void setLogLevel(String logLevel) {
        this.logLevel = logLevel;
    }

    public String getJobDescJson() {
        return jobDescJson;
    }

    public void setJobDescJson(String jobDescJson) {
        this.jobDescJson = jobDescJson;
    }
}
