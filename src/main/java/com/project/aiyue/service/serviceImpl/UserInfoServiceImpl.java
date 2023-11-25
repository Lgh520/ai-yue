package com.project.aiyue.service.serviceImpl;

import com.project.aiyue.dao.UserInfoMapper;
import com.project.aiyue.dao.po.UserInfo;
import com.project.aiyue.exception.CommonException;
import com.project.aiyue.service.UserInfoService;
import com.project.aiyue.utils.SecurityUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserInfoServiceImpl implements UserInfoService {
    @Autowired
    private UserInfoMapper userInfoMapper;
    @Override
    public String register(UserInfo userInfo) {
        String userId = userInfo.getUserId();
        UserInfo info = userInfoMapper.selectByPrimaryKey(userId);
        if(info != null){
            log.info("用户id也存在,请重新填写");
            throw new CommonException(-1,"用户id也存在,请重新填写");
        }
        //加密
        String password = userInfo.getPassword();
        String securityPassword = SecurityUtil.enSecret(password, SecurityUtil.DEFAULT_KEY);
        userInfo.setPassword(securityPassword);
        //保存
        int insert = userInfoMapper.insert(userInfo);
        if (insert == 1){
            return "用户创建成功";
        }
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
        return info;
    }
}
