package com.project.aiyue.dao.po;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
@Data
@ApiModel("流水表")
public class TransInfo implements Serializable {
    private static final long serialVersionUID = -6925746940145729324L;
    @ApiModelProperty("流水号")
    private Long transId;
    @ApiModelProperty("订单号")
    private Long orderId;
    @ApiModelProperty("流水金额")
    private String transMoney;
    @ApiModelProperty("流水状态：A00-创建，A01-支付成功，A02-支付失败，B00-创建，B01-退款成功，B01-退款失败")
    private String transStatus;
    @ApiModelProperty("流水类型：A-支付，B-退")
    private String transType;
    @ApiModelProperty("创建时间")
    private Date createTime;
    @ApiModelProperty("更新时间")
    private Date updateTime;
    @ApiModelProperty("创建时间")
    private String createTimeStr;
    @ApiModelProperty("更新时间")
    private String updateTimeStr;

}