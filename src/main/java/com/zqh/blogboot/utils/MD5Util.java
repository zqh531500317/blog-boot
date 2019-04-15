package com.zqh.blogboot.utils;

import sun.misc.BASE64Encoder;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

/**
 * 加密，解密工具类
 */
public class MD5Util {
    public static String EncoderByMd5(String str) throws Exception{
        //确定计算方法
        MessageDigest md5= MessageDigest.getInstance("MD5");
        BASE64Encoder base64en = new BASE64Encoder();
        //加密后的字符串
        return base64en.encode(md5.digest(str.getBytes(StandardCharsets.UTF_8)));
    }

}
