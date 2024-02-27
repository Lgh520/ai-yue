package com.project.aiyue.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

@ApiModel("查询")
@ToString
@Data
public class SearchWrapper extends Page{
    @ApiModelProperty("查询信息")
    private String name;
}
