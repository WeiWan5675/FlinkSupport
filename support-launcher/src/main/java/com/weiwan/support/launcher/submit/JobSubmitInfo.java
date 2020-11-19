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
package com.weiwan.support.launcher.submit;

import org.apache.flink.client.deployment.ClusterSpecification;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.yarn.conf.YarnConfiguration;

import java.util.List;
import java.util.Map;

/**
 * @Author: xiaozhennan
 * @Date: 2020/10/29 16:09
 * @Package: com.weiwan.support.launcher.submit.JobSubmitInfo
 * @ClassName: JobSubmitInfo
 * @Description:
 **/
public class JobSubmitInfo {

    private Configuration hadoopConfiguration;
    private org.apache.flink.configuration.Configuration flinkConfiguration;
    private YarnConfiguration yarnConfiguration;

    private ClusterSpecification clusterSpecification;
    private List<String> userJars;
    private List<String> userClasspath;
    private String flinkDistJar;
    private List<String> flinkLibs;
    private String appName;
    private String appType;
    private String yarnQueue;
    private String[] appArgs;
    private String appClassName;
    private String savePointPath;
    private String jobResourceId;
    private String localLogDir;
    private Map<String, String> dynamicParameters;

    private JobSubmitInfo(Builder builder) {
        setHadoopConfiguration(builder.hadoopConfiguration);
        setFlinkConfiguration(builder.flinkConfiguration);
        setYarnConfiguration(builder.yarnConfiguration);
        setClusterSpecification(builder.clusterSpecification);
        setUserJars(builder.userJars);
        setUserClasspath(builder.userClasspath);
        setFlinkDistJar(builder.flinkDistJar);
        setFlinkLibs(builder.flinkLibs);
        setAppName(builder.appName);
        setAppType(builder.appType);
        setYarnQueue(builder.yarnQueue);
        setAppArgs(builder.appArgs);
        setAppClassName(builder.appClassName);
        setSavePointPath(builder.savePointPath);
        setJobResourceId(builder.jobResourceId);
        setLocalLogDir(builder.localLogDir);
        setDynamicParameters(builder.dynamicParameters);
    }

    public static Builder newBuilder() {
        return new Builder();
    }


    public Configuration getHadoopConfiguration() {
        return hadoopConfiguration;
    }

    public void setHadoopConfiguration(Configuration hadoopConfiguration) {
        this.hadoopConfiguration = hadoopConfiguration;
    }

    public org.apache.flink.configuration.Configuration getFlinkConfiguration() {
        return flinkConfiguration;
    }

    public void setFlinkConfiguration(org.apache.flink.configuration.Configuration flinkConfiguration) {
        this.flinkConfiguration = flinkConfiguration;
    }

    public YarnConfiguration getYarnConfiguration() {
        return yarnConfiguration;
    }

    public void setYarnConfiguration(YarnConfiguration yarnConfiguration) {
        this.yarnConfiguration = yarnConfiguration;
    }

    public ClusterSpecification getClusterSpecification() {
        return clusterSpecification;
    }

    public void setClusterSpecification(ClusterSpecification clusterSpecification) {
        this.clusterSpecification = clusterSpecification;
    }

    public List<String> getUserJars() {
        return userJars;
    }

    public void setUserJars(List<String> userJars) {
        this.userJars = userJars;
    }

    public List<String> getUserClasspath() {
        return userClasspath;
    }

    public void setUserClasspath(List<String> userClasspath) {
        this.userClasspath = userClasspath;
    }

    public String getFlinkDistJar() {
        return flinkDistJar;
    }

    public void setFlinkDistJar(String flinkDistJar) {
        this.flinkDistJar = flinkDistJar;
    }

    public List<String> getFlinkLibs() {
        return flinkLibs;
    }

    public void setFlinkLibs(List<String> flinkLibs) {
        this.flinkLibs = flinkLibs;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAppType() {
        return appType;
    }

    public void setAppType(String appType) {
        this.appType = appType;
    }

    public String getYarnQueue() {
        return yarnQueue;
    }

    public void setYarnQueue(String yarnQueue) {
        this.yarnQueue = yarnQueue;
    }

    public String[] getAppArgs() {
        return appArgs;
    }

    public void setAppArgs(String[] appArgs) {
        this.appArgs = appArgs;
    }

    public String getAppClassName() {
        return appClassName;
    }

    public void setAppClassName(String appClassName) {
        this.appClassName = appClassName;
    }

    public String getSavePointPath() {
        return savePointPath;
    }

    public void setSavePointPath(String savePointPath) {
        this.savePointPath = savePointPath;
    }

    public String getJobResourceId() {
        return jobResourceId;
    }

    public void setJobResourceId(String jobResourceId) {
        this.jobResourceId = jobResourceId;
    }

    public String getLocalLogDir() {
        return localLogDir;
    }

    public void setLocalLogDir(String localLogDir) {
        this.localLogDir = localLogDir;
    }

    public Map<String, String> getDynamicParameters() {
        return dynamicParameters;
    }

    public void setDynamicParameters(Map<String, String> dynamicParameters) {
        this.dynamicParameters = dynamicParameters;
    }

    public static final class Builder {
        private Configuration hadoopConfiguration;
        private org.apache.flink.configuration.Configuration flinkConfiguration;
        private YarnConfiguration yarnConfiguration;
        private ClusterSpecification clusterSpecification;
        private List<String> userJars;
        private List<String> userClasspath;
        private String flinkDistJar;
        private List<String> flinkLibs;
        private String appName;
        private String appType;
        private String yarnQueue;
        private String[] appArgs;
        private String appClassName;
        private String savePointPath;
        private String jobResourceId;
        private String localLogDir;
        private Map<String, String> dynamicParameters;

        private Builder() {
        }

        public Builder hadoopConfiguration(Configuration val) {
            hadoopConfiguration = val;
            return this;
        }

        public Builder flinkConfiguration(org.apache.flink.configuration.Configuration val) {
            flinkConfiguration = val;
            return this;
        }

        public Builder yarnConfiguration(YarnConfiguration val) {
            yarnConfiguration = val;
            return this;
        }

        public Builder clusterSpecification(ClusterSpecification val) {
            clusterSpecification = val;
            return this;
        }

        public Builder userJars(List<String> val) {
            userJars = val;
            return this;
        }

        public Builder userClasspath(List<String> val) {
            userClasspath = val;
            return this;
        }

        public Builder flinkDistJar(String val) {
            flinkDistJar = val;
            return this;
        }

        public Builder flinkLibs(List<String> val) {
            flinkLibs = val;
            return this;
        }

        public Builder appName(String val) {
            appName = val;
            return this;
        }

        public Builder appType(String val) {
            appType = val;
            return this;
        }

        public Builder yarnQueue(String val) {
            yarnQueue = val;
            return this;
        }

        public Builder appArgs(String[] val) {
            appArgs = val;
            return this;
        }

        public Builder appClassName(String val) {
            appClassName = val;
            return this;
        }

        public Builder savePointPath(String val) {
            savePointPath = val;
            return this;
        }

        public Builder jobResourceId(String val) {
            jobResourceId = val;
            return this;
        }

        public Builder localLogDir(String val) {
            localLogDir = val;
            return this;
        }

        public Builder dynamicParameters(Map<String, String> val) {
            dynamicParameters = val;
            return this;
        }

        public JobSubmitInfo build() {
            return new JobSubmitInfo(this);
        }
    }
}
