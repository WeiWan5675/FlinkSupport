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
package com.weiwan.support.utils.hadoop;


import com.weiwan.support.common.utils.FileUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.hadoop.fs.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class HdfsUtil {


    public static boolean existsFile(FileSystem fileSystem, Path path) {
        if (fileSystem != null && path != null) {
            try {
                return fileSystem.exists(path) && fileSystem.isFile(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }


    public static boolean existsDir(FileSystem fileSystem, Path path) {
        if (fileSystem != null && path != null) {
            try {
                return fileSystem.exists(path) && fileSystem.isDirectory(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }


    public static void mkdir(String userResourceDir, boolean... overwrite) {
        if (overwrite.length == 1) {
            //需要重载
        } else {
            //直接创建
        }

    }

    public static boolean dirIsEmpty(FileSystem fileSystem, Path path) {
        if (fileSystem != null && path != null) {
            try {
                FileStatus[] fileStatuses = fileSystem.listStatus(path);
                if (fileStatuses == null || fileStatuses.length < 1) {
                    return true;
                }
            } catch (IOException e) {
                e.printStackTrace();
                return true;
            }
        }
        return false;
    }

    public static String readFileContent(FileSystem fileSystem, Path path) throws IOException {
        if (fileSystem.exists(path) && fileSystem.isFile(path)) {
            StringBuffer sb = new StringBuffer();
            FSDataInputStream in = fileSystem.open(path);
            BufferedReader d = new BufferedReader(new InputStreamReader(in));
            String line = null;
            while ((line = d.readLine()) != null) {
                sb.append(line);
                sb.append("\n");
            }
            return sb.toString();
        }
        return "";
    }

    public static void drop(FileSystem fileSystem, Path remotePath, boolean recursion) throws IOException {
        if (remotePath != null && fileSystem != null) {
            if (fileSystem.exists(remotePath)) {
                fileSystem.delete(remotePath, recursion);
            }
        }
    }

    public static void uploadFiles(FileSystem fileSystem, String srcDir, Path dstDir) throws IOException {
        //判断文件夹是否存在,不存在需要创建
        File srcPath = new File(srcDir);
        File[] files = srcPath.listFiles();
        Path[] paths = new Path[files.length];
        for (int i = 0; i < files.length; i++) {
            paths[i] = new Path(srcDir + File.separator + files[i].getName());
        }
        if (!fileSystem.exists(dstDir)) {
            fileSystem.mkdirs(dstDir);
        }
        //存在就直接上传
        fileSystem.copyFromLocalFile(false, true, paths, dstDir);
    }

    public static List<Path> find(FileSystem fileSystem, Path path, String suffix) {
        List<Path> paths = new ArrayList<>();
        if (fileSystem != null && path != null) {
            try {
                if (fileSystem.exists(path) && fileSystem.isDirectory(path)) {

                    //后续操作
                    PathFilter pathFilter = null;
                    if (StringUtils.isNotEmpty(suffix)) {
                        //后缀处理
                        pathFilter = new PathFilter() {
                            @Override
                            public boolean accept(Path path) {
                                return path.toString().endsWith(suffix);
                            }
                        };
                    }
                    FileStatus[] fileStatuses = null;
                    if (pathFilter != null) {
                        fileStatuses = fileSystem.listStatus(path, pathFilter);
                    } else {
                        fileStatuses = fileSystem.listStatus(path);
                    }
                    if (fileStatuses != null) {
                        for (FileStatus file : fileStatuses) {
                            paths.add(file.getPath());
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return paths;
    }
}