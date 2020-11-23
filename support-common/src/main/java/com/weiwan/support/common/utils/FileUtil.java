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
package com.weiwan.support.common.utils;

import com.weiwan.support.common.exception.SupportException;
import org.apache.commons.codec.Charsets;
import org.apache.commons.lang3.StringUtils;

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
            throw new SupportException(String.format("The configuration file %s does not exist, please check the configuration file path!", file.getAbsolutePath()));
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



    public static boolean existsDir(String dirName) {
        if (dirName != null) {
            File file = new File(dirName);
            return file.exists() && file.isDirectory();
        }
        return false;
    }

    public static boolean existsFile(String fileName) {
        if (fileName != null) {
            File file = new File(fileName);
            return file.exists() && file.isDirectory();
        }
        return false;
    }

    public static boolean checkFileSuffix(String path, String... fileSuffix) {
        if (StringUtils.isNotEmpty(path)) {
            for (String suffix : fileSuffix) {
                if (path.endsWith(suffix)) {
                    return true;
                }
            }
        }
        return false;
    }
}
