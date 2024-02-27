package com.project.aiyue.controller;

import com.project.aiyue.dao.DeliveryRecordMapper;
import com.project.aiyue.dao.po.DeliveryRecord;
import com.project.aiyue.bo.base.CommonRespon;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/delivery")
@Api(tags = "配送服务接口")
public class DeliveryRecordController {

    @Autowired
    private DeliveryRecordMapper deliveryRecordMapper;

    @ApiOperation("查询未配送的订单接口")
    @PostMapping("/queryNoDelivery")
    public CommonRespon queryNoDelivery(){
        return CommonRespon.success(deliveryRecordMapper.queryNoDelivery());
    }

    @ApiOperation("配送员抢单接口")
    @PostMapping("/getDelivery")
    public CommonRespon getDelivery(@RequestBody DeliveryRecord reqBO){
        if (deliveryRecordMapper.updateByPrimaryKeySelective(reqBO) > 0){
            return CommonRespon.success(null,"抢单成功");
        }
        return CommonRespon.success(null,"已被别人抢先一步，请看看其他单子吧");
    }

}
