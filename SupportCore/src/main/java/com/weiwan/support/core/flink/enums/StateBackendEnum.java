package com.weiwan.support.core.flink.enums;

/**
 * @Author: xiaozhennan
 * @Date: 2020/7/6 10:42
 * @Package: com.hopson.dc.flink.common.enums
 * @ClassName: BackendState
 * @Description:
 **/
public enum StateBackendEnum {
    MEMORY_TYPE("Memory", "内存"),
    FILESYSTEM_TYPE("FileSystem", "文件系统"),
    ROCKSDB_TYPE("RocksDB", "RocksDB");

    private String code;
    private String msg;

    StateBackendEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }


    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
