package com.project.aiyue.controller;

import com.alibaba.fastjson2.JSONObject;
import com.project.aiyue.utils.WxPayUtil;
import com.wechat.pay.contrib.apache.httpclient.exception.ParseException;
import com.wechat.pay.contrib.apache.httpclient.exception.ValidationException;
import com.wechat.pay.contrib.apache.httpclient.notification.Notification;
import com.wechat.pay.contrib.apache.httpclient.notification.NotificationHandler;
import com.wechat.pay.contrib.apache.httpclient.notification.NotificationRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
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
    public void wxNotify(HttpServletRequest request, HttpServletResponse response){
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

}
