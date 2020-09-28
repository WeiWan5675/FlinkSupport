package com.weiwan.support.common.utils;

import org.apache.commons.codec.Charsets;
import com.weiwan.common.exception.ArgusCommonException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * @Author: xiaozhennan
 * @Date: 2020/8/12 17:46
 * @Package: com.weiwan.common.utils.FileUtil
 * @ClassName: FileUtil
 * @Description:
 **/
public class FileUtil {

    public static String readFileContent(String path) throws IOException {
        //读取配置文件  转化成json对象
        File file = new File(path);
        if (!file.exists() || file.isDirectory()) {
            throw new ArgusCommonException(String.format("The configuration file %s does not exist, please check the configuration file path!", file.getAbsolutePath()));
        }
        FileInputStream in = new FileInputStream(file);
        byte[] filecontent = new byte[(int) file.length()];
        in.read(filecontent);
        return new String(filecontent, Charsets.UTF_8.name());
    }

    public static boolean isAbsolutePath(String path) {
        if (path != null && !path.equalsIgnoreCase("")) {
            if (path.startsWith("/")) {
                return true;
            }
            if (path.startsWith(":", 1)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isDir(String path) {
        File file = new File(path);
        return file.isDirectory();
    }


}
