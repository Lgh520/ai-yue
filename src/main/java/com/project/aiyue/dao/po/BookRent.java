package com.project.aiyue.dao.po;

import java.io.Serializable;
import java.util.Date;

public class BookRent implements Serializable {
    private static final long serialVersionUID = 480281907463797619L;
    private Long rentId;

    private String userId;

    private Long bookId;

    private String isBack;

    private Date createTime;

    private Date updateTime;

    public Long getRentId() {
        return rentId;
    }

    public void setRentId(Long rentId) {
        this.rentId = rentId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public String getIsBack() {
        return isBack;
    }

    public void setIsBack(String isBack) {
        this.isBack = isBack == null ? null : isBack.trim();
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
        return "BookRent{" +
                "rentId=" + rentId +
                ", userId='" + userId + '\'' +
                ", bookId=" + bookId +
                ", isBack='" + isBack + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}