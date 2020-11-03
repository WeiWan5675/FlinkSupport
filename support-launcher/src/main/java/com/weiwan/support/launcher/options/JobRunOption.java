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


    @Parameter(names = {"-jc", "-jobConf"}, description = "Task description file, supports local path and HDFS path, HDFS path defaults to the corresponding application folder under resources")
    private String jobConf;

    @Parameter(names = "-resources", description = "User resource path, support hdfs and local, default is hdfs://flink_support_space/resources/support_${appName}_${appResourcesMD5}_job")
    private String resources;

    @Parameter(names = {"-overwrite", "-ow"}, description = "Whether to overwrite the remote resource directory, it will only take effect when the local resource directory is used to start")
    private boolean overwriteResource;

    @Parameter(names = {"-sp", "-s", "-savepoint"}, description = "The program starts from the savepoint snapshot and specifies the snapshot file address")
    private String savePointPath;

    @Parameter(names = {"-q","-queue"}, description = "set the name of the yarn resource queue")
    private String queueName;

    public String getQueueName() {
        return queueName;
    }

    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }

    public String getResources() {
        return resources;
    }

    public void setResources(String resources) {
        this.resources = resources;
    }

    public boolean isOverwriteResource() {
        return overwriteResource;
    }

    public void setOverwriteResource(boolean overwriteResource) {
        this.overwriteResource = overwriteResource;
    }

    public String getSavePointPath() {
        return savePointPath;
    }

    public void setSavePointPath(String savePointPath) {
        this.savePointPath = savePointPath;
    }

    public String getJobConf() {
        return jobConf;
    }

    public void setJobConf(String jobConf) {
        this.jobConf = jobConf;
    }
}
