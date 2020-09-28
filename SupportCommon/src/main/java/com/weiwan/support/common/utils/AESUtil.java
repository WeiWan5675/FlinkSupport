
package com.weiwan.support.common.utils;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.SecureRandom;

/**
 * AES加密和解密工具类
 *
 * @author houlei
 */
public class AESUtil {
    private static final Logger logger = LoggerFactory.getLogger(com.weiwan.common.utils.AESUtil.class);

    // AES密码器
//    private static Cipher cipher;

    // 字符串编码
    private static final String KEY_CHARSET = "UTF-8";

    // 算法方式
    private static final String KEY_ALGORITHM = "AES";

    // 算法/模式/填充
    private static final String CIPHER_ALGORITHM_CBC = "AES/CBC/PKCS5Padding";

    // 私钥大小128/192/256(bits)位 即：16/24/32bytes，暂时使用128，如果扩大需要更换java/jre里面的jar包
    private static final Integer PRIVATE_KEY_SIZE_BIT = 128;
    private static final Integer PRIVATE_KEY_SIZE_BYTE = 16;


    public static void main(String[] args) throws Exception {
        System.out.println(decrypt("2acfc577a341e729","WB3hRi5obfYp2lAsJIQ/UnUVgmFFWmOgHF2adJMMvxpF3/jz7ti0JCpTVqk3VvtafHMjK5XsMb1meVqRzC+Cxfxv2/8X6g6XBWis8iZU3ScFzVM92/cfW+M4dyIP3VunX/GMGZKjrQ1jWY+sqOM3RsRlxNd8pcpi+FF4sNM1BN0ewSK0FFM4npLLnlPdBpNfeiYQTBkAXhDak03qYH2KqGsun80mOy8ZeycroQG+GS8eXgf64tSc2Wsw0EGYP7hlXdCw6lFutvE8nbyRcjwcAmQ7KTOtq3n2LUADqEoCyyDwgE5uw2WjeoNX2HjvkfBjyMGwWFnnhTkEdT6sicJa6OWCr1fuOn3dwyLvzvJTRp+L3AYQ3I56p6OqzDQjv2FtaBkIcn68uT1BBBI+wROR1J+ubeDIkUygVgyXmu2MwZYS+fZsHdtStVuf94+cDN3oYZu1+eCm4cvC4zCQYE3dFE+qXDkAIVJfGois6AKoSvzAQ9Yg9rFpeDe2F+vj/dsQaRE1usY4hxs3TwRL7B7hmXPORXT/Um+HJ/6b+VIv5fJ9p0ME9e/t8qOulFsBuKUkXcLxSeeSRmPZk2ORgVneq5VdyqLbupR6hiLTxElhFN0xOpq103FIDmu1GRnkfuQ21/HaLAiNN3IJG424aB2SjrsH5Nd9GncgL/mYHzHriwi01M5sfYislAR/niNiaORzC80N3mQ0BpUFd3hLKa8thwk0jnH8dT4B3YrgEurW4pBzb9ftko0tnX6FO9I9m7GHb4GtoJynlVnoZeKLkUo95p/OLgWblOw+cJ3JFeJa+aWVIfsN6HMLxwgFQ25yC9WGxyJJdmCXXOSfYUcD8m668onMICANkSApwRRDOTywYpf0/aQxhaa7Ujd0vHSu88ukr93gC1QJcMtspUQYYs4kzMw2YwlixOysTgSXOBqge3McxCR72dzctmBAcTH6sPEOyB9iYnY0iWrUxKze8YQZHBWA8ZqxFeukieCaKXbhH8DPl90b8EeXp+DBFgLpqPvdZC9cWo0pAt5D/DQXXOpoqZiO1z1qUiGbM3X9DhTLFTizXW8L9rfjJQfwXXO260/xzr++c+QBMKEz5XRW1bNo6DhRQOZSuRwDXNt2AGKYSqyieVIGZb4ZuMDmepfxH64QqFTO9PCKlUTUTlaCzUX2BnxIjwONIAAIZEOsMM8LxBIpFKPAdTX7hbz28J8p1Bhz9CABBLXTS3XcHHPt67vLi0hM3hK+tE53fu+WegamHb1x/g6toueSzhGQFpbGNllyMDImxDS2QpkX+AfeZ/9Lvqhg9W2XipbqooFmA9yOyIYgrwzmrTZVBu9d2P9pVlE4HhXxgi7TpWemy67aNRKv9TKMjWW+66jTescT8KQHa7l2kQ300R70vTrdqeqhumnaJ7p1SRHd74zb93qYRymE3lHPgLrGXtC2KkRcTIra7B8TjSJyRkBVerdkeeYkNDFyG9ptIWryCPcgOcBIYFIeThnQ4leN4CAEcFwrz47ZUTU="));
    }

