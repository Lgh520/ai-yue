package com.project.aiyue.utils;


import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.Cipher;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import sun.misc.BASE64Encoder;


public class RSAUtils {
//    protected static Logger logger = LoggerFactory.getLogger(RSAUtils.class);
//    public static final String SIGN_ALGORITHMS = "SHA256withRSA";
//    private static String privateKey;
//    private static String publicKey;
//    private static final String charSet="UTF-8";
//    public static InputStream getProperStream(String path){
//        return RSAUtils.class.getClassLoader().getResourceAsStream(path);
//    }
//
//    /***
//     * RSA签名
//     * @param content 待签名数据
//     * @param key 私钥
//     * @return 签名值
//     */
//    public static String sign(String content,String key)throws Exception{
//        initPrivateKey(key);
//        PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(Base64.decodeBase64(privateKey));
//        KeyFactory keyf = KeyFactory.getInstance("RSA");
//        PrivateKey priKey = keyf.generatePrivate(priPKCS8);
//        java.security.Signature signature = java.security.Signature.getInstance(SIGN_ALGORITHMS);
//        signature.initSign(priKey);
//        signature.update(content.getBytes(charSet));
//        byte[] signed = signature.sign();
////        return Base64.encodeBase64String(signed);
//        return DesUtil.byte2hex(signed);
//
//    }
//
//    /***
//     * RSA签名
//     * @param content 待签名数据
//     * @return 签名值
//     */
//    public static String signBase64(String content)throws Exception{
//        PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(Base64.decodeBase64(privateKey));
//        KeyFactory keyf = KeyFactory.getInstance("RSA");
//        PrivateKey priKey = keyf.generatePrivate(priPKCS8);
//        java.security.Signature signature = java.security.Signature.getInstance(SIGN_ALGORITHMS);
//        signature.initSign(priKey);
//        signature.update(content.getBytes(charSet));
//        byte[] signed = signature.sign();
//        return Base64.encodeBase64String(signed);
//
//    }
//
//    /**
//     * 从文件中加载私钥
//     * @param in 私钥文件流
//     * @return 是否成功
//     * @throws Exception
//     */
//    public static void loadPrivateKey(InputStream in) throws Exception{
//        try {
//            BufferedReader br= new BufferedReader(new InputStreamReader(in));
//            String readLine= null;
//            StringBuilder sb= new StringBuilder();
//            while((readLine= br.readLine())!=null){
//                if(readLine.charAt(0)=='-'){
//                    continue;
//                }else{
//                    sb.append(readLine);
//                    sb.append('\r');
//                }
//            }
//            privateKey = sb.toString();
//        } catch (IOException e) {
//            logger.error("私钥数据读取错误",e);
//        } catch (NullPointerException e) {
//            logger.error("私钥输入流为空",e);
//        }
//    }
//
//
//    /**
//     *
//     * 功能描述: 初始化私钥
//     *
//     * @param
//     * @return
//     * @throws
//     * @auther 刘晓禾
//     * @date  2019/11/12
//     */
//    public static void initPrivateKey(String key){
//        if(!StringUtils.isEmpty(key)){
//            privateKey = key;
//        }
//    }
//
//
//
//    /**
//     * 初始化私钥
//     * @param privatePath 私钥路径
//     * @throws Exception
//     */
//    public static void initSign(String privatePath) throws Exception{
//        InputStream privateInput = new FileInputStream(privatePath);
//        loadPrivateKey(privateInput);
//    }
//    /**
//     * RSA验签名检查
//     * @param content 待签名数据
//     * @param sign 签名值
//     * @return
//     */
//    public static boolean doCheck(String content, String sign){
//        Boolean bool = false;
//        try {
//            logger.debug("content:" + content);
//            logger.debug("sign:" + sign);
//            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
//            byte[] encodedKey = Base64.decodeBase64(publicKey);
//            PublicKey pubKey = keyFactory
//                    .generatePublic(new X509EncodedKeySpec(encodedKey));
//            java.security.Signature signature = java.security.Signature
//                    .getInstance(SIGN_ALGORITHMS);
//            signature.initVerify(pubKey);
//            signature.update(content.getBytes(charSet));
//            bool = signature.verify(DesUtil.hex2byte(sign));
//        } catch (Exception e) {
//            throw new ResourceException("8888","签名异常");
//        }
//        return bool;
//    }
//
//    /**
//     * RSA验签名检查
//     * @param content 待签名数据
//     * @param sign 签名值
//     * @return
//     */
//    public static boolean doCheckBase64(String content, String sign){
//        Boolean bool = false;
//        try {
//            logger.debug("content:" + content);
//            logger.debug("sign:" + sign);
//            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
//            byte[] encodedKey = Base64.decodeBase64(publicKey);
//            PublicKey pubKey = keyFactory
//                    .generatePublic(new X509EncodedKeySpec(encodedKey));
//            java.security.Signature signature = java.security.Signature
//                    .getInstance(SIGN_ALGORITHMS);
//            signature.initVerify(pubKey);
//            signature.update(content.getBytes(charSet));
//            bool = signature.verify(Base64.decodeBase64(sign.getBytes("utf-8")));
//        } catch (Exception e) {
//            throw new ResourceException("8888","签名异常");
//        }
//        return bool;
//    }
//    /**
//     * RSA验签名数据
//     * @param content 待签名数据
//     * @param sign 签名值
//     * @return
//     */
//    public static PublicKey getPublicKey(String content, String sign)throws Exception {
//        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
//        byte[] encodedKey = Base64.decodeBase64(publicKey);
//        PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));
//        return pubKey;
//    }
//
//    /**
//     * 从文件中加载公钥
//     * @param in 公钥文件流
//     * @return 是否成功
//     * @throws Exception
//     */
//    public static void loadPublicKey(InputStream in) throws Exception{
//        try {
//            BufferedReader br= new BufferedReader(new InputStreamReader(in));
//            String readLine= null;
//            StringBuilder sb= new StringBuilder();
//            while((readLine= br.readLine())!=null){
//                if(readLine.charAt(0)=='-'){
//                    continue;
//                }else{
//                    sb.append(readLine);
//                    sb.append('\r');
//                }
//            }
//            publicKey = sb.toString();
//        } catch (IOException e) {
//            logger.error("私钥数据读取错误",e);
//        } catch (NullPointerException e) {
//            logger.error("公钥输入流为空",e);
//        }
//    }
//
//
//    /**
//     * 初始化公钥
//     * @param publicPath
//     * @throws Exception
//     */
//    public static void initPubKey(String publicPath) throws Exception{
//        InputStream publicInput = new FileInputStream(publicPath);
//        loadPublicKey(publicInput);
//    }
//
//
//
//    //以下内容为RSA加解密
//
//    /**
//     * 从字符串中加载公钥
//     */
//    public static RSAPublicKey loadPublicKeyByStr()
//            throws Exception {
//        try {
//            byte[] buffer = Base64.decodeBase64(publicKey);
//            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
//            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
//            return (RSAPublicKey) keyFactory.generatePublic(keySpec);
//        } catch (NoSuchAlgorithmException e) {
//            throw new Exception("无此算法");
//        } catch (InvalidKeySpecException e) {
//            throw new Exception("公钥非法");
//        } catch (NullPointerException e) {
//            throw new Exception("公钥数据为空");
//        }
//    }
//
//    /**
//     * 公钥加密
//     *
//     * @param data
//     * @return
//     * @throws Exception
//     */
//    public static String encryptByPublicKey(String data)
//            throws Exception {
//        RSAPublicKey publicKey = loadPublicKeyByStr();
//        Cipher cipher = Cipher.getInstance("RSA");
//        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
//        // 模长
//        int key_len = publicKey.getModulus().bitLength() / 8;
//        // 加密数据长度 <= 模长-11
//        String[] datas = splitString(data, key_len - 11);
//        String mi = "";
//        //如果明文长度大于模长-11则要分组加密
//        for (String s : datas) {
//            mi += bcd2Str(cipher.doFinal(s.getBytes(charSet)));
//        }
//        return mi;
//    }
//
//    /**
//     * 公钥解密
//     *
//     * @param data
//     * @return
//     * @throws Exception
//     */
//    public static String decryptByPublicKey(String data)
//            throws Exception {
//        RSAPublicKey publicKey = loadPublicKeyByStr();
//        Cipher cipher = Cipher.getInstance("RSA");
//        cipher.init(Cipher.DECRYPT_MODE, publicKey);
//        //模长
//        int key_len = publicKey.getModulus().bitLength() / 8;
//        byte[] bytes = data.getBytes(charSet);
//        byte[] bcd = ASCII_To_BCD(bytes, bytes.length);
//        //如果密文长度大于模长则要分组解密
//        String ming = "";
//        byte[][] arrays = splitArray(bcd, key_len);
//        for(byte[] arr : arrays){
//            ming += new String(cipher.doFinal(arr));
//        }
//        return ming;
//    }
//    /**
//     * 从字符串中加载私钥
//     */
//    public static RSAPrivateKey loadPrivateKeyByStr()
//            throws Exception {
//        try {
//            byte[] buffer = Base64.decodeBase64(privateKey);
//            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(buffer);
//            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
//            return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
//        } catch (NoSuchAlgorithmException e) {
//            throw new Exception("无此算法");
//        } catch (InvalidKeySpecException e) {
//            throw new Exception("私钥非法");
//        } catch (NullPointerException e) {
//            throw new Exception("私钥数据为空");
//        }
//    }
//    /**
//     * 私钥加密
//     *
//     * @param data
//     * @return
//     * @throws Exception
//     */
//    public static String encryptByPrivateKey(String data)
//            throws Exception {
//        RSAPrivateKey privateKey = loadPrivateKeyByStr();
//        Cipher cipher = Cipher.getInstance("RSA");
//        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
//        // 模长
//        int key_len = privateKey.getModulus().bitLength() / 8;
//        // 加密数据长度 <= 模长-11
//        String[] datas = splitString(data, key_len - 11);
//        String mi = "";
//        //如果明文长度大于模长-11则要分组加密
//        for (String s : datas) {
//            mi += bcd2Str(cipher.doFinal(s.getBytes(charSet)));
//        }
//        return mi;
//    }
//
//    /**
//     * 私钥解密
//     *
//     * @param data
//     * @return
//     * @throws Exception
//     */
//    public static String decryptByPrivateKey(String data)
//            throws Exception {
//        RSAPrivateKey privateKey = loadPrivateKeyByStr();
//        Cipher cipher = Cipher.getInstance("RSA");
//        cipher.init(Cipher.DECRYPT_MODE, privateKey);
//        //模长
//        int key_len = privateKey.getModulus().bitLength() / 8;
//        byte[] bytes = data.getBytes(charSet);
//        byte[] bcd = ASCII_To_BCD(bytes, bytes.length);
//        //如果密文长度大于模长则要分组解密
//        String ming = "";
//        byte[][] arrays = splitArray(bcd, key_len);
//        for(byte[] arr : arrays){
//            ming += new String(cipher.doFinal(arr));
//        }
//        return ming;
//    }
//    /**
//     * BCD转字符串
//     */
//    public static String bcd2Str(byte[] bytes) {
//        char temp[] = new char[bytes.length * 2], val;
//
//        for (int i = 0; i < bytes.length; i++) {
//            val = (char) (((bytes[i] & 0xf0) >> 4) & 0x0f);
//            temp[i * 2] = (char) (val > 9 ? val + 'A' - 10 : val + '0');
//
//            val = (char) (bytes[i] & 0x0f);
//            temp[i * 2 + 1] = (char) (val > 9 ? val + 'A' - 10 : val + '0');
//        }
//        return new String(temp);
//    }
//
//    public static byte asc_to_bcd(byte asc) {
//        byte bcd;
//
//        if ((asc >= '0') && (asc <= '9')){
//            bcd = (byte) (asc - '0');
//        }
//        else if ((asc >= 'A') && (asc <= 'F')){
//            bcd = (byte) (asc - 'A' + 10);
//        }
//        else if ((asc >= 'a') && (asc <= 'f')){
//            bcd = (byte) (asc - 'a' + 10);
//        }
//        else{
//            bcd = (byte) (asc - 48);
//        }
//        return bcd;
//    }
//    /**
//     * ASCII码转BCD码
//     *
//     */
//    public static byte[] ASCII_To_BCD(byte[] ascii, int asc_len) {
//        byte[] bcd = new byte[asc_len / 2];
//        int j = 0;
//        for (int i = 0; i < (asc_len + 1) / 2; i++) {
//            bcd[i] = asc_to_bcd(ascii[j++]);
//            bcd[i] = (byte) (((j >= asc_len) ? 0x00 : asc_to_bcd(ascii[j++])) + (bcd[i] << 4));
//        }
//        return bcd;
//    }
//    /**
//     * 拆分字符串
//     */
//    public static String[] splitString(String string, int len) {
//        int x = string.length() / len;
//        int y = string.length() % len;
//        int z = 0;
//        if (y != 0) {
//            z = 1;
//        }
//        String[] strings = new String[x + z];
//        String str = "";
//        for (int i=0; i<x+z; i++) {
//            if (i==x+z-1 && y!=0) {
//                str = string.substring(i*len, i*len+y);
//            }else{
//                str = string.substring(i*len, i*len+len);
//            }
//            strings[i] = str;
//        }
//        return strings;
//    }
//    /**
//     *拆分数组
//     */
//    public static byte[][] splitArray(byte[] data,int len){
//        int x = data.length / len;
//        int y = data.length % len;
//        int z = 0;
//        if(y!=0){
//            z = 1;
//        }
//        byte[][] arrays = new byte[x+z][];
//        byte[] arr;
//        for(int i=0; i<x+z; i++){
//            arr = new byte[len];
//            if(i==x+z-1 && y!=0){
//                System.arraycopy(data, i*len, arr, 0, y);
//            }else{
//                System.arraycopy(data, i*len, arr, 0, len);
//            }
//            arrays[i] = arr;
//        }
//        return arrays;
//    }
//
//    public static void loadPublicKeyCer(InputStream publicInput) throws Exception{
//        try {
//            CertificateFactory cf = CertificateFactory.getInstance("X.509");
//            X509Certificate cert = (X509Certificate)cf.generateCertificate(publicInput);
//            PublicKey p = cert.getPublicKey();
//            BASE64Encoder base64Encoder=new BASE64Encoder();
//            String publicKeyString = base64Encoder.encode(p.getEncoded());
//            publicKey = publicKeyString;
//        } catch (Exception e) {
//            logger.error("cer公钥数据读取错误",e);
//        }
//
//    }
//
//    /**
//     * 初始化公钥
//     * @param publicPath
//     * @throws Exception
//     */
//    public static void initPubKeyCer(String publicPath) throws Exception{
//        InputStream publicInput = new FileInputStream(publicPath);
//        loadPublicKeyCer(publicInput);
//    }
//
//    public static void main(String[] args) throws Exception {
//        String content = "apiVersion=2.0.0|feeType=FEE01|merId=10002149|ordAmt=1.00|orgAbbr=YH|reqTime=20191111074235|signAlgorithm=1|usrNm=ZCbhU7gLRrfXrL632iK/7g==|usrNo=tRwCaBVIx8U7jxchshY9GQ==";
//
//        String key = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQC/ITCr2OUr1Bd9veG6Z0fitF86N7404W29L+MXZfIoKD1++4PPZ4guNSSZpli4PS9pNdJPr4LHgNJqcpXGM1dJ9vczEcGMsqnxkE1lkJ49Bv9DVffwKpxoMVg1zr34gclyeOfTPd5mozHATb9h7PVMBGUlscJahudaDx+rMPtIZDNb4cmfU68cnzbVX53hebVbhjyx6kn51C/uLPoW1kaCYGkMPRNGPcpMkh8aNXrOdpRoTmFuUa+1EK+iJgqimndlXfKiXx55UiFV579dGsBiSjXA5HhLmgSm7dtFePUhlYt5iVafIzh7QU6JD20NjJKVTr60P0NAXyHIv0rcMeKrAgMBAAECggEACkS9Oa5YeadWb8IA26Pq38ozB4yxImQuIijteXdXsEKhMy5OIZl9XoYqZjrir1tSLcPffbp/Ozq3k1J+q3xcpzOmik7c6DM9ICZRkjBX4A19L1/yBywy5QIprBJkcHKAoS7PvbCoPzlrVdqXeqphb2kZChJnz7dJvLPtTqQ+C+CGe4C1OKtrwnGNr2O6L9DD3Mqwibeiw9BHZqgm4D9Kh3w4fz84vnQ+v7OpfgpriiAGJWuHPsA4ZPSpzeJFQciqwzCBG/x1cVgg6ystjvheQ66U5nlXX4cWs7B3sR8w8FHfT1/tEIrq+fwKGy6TGs/YKycwnw8LO79j1ODx9QB9yQKBgQDm72DrZtLE6aiqKyDKxqzf0hRxxw7SRBpj4BCKcFgIUijt3RZg6QjJ4EXNuOOcsbLZzHEGNlpuYFVdG7FpHULNnCn/xA13Wb7vmQ+/mdPw2utw2f/d0hu/KiTLcXatUwzkht1KZ36zQxuvbD4P67Mo0KFDDVx1EKk2SrjCLYD/bQKBgQDT381gHrf+yO8fwTDSs6IIiVTndiaJ2pW4EMLSpdOd4vKhGmClAnhQL6GEFAn9xdbUwD3cwvJyLVIPhcu5JYVIg9JIMLoZ3mNoLF5heD+rGWBTjFsNuV5oxCvFCvvOIaa09lzpNh35s9HbXskzJwPgD+cn4DP/WehorG23b8RjdwKBgQDY1F3t6OdxF+rTjBcIDa73oCquAbb62hLlP9aIBZ/CgFIJtJJ0f5lO4xLna/fagkZplTlyGcqi5DSCbaQQC/dTUpNkoFH8g/lwwgg1YFvNQ9/YX/eL8OE4hntxwaEqcFYkpDRWzgH6ksIF3d4P2MCpS+p+KKdtCy212kbLmyoJqQKBgBx6g5d86Yxko8en5xEbNhdhpjjqZ6m16NPx5M39niElxSkka4MiftuAW3xuWR10PzkfkfEffwkb0GDnwgdj5LNnjJdJfWpGuyPgetstF5m+8nSodAC3PtpkK+HzZP9DBK5kQv7R2Fuc8QNEjVTD1MCEQrTYbQTQp+2R57l1W3dnAoGBAIoISy6Y1SRUhhL59rXZfQeEdpLEw7kueLQqxUs8vjH0M7a3TzKxXRzrHMchMTtcO0W5FonWU1NDWVBAVKCySCtPDU3TxOQtUA9Qg6hqhIPX0UaPrqhoZN8Haw9e6oUX50dvs1cKf9OT2a5SmNVnRxquXV7Y8wRiT8frOQo8Q0j4";
//        String sign = RSAUtils.sign(content,key);
//
//        System.out.println(sign);
//
////        boolean res = RSAUtils.doCheck(content, sign);
////        System.out.println("验签结果:"+res);
//
//    }
//
//

}
