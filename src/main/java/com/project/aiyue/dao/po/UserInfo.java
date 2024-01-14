package com.project.aiyue.dao.po;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
@Data
@ApiModel("用户信息表")
public class  UserInfo implements Serializable {
    private static final long serialVersionUID = -5465178824092002786L;
    @ApiModelProperty("用户ID（手机号）")
    private String userId;
    @ApiModelProperty("用户名")
    private String userName;
    @ApiModelProperty("密码")
    private String password;
    @ApiModelProperty("用户类型：0-普通用户，1-管理员")
    private String userType;
    @ApiModelProperty("是否上锁：0-未锁，1-上锁")
    private String isLock;
    @ApiModelProperty("创建时间")
    private Date createTime;
    @ApiModelProperty("更新时间")
    private Date updateTime;
    @ApiModelProperty("vip卡信息")
    private ReadPlanVip readPlanVip;
    @ApiModelProperty("VIP类型")
    private Long vipID;
    @ApiModelProperty("VIP卡过期时间")
    private Date vipEndTime;
    @ApiModelProperty("VIP卡过期时间")
    private String vipEndTimeStr;
    @ApiModelProperty("openId")
    private String openId;

}