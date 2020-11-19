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
package com.weiwan.support.core.enums;

import java.io.Serializable;

/**
 * @Author: xiaozhennan
 * @Date: 2020/8/3 11:26
 * @Package: com.weiwan.support.writer.hive
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
