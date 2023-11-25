package com.project.aiyue.dao.po;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
@Data
@ApiModel("字典表")
public class SysParam implements Serializable {
    private static final long serialVersionUID = 7806093017207835998L;
    @ApiModelProperty("code")
    private String paramCode;
    @ApiModelProperty("key")
    private String paramKey;
    @ApiModelProperty("value")
    private String paramValue;
    @ApiModelProperty("名称")
    private String paramName;

}