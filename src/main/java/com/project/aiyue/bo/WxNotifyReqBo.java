package com.project.aiyue.bo;

import lombok.Data;
import java.io.Serializable;

@Data
public class WxNotifyReqBo implements Serializable {
    private static final long serialVersionUID = 5415726785020361489L;
    private ResourceBO resource;
}
