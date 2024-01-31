package com.project.aiyue.service.serviceImpl;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.project.aiyue.bo.CreateUserBO;
import com.project.aiyue.bo.PayReadPlanReqBO;
import com.project.aiyue.constant.CommonConstant;
import com.project.aiyue.dao.OrderInfoMapper;
import com.project.aiyue.dao.ReadPlanVipMapper;
import com.project.aiyue.dao.TransInfoMapper;
import com.project.aiyue.dao.UserInfoMapper;
import com.project.aiyue.dao.po.OrderInfo;
import com.project.aiyue.dao.po.ReadPlanVip;
import com.project.aiyue.dao.po.TransInfo;
import com.project.aiyue.dao.po.UserInfo;
import com.project.aiyue.exception.CommonException;
import com.project.aiyue.responor.CommonRespon;
import com.project.aiyue.service.UserInfoService;
import com.project.aiyue.utils.SecurityUtil;
import com.project.aiyue.utils.WxPayUtil;
import com.wechat.pay.java.core.Config;
import com.wechat.pay.java.core.RSAAutoCertificateConfig;
import com.wechat.pay.java.service.payments.jsapi.JsapiService;
import com.wechat.pay.java.service.payments.jsapi.model.Amount;
import com.wechat.pay.java.service.payments.jsapi.model.Payer;
import com.wechat.pay.java.service.payments.jsapi.model.PrepayRequest;
import com.wechat.pay.java.service.payments.jsapi.model.PrepayResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import static org.apache.http.entity.ContentType.APPLICATION_JSON;

@Service
@Slf4j
public class UserInfoServiceImpl implements UserInfoService {
    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private OrderInfoMapper orderInfoMapper;

    @Autowired
    private TransInfoMapper transInfoMapper;

    @Autowired
    WxPayUtil wxPayUtil;

    @Autowired
    private ReadPlanVipMapper readPlanVipMapper;

//    private RSAAutoCertificateConfig config;
//
//    @Autowired
//    public void setConfig(){
//        config = new RSAAutoCertificateConfig.Builder().merchantId(WxConfig.merchantId).privateKeyFromPath(WxConfig.privateKeyPath)
//                .merchantSerialNumber(WxConfig.merchantSerialNumber).apiV3Key(WxConfig.apiV3key).build();
//    }

    private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public CommonRespon<CreateUserBO> register(UserInfo userInfo) {
        String userId = userInfo.getUserId();
        UserInfo info = userInfoMapper.selectByPrimaryKey(userId);
        if (info != null) {
            log.info("用户id已存在,请重新填写");
            throw new CommonException(-1, "用户id已存在,请重新填写");
        }
        //加密
        String password = userInfo.getPassword();
        String securityPassword = SecurityUtil.enSecret(password, SecurityUtil.DEFAULT_KEY);
        userInfo.setPassword(securityPassword);
        //保存
        userInfoMapper.insert(userInfo);

        // 创建订单和流水进行支付
        return createOrder(userId, userInfo.getReadPlanVip(), userInfo.getOpenId());
    }

    @Override
    public UserInfo login(UserInfo userInfo) {
        String userId = userInfo.getUserId();
        String password = userInfo.getPassword();
        String s = SecurityUtil.enSecret(password, SecurityUtil.DEFAULT_KEY);
        UserInfo info = userInfoMapper.login(userId, s);
        if (info == null) {
            log.info("用户账号或密码错误，请重新输入");
            throw new CommonException(-1, "用户账号或密码错误，请重新输入");
        }
        if(info.getIsLock().equals("0")){
            return info;
        }
        log.info("请先购买vip卡或续费vip卡");
        throw new CommonException(-1,"请先购买vip卡或续费vip卡");
    }

    @Override
    public CommonRespon<CreateUserBO> payReadPlan(PayReadPlanReqBO reqBO) {
        return createOrder(reqBO.getUserId(), reqBO.getReadPlanVip(), reqBO.getOpenId());
    }

