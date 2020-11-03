package com.weiwan.support.common.utils;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;

public class Base64Util {
    private static Logger logger = LoggerFactory.getLogger(Base64Util.class);
    //    private static final String charset = "utf-8";
    private static final String charset = "utf-8";

    /**
     * 解密
     *
     * @param data
     * @return
     * @author fanddong
     */
    public static String decode(String data) {
        try {
            if (null == data) {
                return null;
            }

            return new String(Base64.decodeBase64(data.getBytes(charset)), charset);
        } catch (UnsupportedEncodingException e) {
            logger.error(String.format("字符串：%s，解密异常", data));
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 加密
     *
     * @param data
     * @return
     * @author fanddong
     */
    public static String encode(String data) {
        try {
            if (null == data) {
                return null;
            }
            return new String(Base64.encodeBase64(data.getBytes(charset)), charset);
        } catch (UnsupportedEncodingException e) {
            logger.error(String.format("字符串：%s，加密异常", data));
            e.printStackTrace();
        }

        return null;
    }

}