package com.project.aiyue.utils;

import com.alibaba.fastjson2.JSONObject;
import org.springframework.util.Base64Utils;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
import javax.crypto.Cipher;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: RSAUtil
 * @since: 2022/3/3 10:38
 */
public class RSAUtil {
//
//    public static final String KEY_ALGORITHM = "RSA";
//    private static final String PUBLIC_KEY = "RSAPublicKey";
//    private static final String PRIVATE_KEY = "RSAPrivateKey";
//    public static final String SIGNATURE_INSTANCE = "SHA256WithRSA";
//
//    /**
//     * 获得公钥
//     *
//     * @param keyMap
//     * @return java.lang.String
//     * @MethodName: getPublicKey
//     * @author: 〆、dyh
//     * @since: 2022/3/4 10:30
//     */
//    public static String getPublicKey(Map<String, Object> keyMap) throws Exception {
//        //获得map中的公钥对象 转为key对象
//        Key key = (Key) keyMap.get(PUBLIC_KEY);
//        //编码返回字符串
//        return encryptBASE64(key.getEncoded());
//    }
//
//    /**
//     * 获得私钥
//     *
//     * @param keyMap
//     * @return java.lang.String
//     * @MethodName: getPrivateKey
//     * @author: 〆、dyh
//     * @since: 2022/3/4 10:30
//     */
//    public static String getPrivateKey(Map<String, Object> keyMap) throws Exception {
//        //获得map中的私钥对象 转为key对象
//        Key key = (Key) keyMap.get(PRIVATE_KEY);
//        //编码返回字符串
//        return encryptBASE64(key.getEncoded());
//    }
//
//    /**
//     * 解码返回byte
//     *
//     * @param key
//     * @return byte[]
//     * @MethodName: decryptBASE64
//     * @author: 〆、dyh
//     * @since: 2022/3/4 10:30
//     */
//    public static byte[] decryptBASE64(String key) throws Exception {
//        return (new BASE64Decoder()).decodeBuffer(key);
//    }
//
//    /**
//     * 编码返回字符串
//     *
//     * @param key
//     * @return java.lang.String
//     * @MethodName: encryptBASE64
//     * @author: 〆、dyh
//     * @since: 2022/3/4 10:30
//     */
//    public static String encryptBASE64(byte[] key) throws Exception {
//        return (new BASE64Encoder()).encodeBuffer(key);
//    }
//
//    /**
//     * map对象中存放公私钥
//     *
//     * @param
//     * @return java.util.Map<java.lang.String, java.lang.Object>
//     * @MethodName: initKey
//     * @author: 〆、dyh
//     * @since: 2022/3/4 10:31
//     */
//    public static Map<String, Object> initKey() throws Exception {
//        //获得对象 KeyPairGenerator 参数 RSA 1024个字节
//        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
//        keyPairGen.initialize(1024);
//        //通过对象 KeyPairGenerator 获取对象KeyPair
//        KeyPair keyPair = keyPairGen.generateKeyPair();
//
//        //通过对象 KeyPair 获取RSA公私钥对象RSAPublicKey RSAPrivateKey
//        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
//        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
//        //公私钥对象存入map中
//        Map<String, Object> keyMap = new HashMap<String, Object>(2);
//        keyMap.put(PUBLIC_KEY, publicKey);
//        keyMap.put(PRIVATE_KEY, privateKey);
//        return keyMap;
//    }
//
//    /**
//     * 实例化公钥
//     *
//     * @param publicKey
//     * @return java.security.PublicKey
//     * @MethodName: getPublicKey
//     * @author: 〆、dyh
//     * @since: 2022/3/4 10:31
//     */
//    public static PublicKey getPublicKey(String publicKey) throws Exception {
//        byte[] publicKeyBytes = java.util.Base64.getMimeDecoder().decode(publicKey);
//        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKeyBytes);
//        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
//        return keyFactory.generatePublic(keySpec);
//    }
//
//    /**
//     * 实例化私钥
//     *
//     * @param privateKey
//     * @return java.security.PrivateKey
//     * @MethodName: getPrivateKey
//     * @author: 〆、dyh
//     * @since: 2022/3/4 10:31
//     */
//    public static PrivateKey getPrivateKey(String privateKey) throws Exception {
//        byte[] privateKeyBytes = java.util.Base64.getMimeDecoder().decode(privateKey);
//        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
//        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
//        return keyFactory.generatePrivate(keySpec);
//    }
//
//    /**
//     * 公钥加密
//     *
//     * @param content
//     * @param publicKey
//     * @return byte[]
//     * @MethodName: encryptByPublicKey
//     * @author: 〆、dyh
//     * @since: 2022/3/4 10:31
//     */
//    public static byte[] encryptByPublicKey(byte[] content, String publicKey) throws Exception {
//        Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
//        cipher.init(Cipher.ENCRYPT_MODE, getPublicKey(publicKey));
//        return cipher.doFinal(content);
//    }
//
//    /**
//     * 私钥解密
//     *
//     * @param content
//     * @param privateKey
//     * @return byte[]
//     * @MethodName: decryptByPrivateKey
//     * @author: 〆、dyh
//     * @since: 2022/3/4 10:32
//     */
//    public static byte[] decryptByPrivateKey(byte[] content, String privateKey) throws Exception {
//        Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
//        cipher.init(Cipher.DECRYPT_MODE, getPrivateKey(privateKey));
//        return cipher.doFinal(content);
//    }
//
//    /**
//     * 私钥签名
//     *
//     * @param content
//     * @param privateKey
//     * @return byte[]
//     * @MethodName: sign
//     * @author: 〆、dyh
//     * @since: 2022/3/4 10:33
//     */
//    public static byte[] sign(byte[] content, String privateKey) throws Exception {
//        Signature signature = Signature.getInstance(SIGNATURE_INSTANCE);
//        signature.initSign(getPrivateKey(privateKey));
//        signature.update(content);
//        return signature.sign();
//    }
//
//    public static String getSign(byte[] content, String privateKey) throws Exception {
//        Signature signature = Signature.getInstance(SIGNATURE_INSTANCE);
//        signature.initSign(getPrivateKey(privateKey));
//        signature.update(content);
//        return byteToHex(signature.sign());
//    }
//
//    public static String byteToHex(byte[] b) {
//        String hs = "";
//        String tmp = "";
//
//        for(int n = 0; n < b.length; ++n) {
//            tmp = Integer.toHexString(b[n] & 255);
//            if (tmp.length() == 1) {
//                hs = hs + "0" + tmp;
//            } else {
//                hs = hs + tmp;
//            }
//        }
//
//        tmp = null;
//        return hs.toUpperCase();
//    }
//
//
//
//    /**
//     * 公钥验签
//     *
//     * @param content
//     * @param sign
//     * @param publicKey
//     * @return boolean
//     * @MethodName: verify
//     * @author: 〆、dyh
//     * @since: 2022/3/4 10:33
//     */
//    public static boolean verify(byte[] content, byte[] sign, String publicKey) throws Exception {
//        Signature signature = Signature.getInstance(SIGNATURE_INSTANCE);
//        signature.initVerify(getPublicKey(publicKey));
//        signature.update(content);
//        return signature.verify(sign);
//    }
//
//    public static boolean signCheck(String timestamp, String nonce, Map<String, Object> requestBody, String signature) throws NoSuchAlgorithmException, SignatureException, IOException, InvalidKeyException {
//        //构造验签名串
//        String signatureStr = timestamp + "\n" + nonce + "\n" + JSONObject.toJSONString(requestBody) + "\n";
//        // 加载SHA256withRSA签名器
//        Signature signer = Signature.getInstance("SHA256withRSA");
//        // 用微信平台公钥对签名器进行初始化（调上一节中的获取平台证书方法）
//        signer.initVerify(CommonUtils.getCertificates());
//        // 把我们构造的验签名串更新到签名器中
//        signer.update(signatureStr.getBytes(StandardCharsets.UTF_8));
//        // 把请求头中微信服务器返回的签名用Base64解码 并使用签名器进行验证
//        boolean result = signer.verify(Base64Utils.decodeFromString(signature));
//        return result;
//    }
//
//    public static void main(String[] args) throws Exception {
//        Map<String, Object> keyMap;
//        try {
//            //初始化公私钥
//            keyMap = initKey();
//            //获得公钥
//            String publicKey = getPublicKey(keyMap);
//
//            System.out.println("rsa公钥：" + publicKey);
//            //获得私钥
//            String privateKey = getPrivateKey(keyMap);
//
//            System.out.println("rsa私钥：" + privateKey);
//            String content = "我爱你你却爱着他";
//            //公钥加密
//            byte[] encrypt = encryptByPublicKey(content.getBytes(StandardCharsets.UTF_8), publicKey);
//            //私钥解密
//            byte[] decryp = decryptByPrivateKey(encrypt, privateKey);
//            System.out.println("明文：" + new String(decryp));
//            //私钥签名
//            byte[] sign = sign(content.getBytes(StandardCharsets.UTF_8), privateKey);
//            System.out.println("私钥签名：" + sign);
//            //公钥验签
//            boolean verify = verify(content.getBytes(StandardCharsets.UTF_8), sign, publicKey);
//            System.out.println("验签结果：" + verify);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }

}