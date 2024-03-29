package com.project.aiyue.bo;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
public class CreateUserBO implements Serializable {
    private static final long serialVersionUID = 1347247439735061664L;

    private String prepayId;

    private Long orderId;

    private String timeStamp;

    private String nonceStr;

    private String packageStr;

    private String paySign;
}
