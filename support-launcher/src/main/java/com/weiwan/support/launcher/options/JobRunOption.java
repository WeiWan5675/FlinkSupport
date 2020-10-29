package com.weiwan.support.launcher.options;

import com.beust.jcommander.Parameter;

/**
 * @Author: xiaozhennan
 * @Date: 2020/9/30 11:23
 * @Package: com.weiwan.support.launcher.options.JobRunOption
 * @ClassName: JobRunOption
 * @Description:
 **/
public class JobRunOption extends GenericRunOption {


    @Parameter(names = {"-jc","-jobConf"},description = "Task description file, supports local path and HDFS path, HDFS path defaults to the corresponding application folder under resources")
    private String jobConf;

    public String getJobConf() {
        return jobConf;
    }

    public void setJobConf(String jobConf) {
        this.jobConf = jobConf;
    }
}
