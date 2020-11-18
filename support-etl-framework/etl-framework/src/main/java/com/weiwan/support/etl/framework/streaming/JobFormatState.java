package com.weiwan.support.etl.framework.streaming;

import java.io.Serializable;

/**
 * @Author: xiaozhennan
 * @Date: 2020/7/21 15:06
 * @Package: org.weiwan.argus.core.pub.api
 * @ClassName: JobFormatState
 * @Description:
 **/
public class JobFormatState implements Serializable {

    private int numOfSubTask;
    private String jobId;
    private String jobName;
    private Object state;

    public int getNumOfSubTask() {
        return numOfSubTask;
    }

    public void setNumOfSubTask(int numOfSubTask) {
        this.numOfSubTask = numOfSubTask;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public <T> T getState(Class<T> tClass) {
        return (T) state;
    }

    public Object getState() {
        return state;
    }

    public void setState(Object state) {
        this.state = state;
    }


    @Override
    public String toString() {
        return "JobFormatState{" +
                "numOfSubTask=" + numOfSubTask +
                ", jobId='" + jobId + '\'' +
                ", jobName='" + jobName + '\'' +
                ", state=" + state +
                '}';
    }
}
