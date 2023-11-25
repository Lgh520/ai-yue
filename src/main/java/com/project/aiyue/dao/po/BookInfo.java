package com.project.aiyue.dao.po;

import com.project.aiyue.dao.bo.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel("图书信息")
public class BookInfo extends Page implements Serializable {
    private static final long serialVersionUID = -8019949730918822843L;
    @ApiModelProperty("图书id")
    private Long bookId;
    @ApiModelProperty("推荐指数")
    private String levelNum;
    @ApiModelProperty("副标题")
    private String subTitle;
    @ApiModelProperty("作者")
    private String author;
    @ApiModelProperty("发版日期")
    private String pubDate;
    @ApiModelProperty("源标题（国外源标题）")
    private String originTitle;
    @ApiModelProperty("装订方式")
    private String binding;
    @ApiModelProperty("总页数")
    private String pages;
    @ApiModelProperty("缩略图")
    private String imageMedium;
    @ApiModelProperty("大图")
    private String imageLarge;
    @ApiModelProperty("出版社名称")
    private String publisher;
    @ApiModelProperty("10位ISBN码")
    private String isbn10;
    @ApiModelProperty("13位ISBN码")
    private String isbn13;
    @ApiModelProperty("书籍名称")
    private String title;
    @ApiModelProperty("内容简介")
    private String summary;
    @ApiModelProperty("销售价格")
    private String price;
    @ApiModelProperty("库存")
    private Integer bookCounts;
    @ApiModelProperty("借阅次数")
    private Long rentCounts;
    @ApiModelProperty("货架ID")
    private Long goodsId;
    @ApiModelProperty("创建时间")
    private Date createTime;
    @ApiModelProperty("更新时间")
    private Date updateTime;
    @ApiModelProperty("年龄类型")
    private Integer ageType;
    @ApiModelProperty("主题类型")
    private Integer themeType;

}