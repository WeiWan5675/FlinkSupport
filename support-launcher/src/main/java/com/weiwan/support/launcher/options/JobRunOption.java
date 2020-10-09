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

    @Parameter(names = "-jobType", description = "the type of flink program that the task runs")
    private String jobType;




}
