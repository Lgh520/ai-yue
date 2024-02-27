package com.project.aiyue.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import java.io.Serializable;

@Data
@ToString
public class ResetPasswordBO implements Serializable {
    private static final long serialVersionUID = 4712911948738178885L;

    @ApiModelProperty("用户ID（手机号）")
    private String userId;

    @ApiModelProperty("原密码")
    private String password;

    @ApiModelProperty("新密码")
    private String newPassword;
}
