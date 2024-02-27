package com.project.aiyue.dao.po;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@ApiModel("vip卡信息")
public class ReadPlanVip {
    @ApiModelProperty("VIPid")
    private Long vipId;

    @ApiModelProperty("vip卡标题")
    private String title;

    @ApiModelProperty("押金")
    private String deposit;

    @ApiModelProperty("金额")
    private String readPlanMoney;

    @ApiModelProperty("每次可借多少本")
    private Integer perRentCount;

    @ApiModelProperty("有效期：天数")
    private Integer periodOfValidity;

    @Override
    public String toString() {
        return "ReadPlanVip{" +
                "vipId=" + vipId +
                ", title='" + title + '\'' +
                ", deposit='" + deposit + '\'' +
                ", readPlanMoney='" + readPlanMoney + '\'' +
                ", perRentCount=" + perRentCount +
                ", periodOfValidity=" + periodOfValidity +
                '}';
    }
}