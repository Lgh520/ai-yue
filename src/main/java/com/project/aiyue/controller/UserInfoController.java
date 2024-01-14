package com.project.aiyue.controller;

import static org.apache.http.HttpHeaders.ACCEPT;
import static org.apache.http.entity.ContentType.APPLICATION_JSON;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.project.aiyue.utils.WxPayUtil;
import com.wechat.pay.contrib.apache.httpclient.notification.Notification;
import com.wechat.pay.contrib.apache.httpclient.notification.NotificationHandler;
import com.wechat.pay.contrib.apache.httpclient.notification.NotificationRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import static com.wechat.pay.contrib.apache.httpclient.constant.WechatPayHttpHeaders.*;
import com.project.aiyue.bo.CreateUserBO;
import com.project.aiyue.bo.PayReadPlanReqBO;
import com.project.aiyue.constant.ResponCodeConstant;
import com.project.aiyue.dao.po.UserInfo;
import com.project.aiyue.responor.CommonRespon;
import com.project.aiyue.service.UserInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/userInfo")
@Api(tags = "用户服务接口")
public class UserInfoController {
    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private WxPayUtil wxPayUtil;

    @PostMapping ("/register")
    @ApiOperation("客户端用户注册接口")
    public CommonRespon<CreateUserBO> register(@RequestBody @Valid UserInfo userInfo)  {
        try {
            return userInfoService.register(userInfo);
        }catch (Exception e){
            e.printStackTrace();
            log.info("用户注册失败：{}",e.getMessage());
            return  CommonRespon.error(ResponCodeConstant.ERROR_CODE,e.getMessage());
        }
    }

    @ApiOperation("客户端用户登录接口")
    @PostMapping ("/login")
    public CommonRespon<UserInfo> login(@RequestBody @Valid UserInfo userInfo)  {
        try {
            UserInfo info = userInfoService.login(userInfo);
            if(info != null){
                return CommonRespon.success(info);
            }
        }catch (Exception e){
            log.error("登录失败:{}",e.getMessage());
            e.printStackTrace();
            return CommonRespon.error(ResponCodeConstant.ERROR_CODE,e.getMessage());
        }
        return CommonRespon.error(ResponCodeConstant.ERROR_CODE,"请先注册账号再登录~");
    }

    @ApiOperation("续费下单接口")
    @PostMapping ("/payReadPlan")
    public CommonRespon<CreateUserBO> payReadPlan(@RequestBody PayReadPlanReqBO reqBO){
        return userInfoService.payReadPlan(reqBO);
    }

    @ApiOperation("微信通知回调接口")
    @PostMapping ("/wxNotify")
    public void wxNotify(HttpServletRequest request){
        try {
            //读取请求体的信息
            ServletInputStream inputStream = request.getInputStream();
            StringBuffer stringBuffer = new StringBuffer();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String s;
            //读取回调请求体
            while ((s = bufferedReader.readLine()) != null) {
                stringBuffer.append(s);
            }
            String s1 = stringBuffer.toString();

            String timestamp = request.getHeader(WECHAT_PAY_TIMESTAMP);
            String nonce = request.getHeader(WECHAT_PAY_NONCE);
            String serialNo = request.getHeader(WECHAT_PAY_SERIAL);
            String signature = request.getHeader(WECHAT_PAY_SIGNATURE);
            // 构建request，传入必要参数
            NotificationRequest notificationRequest = new NotificationRequest.Builder().withSerialNumber(serialNo)
                    .withNonce(nonce)
                    .withTimestamp(timestamp)
                    .withSignature(signature)
                    .withBody(s1)
                    .build();
            NotificationHandler handler = new NotificationHandler(wxPayUtil.getVerifier(), WxPayUtil.apiV3key.getBytes(StandardCharsets.UTF_8));
            // 验签和解析请求体
            Notification notification = handler.parse(notificationRequest);
            log.info("回调结果：{}",notification);
            JSONObject jsonObject = JSONObject.parseObject(notification.getDecryptData());
            String tradeState = jsonObject.getString("trade_state");
            String orderSn = jsonObject.getString("out_trade_no");
            userInfoService.wxNotify(orderSn, tradeState);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 订单查询
     *
     * @return
     */
    @ApiOperation("微信订单查询接口")
    @PostMapping ("/queryOrder")
    public CommonRespon queryOrder(String orderId) {
        try {
            //根据系统自有订单号查询
            String url = "https://api.mch.weixin.qq.com/v3/pay/transactions/out-trade-no/" + orderId;
            URIBuilder uriBuilder = new URIBuilder(url);
            uriBuilder.setParameter("mchid", WxPayUtil.merchantId);

            HttpGet httpGet = new HttpGet(uriBuilder.build());
            httpGet.addHeader(ACCEPT, APPLICATION_JSON.toString());

            CloseableHttpClient httpClient = wxPayUtil.getHttpClient();
            CloseableHttpResponse response = httpClient.execute(httpGet);

            String bodyAsString = EntityUtils.toString(response.getEntity());
            JSONObject data = JSON.parseObject(bodyAsString);
            log.info("微信订单查询结果：{}", data);
            CommonRespon tradeState = userInfoService.updateOrderAndUserInfo(orderId, data);
            return tradeState;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("订单查询异常:{}", e.getMessage());
            return CommonRespon.error(1, "订单查询异常:" + e.getMessage());
        }
    }
}
