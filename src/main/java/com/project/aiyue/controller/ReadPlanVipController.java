package com.project.aiyue.controller;

import com.project.aiyue.dao.ReadPlanVipMapper;
import com.project.aiyue.dao.po.ReadPlanVip;
import com.project.aiyue.responor.CommonRespon;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/readPlanVip")
@Api(tags = "配置vip卡接口")
public class ReadPlanVipController {

    @Autowired
    private ReadPlanVipMapper readPlanVipMapper;

    @PostMapping("/queryVipCard")
    @ApiOperation("查询VIP卡")
    public CommonRespon<List<ReadPlanVip>> queryVipCard(@RequestBody ReadPlanVip req){
        return CommonRespon.success(readPlanVipMapper.selectByCondition(req));
    }

    @PostMapping("/deleteVipCard")
    @ApiOperation("删除VIP卡")
    public CommonRespon deleteVipCard(@RequestBody Long vipId){
        readPlanVipMapper.deleteByPrimaryKey(vipId);
        return CommonRespon.success(null);
    }

    @PostMapping("/updateVipCard")
    @ApiOperation("更新VIP卡")
    public CommonRespon updateVipCard(@RequestBody ReadPlanVip req){
        readPlanVipMapper.updateByPrimaryKeySelective(req);
        return CommonRespon.success(null);
    }

    @PostMapping("/addVipCard")
    @ApiOperation("新增VIP卡")
    public CommonRespon addVipCard(@RequestBody ReadPlanVip req){
        readPlanVipMapper.insertSelective(req);
        return CommonRespon.success(null);
    }
}
