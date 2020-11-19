/*
 *      Copyright [2020] [xiaozhennan1995@gmail.com]
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 *      http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.weiwan.support.launcher.options;

import com.beust.jcommander.Parameter;
import com.weiwan.support.launcher.enums.RunCmd;

/**
 * @Author: xiaozhennan
 * @Date: 2020/9/30 11:23
 * @Package: com.weiwan.support.launcher.options.JobRunOption
 * @ClassName: JobRunOption
 * @Description:
 **/
public class JobRunOption extends GenericRunOption {

    @Parameter(names = {"-cmd"}, converter = RumCmdEnumConverter.class, description = "The command to be executed, the optional commands are [run|stop|canal|info|list]")
    private RunCmd cmd;

    @Parameter(names = {"-c", "-conf"}, description = "Task description file, supports local path and HDFS path, HDFS path defaults to the corresponding application folder under resources")
    private String jobConf;

    @Parameter(names = "-resources", description = "User resource path, support hdfs and local, default is hdfs://flink_support/resources/support_${appName}_${appResourcesMD5}_job")
    private String resources;

    @Parameter(names = {"-overwrite", "-ow"}, description = "Whether to overwrite the remote resource directory, it will only take effect when the local resource directory is used to start")
    private boolean overwriteResource;

    @Parameter(names = {"-sp", "-s", "-savepoint"}, description = "The program starts from the savepoint snapshot and specifies the snapshot file address")
    private String savePointPath;

    @Parameter(names = {"-q", "-queue"}, description = "set the name of the yarn resource queue")
    private String queueName;

    @Parameter(names = "-jid",description = "When you want to execute {stop|canal|info}, you need to use this parameter to specify job Id")
    private String jobId;

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

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


    public RunCmd getCmd() {
        return cmd;
    }

    public void setCmd(RunCmd cmd) {
        this.cmd = cmd;
    }
}