    public static Key getKey(String strKey) throws Exception {
        //创建密钥生成器
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        //初始化密钥
        keyGenerator.init(new SecureRandom(strKey.getBytes()));
        //生成密钥
        SecretKey getKey = keyGenerator.generateKey();
        System.out.println("生成密钥:" + parseByte2HexStr(getKey.getEncoded()) + "----" + parseByte2HexStr(getKey.getEncoded()).length());
        return getKey;
    }

    /**
     * 加密
     *
     * @param secretKey 密钥：加密的规则 16位
     * @param plainText 明文：要加密的内容
     * @return cipherText
     * 密文：加密后的内容，如有异常返回空串：""
     */
    public static String encrypt(String secretKey, String plainText) {
        if (secretKey.length() != PRIVATE_KEY_SIZE_BYTE) {
            throw new RuntimeException("AESUtil:Invalid AES secretKey length (must be 16 bytes)");
        }

        // 密文字符串
        String cipherText = "";
        try {
            // 加密模式初始化参数
           Cipher cipher=initParam(secretKey, Cipher.ENCRYPT_MODE);
            // 获取加密内容的字节数组
            byte[] bytePlainText = plainText.getBytes(KEY_CHARSET);
            // 执行加密
            byte[] byteCipherText = cipher.doFinal(bytePlainText);
            cipherText = Base64.encodeBase64String(byteCipherText);
        } catch (Exception e) {
            throw new RuntimeException("AESUtil:encrypt fail!", e);
        }
        return cipherText;
    }

    /**
     * 解密
     *
     * @param secretKey  密钥：加密的规则 16位
     * @param cipherText 密文：加密后的内容，即需要解密的内容
     * @return plainText
     * 明文：解密后的内容即加密前的内容，如有异常返回空串：""
     */
    public static String decrypt(String secretKey, String cipherText) {
        if (secretKey.length() != PRIVATE_KEY_SIZE_BYTE) {
            throw new RuntimeException("AESUtil:Invalid AES secretKey length (must be 16 bytes)");
        }
        // 明文字符串
        String plainText = "";
        try {
            Cipher cipher = initParam(secretKey, Cipher.DECRYPT_MODE);
            // 将加密并编码后的内容解码成字节数组
            byte[] byteCipherText = Base64.decodeBase64(cipherText);
            // 解密
            byte[] bytePlainText = cipher.doFinal(byteCipherText);
            plainText = new String(bytePlainText, KEY_CHARSET);
        } catch (Exception e){
            logger.error("AESUtil:decrypt fail! secretKey: {}, cipherText: {},exception: {}",secretKey,cipherText,e);
            throw new RuntimeException("AESUtil:decrypt fail!", e);
        }
        return plainText;
    }

    /**
     * 初始化参数
     *
     * @param secretKey 密钥：加密的规则 16位
     * @param mode      加密模式：加密or解密
     */
    public static Cipher initParam(String secretKey, int mode) {
        try {
            // 防止Linux下生成随机key
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(secretKey.getBytes());
            // 获取key生成器
            KeyGenerator keygen = KeyGenerator.getInstance(KEY_ALGORITHM);
            keygen.init(PRIVATE_KEY_SIZE_BIT, secureRandom);

            // 获得原始对称密钥的字节数组
            byte[] raw = secretKey.getBytes();

            // 根据字节数组生成AES内部密钥
            SecretKeySpec key = new SecretKeySpec(raw, KEY_ALGORITHM);
            // 根据指定算法"AES/CBC/PKCS5Padding"实例化密码器
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM_CBC);
            IvParameterSpec iv = new IvParameterSpec(secretKey.getBytes());
            cipher.init(mode, key, iv);
            return  cipher;
        } catch (Exception e) {
            throw new RuntimeException("AESUtil:initParam fail!", e);
        }
    }

    /**
     * 生成密钥
     * 自动生成base64 编码后的AES128位密钥
     *
     * @throws //NoSuchAlgorithmException
     * @throws //UnsupportedEncodingException
     */
    public static String getAESKey() throws Exception {
        KeyGenerator kg = KeyGenerator.getInstance("AES");
        kg.init(128);//要生成多少位，只需要修改这里即可128, 192或256
        SecretKey sk = kg.generateKey();
        byte[] b = sk.getEncoded();
        return parseByte2HexStr(b);
    }



    /**
     * 将16进制转换为二进制
     *
     * @param hexStr
     * @return
     */
    public static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1)
            return null;
        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }

    /**
     * 将二进制转换成16进制
     *
     * @param buf
     * @return
     */
    public static String parseByte2HexStr(byte buf[]) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }
}


