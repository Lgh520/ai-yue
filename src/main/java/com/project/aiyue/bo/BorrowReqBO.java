package com.project.aiyue.bo;

import com.project.aiyue.dao.po.BookInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Data
@ToString
@ApiModel("借阅书籍入参")
public class BorrowReqBO implements Serializable {
    private static final long serialVersionUID = -800767299825609672L;

    @ApiModelProperty("借阅书籍列表")
    private List<BookInfo> list;

    @ApiModelProperty("联系方式")
    private String phoneNumber;

    @ApiModelProperty("联系人")
    private String nameStr;

    @ApiModelProperty("联系地址")
    private String addressStr;
}
