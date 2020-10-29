package com.weiwan.support.launcher.cluster;

import org.apache.flink.client.deployment.ClusterSpecification;
import org.apache.flink.client.deployment.application.ApplicationConfiguration;
import org.apache.flink.yarn.configuration.YarnDeploymentTarget;
import org.apache.hadoop.conf.Configuration;

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

    private ClusterSpecification clusterSpecification;
    private String userJarPath;
    private String userClassPath;
    private String flinkDistJar;
    private String flinkLibs;
    private String appName;
    private String appType;
    private String[] appArgs;
    private String appClassName;

    private JobSubmitInfo(Builder builder) {
        setHadoopConfiguration(builder.hadoopConfiguration);
        setFlinkConfiguration(builder.flinkConfiguration);
        setClusterSpecification(builder.clusterSpecification);
        setUserJarPath(builder.userJarPath);
        setUserClassPath(builder.userClassPath);
        setFlinkDistJar(builder.flinkDistJar);
        setFlinkLibs(builder.flinkLibs);
        setAppName(builder.appName);
        setAppType(builder.appType);
        setAppArgs(builder.appArgs);
        setAppClassName(builder.appClassName);
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

    public String getUserClassPath() {
        return userClassPath;
    }

    public void setUserClassPath(String userClassPath) {
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

    public static final class Builder {
        private Configuration hadoopConfiguration;
        private org.apache.flink.configuration.Configuration flinkConfiguration;
        private ClusterSpecification clusterSpecification;
        private String userJarPath;
        private String userClassPath;
        private String flinkDistJar;
        private String flinkLibs;
        private String appName;
        private String appType;
        private String[] appArgs;
        private String appClassName;

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

        public Builder clusterSpecification(ClusterSpecification val) {
            clusterSpecification = val;
            return this;
        }

        public Builder userJarPath(String val) {
            userJarPath = val;
            return this;
        }

        public Builder userClassPath(String val) {
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

        public Builder appArgs(String[] val) {
            appArgs = val;
            return this;
        }

        public Builder appClassName(String val) {
            appClassName = val;
            return this;
        }

        public JobSubmitInfo build() {
            return new JobSubmitInfo(this);
        }
    }
}
