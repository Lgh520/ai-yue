package com.project.aiyue.bo;

import com.project.aiyue.dao.po.ReadPlanVip;
import lombok.Data;

import java.io.Serializable;

@Data
public class PayReadPlanReqBO implements Serializable {
    private static final long serialVersionUID = 2050106407668652186L;
    private String userId;
    private String openId;
    private ReadPlanVip readPlanVip;

}
