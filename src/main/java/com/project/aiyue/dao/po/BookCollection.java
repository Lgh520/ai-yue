package com.project.aiyue.dao.po;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

@Data
@ToString
public class BookCollection implements Serializable {

    private static final long serialVersionUID = -2413708907011382898L;

    @ApiModelProperty("用户ID")
    private String userId;

    @ApiModelProperty("书籍id")
    private Long bookId;

    @ApiModelProperty("书籍名称")
    private String title;

    @ApiModelProperty("书籍图片")
    private String imageLarge;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("更新时间")
    private Date updateTime;

    @ApiModelProperty("创建时间")
    private String createTimeStr;

    @ApiModelProperty("更新时间")
    private String updateTimeStr;

}