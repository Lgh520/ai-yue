package com.project.aiyue.service;


import com.alibaba.fastjson2.JSONObject;
import com.project.aiyue.bo.CreateUserBO;
import com.project.aiyue.bo.PayReadPlanReqBO;
import com.project.aiyue.dao.po.UserInfo;
import com.project.aiyue.responor.CommonRespon;

public interface UserInfoService {
    CommonRespon<CreateUserBO> register(UserInfo userInfo);
    UserInfo login(UserInfo userInfo);
    CommonRespon<CreateUserBO> payReadPlan(PayReadPlanReqBO userInfo);
    void wxNotify(String orderId,String status);
    CommonRespon updateOrderAndUserInfo(String orderId, JSONObject trade_state);
}
