package com.weiwan.support.core.enums;

import org.apache.hadoop.io.compress.*;

import java.io.Serializable;

/**
 * @Author: xiaozhennan
 * @Date: 2020/8/3 11:24
 * @Package: org.weiwan.argus.writer.hive
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
