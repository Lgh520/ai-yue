package com.project.aiyue.utils;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.io.FileUtil;
import com.ijpay.core.kit.PayKit;
import com.wechat.pay.contrib.apache.httpclient.WechatPayHttpClientBuilder;
import com.wechat.pay.contrib.apache.httpclient.auth.PrivateKeySigner;
import com.wechat.pay.contrib.apache.httpclient.auth.Verifier;
import com.wechat.pay.contrib.apache.httpclient.auth.WechatPay2Credentials;
import com.wechat.pay.contrib.apache.httpclient.cert.CertificatesManager;
import com.wechat.pay.contrib.apache.httpclient.util.PemUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.impl.client.CloseableHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * 支付相关
 * @Author smilehan
 */
@Component
@Slf4j
@Data
public class WxPayUtil {

    /** 商户号 */
    public static final String merchantId = "1662535112";

    /** 商户API私钥路径 */
    public static String privateKeyPath = "/usr/local/app/java_app/apiclient_key.pem";
//    public static String privateKeyPath = "D:\\ideaproject\\ai-yue\\src\\main\\resources\\apiclient_key.pem";

    /** 商户API私钥路径 */
    public static String certPath = "/usr/local/app/java_app/apiclient_cert.pem";
//    public static String certPath = "D:\\ideaproject\\ai-yue\\src\\main\\resources\\apiclient_cert.pem";

    /** 商户证书序列号 */
    public static final String merchantSerialNumber = "761BEFBD7E442AF5B5E80A1776C8EE813EB04903";

    /** 商户APIV3密钥 */
    public static final String apiV3key = "dedad62418608e4f65t985cj733b02ba";

    /**appid */
    public static final String appid="wx80c234a658b806df";

    /**mchSerialNo */
    public static final String mchSerialNo="761BEFBD7E442AF5B5E80A1776C8EE813EB04903";

    /**notify_url */
    public static final String notify_url="http://aiyuekj.cn/aiyue/userInfo/wxNotify";

    private CloseableHttpClient httpClient;

    private Verifier verifier;

    public static void main(String[] args) {
        File file = new File(privateKeyPath);
        System.out.println(file.isFile());
        String name = file.getName();
        System.out.println(name);
        System.out.println(file.length());
    }

    @PostConstruct
    public void initHttpClient(){
        log.info("微信支付httpClient初始化");
        PrivateKey merchantPrivateKey = PemUtil.loadPrivateKey(FileUtil.getInputStream(privateKeyPath));
        X509Certificate wechatPayCertificate = PemUtil.loadCertificate(FileUtil.getInputStream(certPath));

        ArrayList<X509Certificate> listCertificates = new ArrayList<>();
        listCertificates.add(wechatPayCertificate);

        httpClient = WechatPayHttpClientBuilder.create()
                .withMerchant(merchantId, mchSerialNo, merchantPrivateKey)
                .withWechatPay(listCertificates)
                .build();
    }


    @PostConstruct
    public void initVerifier() throws Exception{
        log.info("微信支付Verifier初始化");
        PrivateKey merchantPrivateKey = PemUtil.loadPrivateKey(FileUtil.getInputStream(privateKeyPath));
        // 获取证书管理器实例
        CertificatesManager certificatesManager = CertificatesManager.getInstance();
        // 向证书管理器增加需要自动更新平台证书的商户信息
        certificatesManager.putMerchant(merchantId, new WechatPay2Credentials(merchantId,
                new PrivateKeySigner(mchSerialNo, merchantPrivateKey)), apiV3key.getBytes(StandardCharsets.UTF_8));
        // 从证书管理器中获取verifier
        verifier = certificatesManager.getVerifier(merchantId);
    }


    /**
     * 生成签名
     * @param timestamp
     * @param nonceStr
     * @param prepayId
     * @return
     * @throws Exception
     */
    public String getSign(String timestamp, String nonceStr, String prepayId) throws Exception{
        String message=appid + "\n"
                + timestamp + "\n"
                + nonceStr + "\n"
                + prepayId + "\n";

        String sign = PayKit.createSign(message, privateKeyPath);

        return sign;
    }


    /**
     * 回调的支付时间转成date
     * @param dateTimeString
     * @return
     */
    public Date dateTimeToDate(String dateTimeString){
        DateTime dateTime = new DateTime(dateTimeString);
        long timeInMillis = dateTime.toCalendar(Locale.getDefault()).getTimeInMillis();
        return new Date(timeInMillis);
    }

}
