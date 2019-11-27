package com.project.aiyue.controller;


import com.project.aiyue.constant.ResponCodeConstant;
import com.project.aiyue.dao.po.UserInfo;
import com.project.aiyue.exception.CommonException;
import com.project.aiyue.responor.CommonRespon;
import com.project.aiyue.service.UserInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/userInfo")
@Api(tags = "用户服务接口")
public class UserInfoController {
    @Autowired
    private UserInfoService userInfoService;

    @PostMapping ("/register")
    @ApiOperation("客户端用户注册接口")
    public CommonRespon<String> register(@RequestBody @Valid UserInfo userInfo)  {
        try {
            String register = userInfoService.register(userInfo);
            if(register != null){
                return CommonRespon.success(register);
            }
        }catch (Exception e){
            e.printStackTrace();
            log.info("用户注册失败：{}",e.getMessage());
            return  CommonRespon.error(ResponCodeConstant.ERROR_CODE,e.getMessage());
        }
        return  CommonRespon.error(ResponCodeConstant.ERROR_CODE,"注册失败，请重试");
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


}
