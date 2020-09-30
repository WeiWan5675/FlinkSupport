package com.weiwan.support.common.utils;

import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FilenameFilter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: xiaozhennan
 * @Date: 2020/7/16 15:43
 * @Package: com.weiwan.common.utils
 * @ClassName: SystemUtil
 * @Description:
 **/
public class SystemUtil {

    public static String getSystemVar(String name) {
        String property = System.getenv(name);
        if (StringUtils.isEmpty(property)) {
            property = System.getProperty(name);
        }

        return property;
    }


    public static void sleep(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<URL> findJarsInDir(File dir) throws MalformedURLException {
        List<URL> urlList = new ArrayList<>();

        if (dir.exists() && dir.isDirectory()) {
            File[] jarFiles = dir.listFiles(new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    return name.toLowerCase().endsWith(".jar");
                }
            });

            for (File jarFile : jarFiles) {
                urlList.add(jarFile.toURI().toURL());
            }

        }

        return urlList;
    }

    public static void setSystemVar(String key, String var) {
        System.setProperty(key, var);
    }
}
