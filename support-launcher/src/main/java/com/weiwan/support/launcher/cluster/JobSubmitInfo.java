package com.weiwan.support.launcher.cluster;

import org.apache.flink.client.deployment.ClusterSpecification;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.yarn.conf.YarnConfiguration;

import java.util.List;

/**
 * @Author: xiaozhennan
 * @Date: 2020/10/29 16:09
 * @Package: com.weiwan.support.launcher.cluster.JobSubmitInfo
 * @ClassName: JobSubmitInfo
 * @Description:
 **/
public class JobSubmitInfo {

    private Configuration hadoopConfiguration;
    private org.apache.flink.configuration.Configuration flinkConfiguration;
    private YarnConfiguration yarnConfiguration;

    private ClusterSpecification clusterSpecification;
    private String userJarPath;
    private List<String> userClassPath;
    private String flinkDistJar;
    private String flinkLibs;
    private String appName;
    private String appType;
    private String yarnQueue;
    private String[] appArgs;
    private String appClassName;
    private String savePointPath;

    private JobSubmitInfo(Builder builder) {
        setHadoopConfiguration(builder.hadoopConfiguration);
        setFlinkConfiguration(builder.flinkConfiguration);
        setYarnConfiguration(builder.yarnConfiguration);
        setClusterSpecification(builder.clusterSpecification);
        setUserJarPath(builder.userJarPath);
        setUserClassPath(builder.userClassPath);
        setFlinkDistJar(builder.flinkDistJar);
        setFlinkLibs(builder.flinkLibs);
        setAppName(builder.appName);
        setAppType(builder.appType);
        setYarnQueue(builder.yarnQueue);
        setAppArgs(builder.appArgs);
        setAppClassName(builder.appClassName);
        setSavePointPath(builder.savePointPath);
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

    public String getUserJarPath() {
        return userJarPath;
    }

    public void setUserJarPath(String userJarPath) {
        this.userJarPath = userJarPath;
    }

    public List<String> getUserClassPath() {
        return userClassPath;
    }

    public void setUserClassPath(List<String> userClassPath) {
        this.userClassPath = userClassPath;
    }

    public String getFlinkDistJar() {
        return flinkDistJar;
    }

    public void setFlinkDistJar(String flinkDistJar) {
        this.flinkDistJar = flinkDistJar;
    }

    public String getFlinkLibs() {
        return flinkLibs;
    }

    public void setFlinkLibs(String flinkLibs) {
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

    public static final class Builder {
        private Configuration hadoopConfiguration;
        private org.apache.flink.configuration.Configuration flinkConfiguration;
        private YarnConfiguration yarnConfiguration;
        private ClusterSpecification clusterSpecification;
        private String userJarPath;
        private List<String> userClassPath;
        private String flinkDistJar;
        private String flinkLibs;
        private String appName;
        private String appType;
        private String yarnQueue;
        private String[] appArgs;
        private String appClassName;
        private String savePointPath;

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

        public Builder userJarPath(String val) {
            userJarPath = val;
            return this;
        }

        public Builder userClassPath(List<String> val) {
            userClassPath = val;
            return this;
        }

        public Builder flinkDistJar(String val) {
            flinkDistJar = val;
            return this;
        }

        public Builder flinkLibs(String val) {
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

        public JobSubmitInfo build() {
            return new JobSubmitInfo(this);
        }
    }
}
