package com.project.aiyue.service;


import com.project.aiyue.dao.po.UserInfo;

public interface UserInfoService {
    String register(UserInfo userInfo);
    Boolean login(UserInfo userInfo);
}