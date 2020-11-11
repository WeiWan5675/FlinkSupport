package com.weiwan.support.core.start;

import com.beust.jcommander.Parameter;
import com.weiwan.support.common.options.CommonOptions;

/**
 * @Author: xiaozhennan
 * @Date: 2020/9/28 17:20
 * @Package: com.weiwan.support.core.start.RunOptions
 * @ClassName: RunOptions
 * @Description:
 **/
public class RunOptions extends CommonOptions {


    @Parameter(names = {"-x", "-stream"}, description = "stream mode")
    private boolean stream;

    @Parameter(names = {"-b", "-batch"}, description = "stream mode")
    private boolean batch;

    @Parameter(names = {"-t", "-table"}, description = "stream table mode")
    private boolean table;

    @Parameter(names = {"-e", "-etl", "-etlMode"}, description = "running etl mode")
    private boolean etl;

    @Parameter(names = {"-logLevel"}, description = "setting log level")
    private String logLevel;

    @Parameter(names = {"-jobConf", "-jc"}, description = "job desc json content")
    private String jobDescJson;

    private boolean enableAnnotation;

    public boolean isEnableAnnotation() {
        return enableAnnotation;
    }

    public void setEnableAnnotation(boolean enableAnnotation) {
        this.enableAnnotation = enableAnnotation;
    }

    public boolean isBatch() {
        return batch;
    }

    public void setBatch(boolean batch) {
        this.batch = batch;
    }

    public boolean isStream() {
        return stream;
    }

    public void setStream(boolean stream) {
        this.stream = stream;
    }

    public boolean isTable() {
        return table;
    }

    public void setTable(boolean table) {
        this.table = table;
    }

    public boolean isEtl() {
        return etl;
    }

    public void setEtl(boolean etl) {
        this.etl = etl;
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
