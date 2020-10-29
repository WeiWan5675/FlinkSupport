package com.weiwan.support.utils.hadoop;


import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.io.IOException;

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




}