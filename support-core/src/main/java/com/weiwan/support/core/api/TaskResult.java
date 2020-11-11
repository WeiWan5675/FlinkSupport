package com.weiwan.support.core.api;

import org.apache.flink.api.common.JobID;

/**
 * @Author: xiaozhennan
 * @Date: 2020/9/28 16:12
 * @Package: com.weiwan.support.core.api.TaskResult
 * @ClassName: TaskResult
 * @Description:
 **/
public class TaskResult {

    private JobID jobID;

    public TaskResult(JobID jobID) {
        this.jobID = jobID;
    }


    public JobID getJobID() {
        return jobID;
    }

    public void setJobID(JobID jobID) {
        this.jobID = jobID;
    }
}
