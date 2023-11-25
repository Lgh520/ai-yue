package com.project.aiyue.dao.po;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
@Data
@ApiModel("配送记录")
public class DeliveryRecord implements Serializable {
    private static final long serialVersionUID = 5252705374089147219L;
    @ApiModelProperty("主键")
    private Long deliveryId;
    @ApiModelProperty("书籍借阅ID")
    private Long rentId;
    @ApiModelProperty("配送用户ID")
    private String deliveryUser;
    @ApiModelProperty("配送状态：1-已接单，2-配送中，3-配送完成")
    private String deliveryStatus;
    @ApiModelProperty("配送时间（分）")
    private String deliveryTime;
    @ApiModelProperty("创建时间")
    private Date createTime;
    @ApiModelProperty("更新时间")
    private Date updateTime;

}