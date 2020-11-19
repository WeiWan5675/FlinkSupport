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

import org.apache.hadoop.io.compress.*;

import java.io.Serializable;

/**
 * @Author: xiaozhennan
 * @Date: 2020/8/3 11:24
 * @Package: com.weiwan.support.writer.hive
 * @ClassName: CompressType
 * @Description:
 **/
public enum CompressType implements Serializable {
    GZIP("GZIP", "GIZP", GzipCodec.class),
    BIZP2("BIZP2", "BIZP2", BZip2Codec.class),
    SNAPPY("SNAPPY", "SNAPPY", SnappyCodec.class),
    LZ4("LZ4", "LZ4", Lz4Codec.class),
    NONE("NONE", "NONE", DefaultCodec.class);

    private String code;
    private String msg;
    private Class<? extends CompressionCodec> codecClass;

    CompressType(String code, String msg, Class<? extends CompressionCodec> codecClass) {
        this.code = code;
        this.msg = msg;
        this.codecClass = codecClass;
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Class<? extends CompressionCodec> getCodecClass() {
        return codecClass;
    }

    public void setCodecClass(Class<? extends CompressionCodec> codecClass) {
        this.codecClass = codecClass;
    }
}
