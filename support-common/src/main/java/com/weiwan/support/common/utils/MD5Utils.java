package com.weiwan.support.common.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 报文加解密工具类
 * @date 2018-12-25 10:54:09
 */
public class MD5Utils {

    public static String md5(String str) {
        if (str == null) {
            return null;
        }
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(str.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            return str;
        } catch (UnsupportedEncodingException e) {
            return str;
        }
        byte[] byteArray = messageDigest.digest();
        StringBuffer md5StrBuff = new StringBuffer();
        int aa;

        for (int i = 0; i < byteArray.length; i++) {
            aa = byteArray[i];
            aa = aa & 0xff;
            if (Integer.toHexString(aa).length() == 1)
                md5StrBuff.append("0").append(Integer.toHexString(aa));
            else
                md5StrBuff.append(Integer.toHexString(aa));
        }
        return md5StrBuff.toString();
    }
}
