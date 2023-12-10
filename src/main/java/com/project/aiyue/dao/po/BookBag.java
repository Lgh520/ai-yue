package com.project.aiyue.dao.po;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
@Data
@ApiModel("书包")
public class BookBag {
    @ApiModelProperty("主键")
    private Long id;
    @ApiModelProperty("用户ID（手机号）")
    private String userId;
    @ApiModelProperty("书籍ID")
    private Long bookId;
    @ApiModelProperty("创建时间")
    private Date createTime;
    @ApiModelProperty("更新时间")
    private Date updateTime;
    @ApiModelProperty("书籍数量")
    private Integer bookCount;
    @ApiModelProperty("书名")
    private String bookName;
    @ApiModelProperty("缩略图")
    private String imageMedium;

}