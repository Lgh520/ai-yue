package com.project.aiyue.dao.po;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("图书信息")
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

    public Long getVipId() {
        return vipId;
    }

    public void setVipId(Long vipId) {
        this.vipId = vipId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getDeposit() {
        return deposit;
    }

    public void setDeposit(String deposit) {
        this.deposit = deposit == null ? null : deposit.trim();
    }

    public String getReadPlanMoney() {
        return readPlanMoney;
    }

    public void setReadPlanMoney(String readPlanMoney) {
        this.readPlanMoney = readPlanMoney == null ? null : readPlanMoney.trim();
    }

    public Integer getPerRentCount() {
        return perRentCount;
    }

    public void setPerRentCount(Integer perRentCount) {
        this.perRentCount = perRentCount;
    }

    public Integer getPeriodOfValidity() {
        return periodOfValidity;
    }

    public void setPeriodOfValidity(Integer periodOfValidity) {
        this.periodOfValidity = periodOfValidity;
    }

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