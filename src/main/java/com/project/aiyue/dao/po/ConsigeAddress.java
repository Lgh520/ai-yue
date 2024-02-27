package com.project.aiyue.dao.po;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;
@Data
@ToString
@ApiModel("用户收货地址")
public class ConsigeAddress implements Serializable {
    @ApiModelProperty("主键")
    private Integer consignId;
    @ApiModelProperty("用户ID（手机号）")
    private String userId;
    @ApiModelProperty("收货地址")
    private String address;
    @ApiModelProperty("收货人")
    private String consignee;
    @ApiModelProperty("收货人号码")
    private String consigneePhone;
    @ApiModelProperty("备注")
    private String remark;
    @ApiModelProperty("创建时间")
    private Date createTime;
    @ApiModelProperty("创建时间")
    private String createTimeStr;
    @ApiModelProperty("更新时间")
    private Date updateTime;
    @ApiModelProperty("更新时间")
    private String updateTimeStr;

}