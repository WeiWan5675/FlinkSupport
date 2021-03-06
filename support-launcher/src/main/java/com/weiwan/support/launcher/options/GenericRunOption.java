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

import com.beust.jcommander.DynamicParameter;
import com.beust.jcommander.Parameter;
import com.weiwan.support.common.options.CommonOptions;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: xiaozhennan
 * @Date: 2020/9/30 11:22
 * @Package: com.weiwan.support.launcher.options.GenericRunOption
 * @ClassName: GenericRunOption
 * @Description:
 **/
public class GenericRunOption extends CommonOptions {

    @Parameter(names = {"-m", "-mode"}, description = "support framework client runing env mode")
    private String runMode = "JOB";

    @Parameter(names = {"-n", "-name"}, description = "User program name, default is: Support Application")
    private String appName;

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


}
