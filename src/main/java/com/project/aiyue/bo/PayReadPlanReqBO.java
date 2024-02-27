package com.project.aiyue.bo;

import com.project.aiyue.dao.po.ReadPlanVip;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
public class PayReadPlanReqBO implements Serializable {
    private static final long serialVersionUID = 2050106407668652186L;
    private String userId;
    private String openId;
    private ReadPlanVip readPlanVip;

}
