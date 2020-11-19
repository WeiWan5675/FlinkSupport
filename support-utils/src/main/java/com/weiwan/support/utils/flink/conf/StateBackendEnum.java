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
package com.weiwan.support.utils.flink.conf;

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
