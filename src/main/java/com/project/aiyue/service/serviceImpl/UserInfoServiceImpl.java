package com.project.aiyue.service.serviceImpl;

import com.project.aiyue.dao.OrderInfoMapper;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
@Slf4j
public class UserInfoServiceImpl implements UserInfoService {
    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private OrderInfoMapper orderInfoMapper;

    @Autowired
    private TransInfoMapper transInfoMapper;

    private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public String register(UserInfo userInfo) {
        String userId = userInfo.getUserId();
        UserInfo info = userInfoMapper.selectByPrimaryKey(userId);
        if(info != null){
            log.info("用户id已存在,请重新填写");
            throw new CommonException(-1,"用户id已存在,请重新填写");
        }
        //加密
        String password = userInfo.getPassword();
        String securityPassword = SecurityUtil.enSecret(password, SecurityUtil.DEFAULT_KEY);
        userInfo.setPassword(securityPassword);
        //保存
        userInfoMapper.insert(userInfo);
        // 创建订单和流水进行支付
        CommonRespon commonRespon = createOrder(userId,userInfo.getReadPlanVip());
        return "用户创建失败，请重试";
    }

    @Override
    public UserInfo login(UserInfo userInfo) {
        String userId = userInfo.getUserId();
        String password = userInfo.getPassword();
        String s = SecurityUtil.enSecret(password, SecurityUtil.DEFAULT_KEY);
        UserInfo info = userInfoMapper.login(userId,s);
        if(info == null){
            log.info("用户账号或密码错误，请重新输入");
            throw new CommonException(-1,"用户账号或密码错误，请重新输入");
        }
        if(info.getIsLock() != "0"){
            log.info("请先购买vip卡或续费vip卡");
            throw new CommonException(-1,"请先购买vip卡或续费vip卡");
        }
        return info;
    }

    /**
     * 创建订单和流水并支付
     * @param userId
     * @param readPlanVip
     * @return
     */
    private CommonRespon createOrder(String userId, ReadPlanVip readPlanVip) {
        Date now = new Date();
        String nowStr = SDF.format(now);
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setCreateTimeStr(nowStr);
        orderInfo.setOrderStatus("A00");
        orderInfo.setOrderType("A");
        orderInfo.setUserId(userId);
        orderInfo.setOrderMoney(Long.parseLong(readPlanVip.getReadPlanMoney()));
        long orderId = orderInfoMapper.insertNew(orderInfo);

        TransInfo transInfo = new TransInfo();
        transInfo.setOrderId(orderId);
        transInfo.setTransMoney(orderInfo.getOrderMoney().toString());
        transInfo.setTransStatus("A00");
        transInfo.setUpdateTimeStr(nowStr);
        transInfo.setTransId(Long.parseLong(getTransId()));
        transInfo.setCreateTimeStr(nowStr);
        transInfoMapper.insertNew(transInfo);

        return null;
    }

    /**
     * 获取现在时间
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
     * @return
     */
    public static String getTransId(){
        String t = getStringDate();
        int x=(int)(Math.random()*900)+100;
        String serial = t + x;
        return serial;
    }

    public static void main(String[] args) {
        System.out.println(getTransId());
        System.out.println(Long.MAX_VALUE);
    }
}
