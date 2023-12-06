package com.project.aiyue.dao.po;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
@Data
@ApiModel("订单")
public class OrderInfo implements Serializable {
    private static final long serialVersionUID = -5894159218018339456L;
    @ApiModelProperty("订单号")
    private Long orderId;
    @ApiModelProperty("订单金额")
    private Long orderMoney;
    @ApiModelProperty("用户ID")
    private String userId;
    @ApiModelProperty("订单状态：A00-创建，A01-支付成功，A01-支付失败，B00-创建，B01-退款成功，B01-退款失败")
    private String orderStatus;
    @ApiModelProperty("订单类型：A-支付，B-退款")
    private String orderType;
    @ApiModelProperty("创建时间")
    private Date createTime;
    @ApiModelProperty("更新时间")
    private Date updateTime;
    @ApiModelProperty("创建时间")
    private String createTimeStr;
    @ApiModelProperty("更新时间")
    private String updateTimeStr;

}