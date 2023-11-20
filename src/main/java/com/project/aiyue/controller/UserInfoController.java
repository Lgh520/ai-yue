package com.project.aiyue.controller;


import com.project.aiyue.ResponCodeConstant;
import com.project.aiyue.dao.po.UserInfo;
import com.project.aiyue.exception.CommonException;
import com.project.aiyue.responor.CommonRespon;
import com.project.aiyue.service.UserInfoService;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/userInfo")
public class UserInfoController {
    @Autowired
    private UserInfoService userInfoService;

    @PostMapping ("/register")
    public CommonRespon register(@RequestBody @Valid UserInfo userInfo)  {
        CommonRespon respon = new CommonRespon();
        try {
            String register = userInfoService.register(userInfo);
            if(register != null){
                respon.setCode(ResponCodeConstant.NORMAL_CODE);
                respon.setData(register);
            }
        }catch (CommonException e){
            e.printStackTrace();
            log.info(e.getMessage());
            respon.setCode(ResponCodeConstant.PARA_ERROR_CODE);
            respon.setData(e.getMessage());
            return respon;
        }catch (Exception e){
            e.printStackTrace();
            log.info(e.getMessage());
            respon.setCode(ResponCodeConstant.PARA_ERROR_CODE);
            respon.setData(e.getMessage());
            return respon;
        }
        return respon;
    }
    @PostMapping ("/login")
    public CommonRespon login(@RequestBody @Valid UserInfo userInfo)  {
        CommonRespon respon = new CommonRespon();
        try {
            Boolean login = userInfoService.login(userInfo);
            if(login != null){
                respon.setCode(ResponCodeConstant.NORMAL_CODE);
                respon.setData(login);
            }
        }catch (CommonException e){
            e.printStackTrace();
            log.info(e.getMessage());
            respon.setCode(ResponCodeConstant.PARA_ERROR_CODE);
            respon.setData(e.getMessage());
            return respon;
        }catch (Exception e){
            e.printStackTrace();
            log.info(e.getMessage());
            respon.setCode(ResponCodeConstant.INNER_ERROR_CODE);
            respon.setData(e.getMessage());
            return respon;
        }
        return respon;
    }
}
