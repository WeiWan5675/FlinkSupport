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
package com.weiwan.support.utils.cluster;

import org.apache.commons.lang.StringUtils;
import org.apache.flink.configuration.GlobalConfiguration;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.yarn.conf.YarnConfiguration;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Iterator;
import java.util.Map;

/**
 * @Author: xiaozhennan
 * @Date: 2020/7/31 10:09
 * @Package: com.weiwan.support.core.utils
 * @ClassName: HadoopConfigLoader
 * @Description: 集群配置文件加载
 **/
public class ClusterConfigLoader {

    public static org.apache.hadoop.conf.Configuration loadHadoopConfig(String confDir) {
        org.apache.hadoop.conf.Configuration configuration = new org.apache.hadoop.conf.Configuration();
        try {
            findXmlFiles(confDir, configuration);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return configuration;
    }


    public static YarnConfiguration loadYarnConfig(String confDir) {
        YarnConfiguration yarnConfiguration = new YarnConfiguration();
        try {
            findXmlFiles(confDir, yarnConfiguration);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        haYarnConf(yarnConfiguration);
        return yarnConfiguration;
    }

    /**
     * deal yarn HA conf
     */
    private static Configuration haYarnConf(org.apache.hadoop.conf.Configuration yarnConf) {
        Iterator<Map.Entry<String, String>> iterator = yarnConf.iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> entry = iterator.next();
            String key = entry.getKey();
            String value = entry.getValue();
            if (key.startsWith("yarn.resourcemanager.hostname.")) {
                String rm = key.substring("yarn.resourcemanager.hostname.".length());
                String addressKey = "yarn.resourcemanager.address." + rm;
                if (yarnConf.get(addressKey) == null) {
                    yarnConf.set(addressKey, value + ":" + YarnConfiguration.DEFAULT_RM_PORT);
                }
            }
        }
        return yarnConf;
    }


    public static org.apache.flink.configuration.Configuration loadFlinkConfig(String confDir) {
        org.apache.flink.configuration.Configuration flinkConfiguration = StringUtils.isEmpty(confDir) ? new org.apache.flink.configuration.Configuration() : GlobalConfiguration.loadConfiguration(confDir);
        return flinkConfiguration;
    }


    private static void findXmlFiles(String confDir, org.apache.hadoop.conf.Configuration configuration) throws MalformedURLException {
        File dir = new File(confDir);
        if (dir.exists() && dir.isDirectory()) {

            File[] xmlFileList = new File(confDir).listFiles((dir1, name) -> {
                if (name.endsWith(".xml")) {
                    return true;
                }
                return false;
            });
            if (xmlFileList != null) {
                for (File xmlFile : xmlFileList) {
                    configuration.addResource(xmlFile.toURI().toURL());
                }
            }
        }
    }

}