    @Override
    public void wxNotify(String orderId,String status) {
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setOrderId(Long.parseLong(orderId));
        orderInfo.setOrderStatus("A01");
        if (!"SUCCESS".equals(status)){
            orderInfo.setOrderStatus("A02");
        }
        orderInfoMapper.updateByPrimaryKeySelective(orderInfo);
        TransInfo transInfo = new TransInfo();
        transInfo.setOrderId(orderInfo.getOrderId());
        transInfo.setTransStatus(orderInfo.getOrderStatus());
        transInfoMapper.updateByPrimaryKeySelective(transInfo);


        if ("SUCCESS".equals(status)){
            // 如果支付成功，则将该用户该成VIP用户
            OrderInfo originOrderInfo = orderInfoMapper.selectByPrimaryKey(orderInfo.getOrderId());
            UserInfo originUserInfo = userInfoMapper.selectByPrimaryKey(originOrderInfo.getUserId());
            ReadPlanVip readPlanVip = readPlanVipMapper.selectByPrimaryKey(originOrderInfo.getVipId());
            UserInfo userInfo = new UserInfo();
            userInfo.setUserId(originOrderInfo.getUserId());
            userInfo.setUserType("0");
            Calendar instance = Calendar.getInstance();
            if (null == originUserInfo.getVipEndTime()){
                instance.add(Calendar.DAY_OF_MONTH,readPlanVip.getPeriodOfValidity().intValue());
                userInfo.setVipEndTimeStr(SDF.format(instance.getTime()));
            }else{
                instance.setTime(originUserInfo.getVipEndTime());
                instance.add(Calendar.DAY_OF_MONTH,readPlanVip.getPeriodOfValidity().intValue());
                userInfo.setVipEndTimeStr(SDF.format(instance.getTime()));
            }
            userInfoMapper.updateByPrimaryKeySelective(userInfo);
        }
    }

    @Override
    public CommonRespon updateOrderAndUserInfo(String orderId, JSONObject data) {
        String trade_state = data.getString("trade_state");
        if (trade_state.equals("SUCCESS")){
            OrderInfo info = new OrderInfo();
            info.setOrderId(Long.parseLong(orderId));
            info.setUpdateTimeStr(SDF.format(new Date()));
            info.setOrderStatus("A01");
            orderInfoMapper.updateByStatus(info);
            TransInfo transInfo = new TransInfo();
            transInfo.setOrderId(info.getOrderId());
            transInfo.setTransStatus("A01");
            transInfo.setUpdateTimeStr(info.getUpdateTimeStr());
            transInfoMapper.updateByStatus(transInfo);
            OrderInfo orderInfo = orderInfoMapper.selectByPrimaryKey(info.getOrderId());
            UserInfo originUserInfo = userInfoMapper.selectByPrimaryKey(orderInfo.getUserId());
            ReadPlanVip readPlanVip = readPlanVipMapper.selectByPrimaryKey(orderInfo.getVipId());
            UserInfo userInfo = new UserInfo();
            userInfo.setUserId(orderInfo.getUserId());
            userInfo.setIsLock("0");
            Calendar instance = Calendar.getInstance();
            if (null == originUserInfo.getVipEndTime()){
                instance.add(Calendar.DAY_OF_MONTH,readPlanVip.getPeriodOfValidity().intValue());
                userInfo.setVipEndTimeStr(SDF.format(instance.getTime()));
            }else{
                instance.setTime(originUserInfo.getVipEndTime());
                instance.add(Calendar.DAY_OF_MONTH,readPlanVip.getPeriodOfValidity().intValue());
                userInfo.setVipEndTimeStr(SDF.format(instance.getTime()));
            }
            userInfoMapper.updateByPrimaryKeySelective(userInfo);
        }
        return CommonRespon.success(null);
    }

    public String getOpenid(String code) {
        String result = "";
        BufferedReader in = null;
        try {
            URL realUrl = new URL("https://api.weixin.qq.com/sns/jscode2session?appid=wx80c234a658b806df&secret=374afc7e802c501e5641a83903659b72&js_code="+code+"&grant_type=authorization_code");
            log.info("获取openid，请求URL：{}", realUrl);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            connection.setRequestProperty("version", "ems_track_cn_1.0");
//            connection.setRequestProperty("authenticate", "35B05C8E7ED189B4E050030A240B17D1");
            // 建立实际的连接
            connection.connect();
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(connection
                    .getInputStream(), StandardCharsets.UTF_8));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.info("发送GET请求出现异常！{}", e);
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        log.info("获取openid，result：{}" , result);
        //将响应转为Json对象
        JSONObject respObj = JSONObject.parseObject(result);
        //获取openid
        String openid = respObj.getString("openid");
        return openid;
    }

