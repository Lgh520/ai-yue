package com.project.aiyue.utils;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;


public class SecurityUtil {

    public static final String DEFAULT_KEY = "b62a241b14a58679175c6dab96bd12f3";

    public static final String DEFAULT_ENCODE = "UTF-8";
    
    public static void main(String[] args) throws IOException{
        String content = "1868152612416546545648";
        String Key = DEFAULT_KEY;

        // 加密
        System.out.println("加密前：" + content);
        String encryptResult = SecurityUtil.enSecret(content, Key);
        System.out.println("加密后：" + encryptResult);

        // 解密
        String decryptResult = SecurityUtil.deSecret(encryptResult, Key);
        System.out.println("解密后：" + new String(decryptResult));
    }

    /**
     * @param content 需要加密的内容
     * @param keyWord 加密密钥
     * @return String 加密后的字符串
     * @throws UnsupportedEncodingException
     */
    public static String enSecret(String content, String keyWord) {
        return parseByte2HexStr(encrypt(content, keyWord));
    }

    /**
     * @param content 待解密内容(字符串)
     * @param keyWord 解密密钥
     * @return byte[]
     * @throws IOException
     */
    public static String deSecret(String content, String keyWord) {
        if (StringUtils.isBlank(content)) {
            return null;
        }
        return new String(decrypt(parseHexStr2Byte(content), keyWord));
    }

    /**
     * @param content 需要加密的内容
     * @return String 加密后的字符串
     * @throws UnsupportedEncodingException
     */
    public static String enSecret(String content) {
        return parseByte2HexStr(encrypt(content, DEFAULT_KEY));
    }

    /**
     * @param content 待解密内容(字符串)
     * @return byte[]
     * @throws IOException
     */
    public static String deSecret(String content) {
        if (StringUtils.isBlank(content)) {
            return null;
        }
        return new String(decrypt(parseHexStr2Byte(content), DEFAULT_KEY));
    }

    private static byte[] encrypt(String content, String keyWord) {
        try {
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(keyWord.getBytes());
            kgen.init(128, secureRandom);
            SecretKey secretKey = kgen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
            Cipher cipher = Cipher.getInstance("AES");// 创建密码器
            byte[] byteContent = content.getBytes("utf-8");
            cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化
            byte[] result = cipher.doFinal(byteContent);
            return result; // 加密
        } catch (Exception e) {
            ;
        } 
        return null;
    }

    /**
     * 解密
     * 
     * @param content 待解密内容
     * @param keyWord 解密密钥
     * @return byte[]
     */
    private static byte[] decrypt(byte[] content, String keyWord) {
        try {
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(keyWord.getBytes());
            kgen.init(128, secureRandom);
            SecretKey secretKey = kgen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
            Cipher cipher = Cipher.getInstance("AES");// 创建密码器
            cipher.init(Cipher.DECRYPT_MODE, key);// 初始化
            byte[] result = cipher.doFinal(content);
            return result; // 加密
        } catch (Exception e) {
            ;
        } 
        return null;
    }

    /**
     * 将二进制转换成16进制
     * 
     * @param buf
     * @return String
     */
    private static String parseByte2HexStr(byte buf[]) {
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

    /**
     * 将16进制转换为二进制
     * 
     * @param hexStr
     * @return byte[]
     */
    private static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1)
        {    return null;}
        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }

}
