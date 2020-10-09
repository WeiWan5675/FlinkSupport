package com.weiwan.support.launcher.options;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParametersDelegate;

/**
 * @Author: xiaozhennan
 * @Date: 2020/9/30 11:22
 * @Package: com.weiwan.support.launcher.options.GenericRunOption
 * @ClassName: GenericRunOption
 * @Description:
 **/
public class GenericRunOption {


    @Parameter(names = "--help", help = true)
    private boolean help;

    @Parameter(names = "--version", description = "support framework client version")
    private boolean verbose;

    @Parameter(names = {"-m", "-mode"}, description = "support framework client runing env mode")
    private String runMode = "job";

    @Parameter(names = "-n", description = "User program name, default is: Support Application")
    private String appName = "Support Application";

    @Parameter(names = "-logLevel", description = "client log level, default is: INFO")
    private String logLevel = "INFO";

    @Parameter(names = "-flinkHome", description = "Flink Home, obtained from environment variables by default")
    private String flinkHome;

    @Parameter(names = "-hadoopHome", description = "Hadoop Home, obtained from environment variables by default")
    private String hadoopHome;

    @Parameter(names = "-yarnHome", description = "Yarn Home, obtained from environment variables by default")
    private String yarnHome;

    @Parameter(names = "-hiveHome", description = "Hive Home, obtained from environment variables by default")
    private String hiveHome;

    @Parameter(names = "-myHome", description = "The location of the support framework installed on the disk")
    private String myHome;

    @Parameter(names = "-userResources", description = "The path of user application resources, supports local or HDFS paths")
    private String userResources;

    public boolean isHelp() {
        return help;
    }

    public void setHelp(boolean help) {
        this.help = help;
    }

    public boolean isVerbose() {
        return verbose;
    }

    public void setVerbose(boolean verbose) {
        this.verbose = verbose;
    }

    public String getRunMode() {
        return runMode;
    }

    public void setRunMode(String runMode) {
        this.runMode = runMode;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getLogLevel() {
        return logLevel;
    }

    public void setLogLevel(String logLevel) {
        this.logLevel = logLevel;
    }

    public String getFlinkHome() {
        return flinkHome;
    }

    public void setFlinkHome(String flinkHome) {
        this.flinkHome = flinkHome;
    }

    public String getHadoopHome() {
        return hadoopHome;
    }

    public void setHadoopHome(String hadoopHome) {
        this.hadoopHome = hadoopHome;
    }

    public String getYarnHome() {
        return yarnHome;
    }

    public void setYarnHome(String yarnHome) {
        this.yarnHome = yarnHome;
    }

    public String getHiveHome() {
        return hiveHome;
    }

    public void setHiveHome(String hiveHome) {
        this.hiveHome = hiveHome;
    }

    public String getMyHome() {
        return myHome;
    }

    public void setMyHome(String myHome) {
        this.myHome = myHome;
    }

    public String getUserResources() {
        return userResources;
    }

    public void setUserResources(String userResources) {
        this.userResources = userResources;
    }
}
