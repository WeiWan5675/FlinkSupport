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

    @Parameter(names = "-logLevel", description = "client log level")
    private String logLevel = "INFO";

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
}
