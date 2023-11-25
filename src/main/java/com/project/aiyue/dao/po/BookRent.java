package com.project.aiyue.dao.po;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel("图书借阅")
public class BookRent implements Serializable {
    private static final long serialVersionUID = 480281907463797619L;
    @ApiModelProperty("主键")
    private Long rentId;
    @ApiModelProperty("用户ID（手机号）")
    private String userId;
    @ApiModelProperty("书籍ID")
    private Long bookId;
    @ApiModelProperty("是否归还：0-未归还，1-已归还")
    private String isBack;
    @ApiModelProperty("创建时间")
    private Date createTime;
    @ApiModelProperty("更新时间")
    private Date updateTime;
    @ApiModelProperty("图书数量")
    private Integer bookCount;

}