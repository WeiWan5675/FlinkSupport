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
package com.weiwan.support.core.pojo;

import java.io.Serializable;

/**
 * @Author: xiaozhennan
 * @Date: 2020/7/21 15:06
 * @Package: com.weiwan.support.core.pub.api
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