        /**
         * 创建订单和流水并支付
         *
         * @param userId
         * @param readPlanVip
         * @param openId
         * @return
         */
    private CommonRespon<CreateUserBO> createOrder(String userId, ReadPlanVip readPlanVip, String openId) {
        Long orderId = null;
        Long transId = null;
        try {
            Date now = new Date();
            String nowStr = SDF.format(now);
            OrderInfo orderInfo = new OrderInfo();
            orderInfo.setCreateTimeStr(nowStr);
            orderInfo.setOrderStatus("A00");
            orderInfo.setOrderType("A");
            orderInfo.setUserId(userId);
            orderInfo.setVipId(readPlanVip.getVipId());
            orderInfo.setOrderMoney(readPlanVip.getReadPlanMoney());
            orderId = System.currentTimeMillis();
            orderInfo.setOrderId(orderId);
            orderInfoMapper.insertNew(orderInfo);
            TransInfo transInfo = new TransInfo();
            transInfo.setOrderId(orderId);
            transInfo.setTransMoney(orderInfo.getOrderMoney().toString());
            transInfo.setTransStatus("A00");
            transInfo.setUpdateTimeStr(nowStr);
            transId = Long.parseLong(getTransId());
            transInfo.setTransId(transId);
            transInfo.setCreateTimeStr(nowStr);
            transInfo.setTransType("A");
            transInfoMapper.insertNew(transInfo);
            Payer payer = new Payer();
            payer.setOpenid(getOpenid(openId));
            Config config =
                    new RSAAutoCertificateConfig.Builder()
                            .merchantId("1662535112")
                            .privateKeyFromPath("D:\\ideaproject\\ai-yue\\src\\main\\resources\\apiclient_key.pem")
                            .merchantSerialNumber("761BEFBD7E442AF5B5E80A1776C8EE813EB04903")
                            .apiV3Key("dedad62418608e4f65t985cj733b02ba")
                            .build();

            JsapiService service = new JsapiService.Builder().config(config).build();
            PrepayRequest request = new PrepayRequest();
            Amount amount = new Amount();
            Float fl = (Float.parseFloat(readPlanVip.getReadPlanMoney()) + Float.parseFloat(readPlanVip.getDeposit())) * 100;
            amount.setTotal(fl.intValue());
            request.setAmount(amount);
            request.setAppid("wx80c234a658b806df");
            request.setMchid("1662535112");
            request.setDescription(readPlanVip.getTitle());
            request.setNotifyUrl(WxPayUtil.notify_url);
            request.setPayer(payer);
            request.setOutTradeNo(String.valueOf(orderId));
            // 调用下单方法，得到应答
            PrepayResponse response = service.prepay(request);
            String prepayId = response.getPrepayId();

            if (StringUtils.isNotEmpty(prepayId)){
                CreateUserBO bo = new CreateUserBO();
                bo.setOrderId(orderId);
                bo.setPrepayId(prepayId);
                bo.setTimeStamp(String.valueOf(System.currentTimeMillis() / 1000));
                bo.setPackageStr(new StringBuffer("prepay_id=").append(prepayId).toString());
                bo.setNonceStr(getRandomString());
                JsonObject signObj = new JsonObject();
                signObj.addProperty("appid", WxPayUtil.appid);
                signObj.addProperty("timeStamp", bo.getTimeStamp());
                signObj.addProperty("nonceStr", bo.getNonceStr());
                signObj.addProperty("package", bo.getPackageStr());
                bo.setPaySign(wxPayUtil.getSign(bo.getTimeStamp(),bo.getNonceStr(), prepayId));
                log.info("下单成功，下单出参:{}", JSON.toJSONString(bo));
                return CommonRespon.success(bo);
            }else{
                orderInfoMapper.deleteByPrimaryKey(orderId);
                transInfoMapper.deleteByPrimaryKey(transId);
                return CommonRespon.error(1, "下单失败请重新下单!");
            }

            /*HttpPost httpPost = new HttpPost("https://api.mch.weixin.qq.com/v3/pay/transactions/jsapi");
            httpPost.addHeader(HttpHeaders.ACCEPT, APPLICATION_JSON.toString());
            httpPost.addHeader(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON.toString());
            httpPost.addHeader(HttpHeaders.AUTHORIZATION, APPLICATION_JSON.toString());

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectMapper objectMapper = new ObjectMapper();

            ObjectNode rootNode = objectMapper.createObjectNode();
            rootNode.put("mchid", WxPayUtil.merchantId)
                    .put("appid", WxPayUtil.appid)
                    .put("description", readPlanVip.getTitle())
                    .put("notify_url", WxPayUtil.notify_url)
                    .put("out_trade_no", orderId);
            rootNode.putObject("amount")
                    .put("total", (Float.parseFloat(readPlanVip.getReadPlanMoney()) + Float.parseFloat(readPlanVip.getDeposit())) * 100);
            rootNode.putObject("payer")
                    .put("openid", getOpenid(openId));

            objectMapper.writeValue(bos, rootNode);
            httpPost.setEntity(new StringEntity(bos.toString("UTF-8"), "UTF-8"));

            CloseableHttpClient httpClient = wxPayUtil.getHttpClient();
            CloseableHttpResponse execute = httpClient.execute(httpPost);

//            HttpRequest post = HttpUtil.createPost(CommonConstant.WX_CREATE_ORDER_URL);
//            HashMap<String, String> header = new HashMap<>();
//            header.put("Authorization", "");
//            header.put("Accept", "application/json");
//            header.put("Content-Type", "application/json");
//            post.addHeaders(header);
//            JsonObject jsonObject = new JsonObject();
//            JsonObject amount = new JsonObject();
//            JsonObject payer = new JsonObject();
//            amount.addProperty("total", (Integer.parseInt(readPlanVip.getReadPlanMoney()) + Integer.parseInt(readPlanVip.getDeposit())) * 100);
//            payer.addProperty("openid", openId);
//            jsonObject.addProperty("appid", CommonConstant.WX_APP_ID);
//            jsonObject.addProperty("mchid", CommonConstant.WX_MCH_ID);
//            jsonObject.addProperty("out_trade_no", transId);
//            jsonObject.addProperty("description", readPlanVip.getTitle());
//            jsonObject.addProperty("notify_url", CommonConstant.WX_NOTIFY_URL);
//            jsonObject.add("amount", amount);
//            jsonObject.add("payer", payer);
//            post.body(jsonObject.toString());
//            HttpResponse execute = post.execute();
//            if (null != execute && execute.getStatusLine().getStatusCode() == 200) {
//                String body = EntityUtils.toString(execute.getEntity());
////                String body = execute.body();
//                JsonParser jp = new JsonParser();
//                JsonObject parse = (JsonObject) jp.parse(body);
//                String prepayId = parse.get("prepay_id").getAsString();
//                CreateUserBO bo = new CreateUserBO();
//                bo.setOrderId(orderId);
//                bo.setPrepayId(prepayId);
//                bo.setTimeStamp(String.valueOf(System.currentTimeMillis() / 1000));
//                bo.setPackageStr(new StringBuffer("prepay_id=").append(prepayId).toString());
//                bo.setNonceStr(getRandomString());
//                JsonObject signObj = new JsonObject();
//                signObj.addProperty("appid", CommonConstant.WX_APP_ID);
//                signObj.addProperty("timeStamp", bo.getTimeStamp());
//                signObj.addProperty("nonceStr", bo.getNonceStr());
//                signObj.addProperty("package", bo.getPackageStr());
//                bo.setPaySign(RSAUtil.getSign(signObj.toString().getBytes(), CommonConstant.WX_PRIVATE_KEY));
//                return CommonRespon.success(bo);
//            } else {
//                orderInfoMapper.deleteByPrimaryKey(orderId);
//                transInfoMapper.deleteByPrimaryKey(transId);
//                return CommonRespon.error(1, "下单失败请重新下单!");
//            }
            if (null != execute && execute.getStatusLine().getStatusCode() == 200) {
                String body = EntityUtils.toString(execute.getEntity());
//                String body = execute.body();
                JsonParser jp = new JsonParser();
                JsonObject parse = (JsonObject) jp.parse(body);
                String prepayId = parse.get("prepay_id").getAsString();
                CreateUserBO bo = new CreateUserBO();
                bo.setOrderId(orderId);
                bo.setPrepayId(prepayId);
                bo.setTimeStamp(String.valueOf(System.currentTimeMillis() / 1000));
                bo.setPackageStr(new StringBuffer("prepay_id=").append(prepayId).toString());
                bo.setNonceStr(getRandomString());
                JsonObject signObj = new JsonObject();
                signObj.addProperty("appid", CommonConstant.WX_APP_ID);
                signObj.addProperty("timeStamp", bo.getTimeStamp());
                signObj.addProperty("nonceStr", bo.getNonceStr());
                signObj.addProperty("package", bo.getPackageStr());
                bo.setPaySign(wxPayUtil.getSign(bo.getTimeStamp(),bo.getNonceStr(), prepayId));
                return CommonRespon.success(bo);
            } else {
                orderInfoMapper.deleteByPrimaryKey(orderId);
                transInfoMapper.deleteByPrimaryKey(transId);
                return CommonRespon.error(1, "下单失败请重新下单!");
            }*/
        } catch (Exception e) {
            if (null != orderId) {
                orderInfoMapper.deleteByPrimaryKey(orderId);
            }
            if (null != transId){
                transInfoMapper.deleteByPrimaryKey(transId);
            }
            log.error("微信下单异常:{]", e.getMessage());
            e.printStackTrace();
            return CommonRespon.error(1, "下单失败请重新下单!");
        }
    }

    private String getRandomString() {
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < 32; i++) {
            int i1 = random.nextInt(base.length());
            stringBuffer.append(i1);
        }
        return stringBuffer.toString();
    }

    /**
     * 获取现在时间
     *
     * @return返回字符串格式yyyyMMddHHmmss
     */
    public static String getStringDate() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    /**
     * 由年月日时分秒+3位随机数
     * 生成流水号
     *
     * @return
     */
    public static String getTransId() {
        String t = getStringDate();
        int x = (int) (Math.random() * 900) + 100;
        String serial = t + x;
        return serial;
    }

    public static void main(String[] args) {
        System.out.println(getTransId());
        System.out.println(Long.MAX_VALUE);
    }
}
