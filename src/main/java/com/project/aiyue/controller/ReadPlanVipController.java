package com.project.aiyue.controller;

import com.project.aiyue.dao.ReadPlanVipMapper;
import com.project.aiyue.dao.po.ReadPlanVip;
import com.project.aiyue.bo.base.CommonRespon;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/readPlanVip")
@Api(tags = "配置vip卡接口")
public class ReadPlanVipController {

    @Autowired
    private ReadPlanVipMapper readPlanVipMapper;

    @PostMapping("/queryVipCard")
    @ApiOperation("查询VIP卡")
    public CommonRespon<List<ReadPlanVip>> queryVipCard(@RequestBody ReadPlanVip req){
        log.info("查询VIP卡入参:{}",req);
        return CommonRespon.success(readPlanVipMapper.selectByCondition(req));
    }

    @PostMapping("/deleteVipCard")
    @ApiOperation("删除VIP卡")
    public CommonRespon deleteVipCard(@RequestBody Long vipId){
        log.info("删除VIP卡入参:{}",vipId);
        readPlanVipMapper.deleteByPrimaryKey(vipId);
        return CommonRespon.success(null);
    }

    @PostMapping("/updateVipCard")
    @ApiOperation("更新VIP卡")
    public CommonRespon updateVipCard(@RequestBody ReadPlanVip req){
        log.info("更新VIP卡入参:{}",req);
        readPlanVipMapper.updateByPrimaryKeySelective(req);
        return CommonRespon.success(null);
    }

    @PostMapping("/addVipCard")
    @ApiOperation("新增VIP卡")
    public CommonRespon addVipCard(@RequestBody ReadPlanVip req){
        log.info("新增VIP卡入参:{}",req);
        readPlanVipMapper.insertSelective(req);
        return CommonRespon.success(null);
    }
}
