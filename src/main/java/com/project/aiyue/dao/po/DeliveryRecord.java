package com.project.aiyue.dao.po;

import java.io.Serializable;
import java.util.Date;

public class DeliveryRecord implements Serializable {
    private static final long serialVersionUID = 5252705374089147219L;
    private Long deliveryId;

    private Long rentId;

    private String deliveryUser;

    private String deliveryStatus;

    private String deliveryTime;

    private Date createTime;

    private Date updateTime;

    public Long getDeliveryId() {
        return deliveryId;
    }

    public void setDeliveryId(Long deliveryId) {
        this.deliveryId = deliveryId;
    }

    public Long getRentId() {
        return rentId;
    }

    public void setRentId(Long rentId) {
        this.rentId = rentId;
    }

    public String getDeliveryUser() {
        return deliveryUser;
    }

    public void setDeliveryUser(String deliveryUser) {
        this.deliveryUser = deliveryUser == null ? null : deliveryUser.trim();
    }

    public String getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(String deliveryStatus) {
        this.deliveryStatus = deliveryStatus == null ? null : deliveryStatus.trim();
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime == null ? null : deliveryTime.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "DeliveryRecord{" +
                "deliveryId=" + deliveryId +
                ", rentId=" + rentId +
                ", deliveryUser='" + deliveryUser + '\'' +
                ", deliveryStatus='" + deliveryStatus + '\'' +
                ", deliveryTime='" + deliveryTime + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}