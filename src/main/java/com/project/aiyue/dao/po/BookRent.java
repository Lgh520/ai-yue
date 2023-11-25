package com.project.aiyue.dao.po;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class BookRent implements Serializable {
    private static final long serialVersionUID = 480281907463797619L;
    private Long rentId;

    private String userId;

    private Long bookId;

    private String isBack;

    private Date createTime;

    private Date updateTime;

    private Integer bookCount;

}