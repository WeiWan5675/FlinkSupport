package com.weiwan.support.core.enums;

import java.io.Serializable;

/**
 * @Author: xiaozhennan
 * @Date: 2020/8/3 11:26
 * @Package: org.weiwan.argus.writer.hive
 * @ClassName: FileType
 * @Description:
 **/
public enum FileType implements Serializable {
    TEXT("TEXT", "文本类型"),
    ORC("ORC", "ORC"),
    PARQUET("PARQUET", "PARQUET");
    private String msg;
    private String code;

    FileType(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
