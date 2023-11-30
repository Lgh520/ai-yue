package com.project.aiyue.controller;

import com.project.aiyue.dao.ConsigeAddressMapper;
import com.project.aiyue.dao.po.ConsigeAddress;
import com.project.aiyue.responor.CommonRespon;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Slf4j
@RestController
@Api(tags = "用户配置地址接口")
@RequestMapping("/address")
public class ConsigeAddressController {
    @Autowired
    private ConsigeAddressMapper consigeAddressMapper;

    private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @PostMapping("/queryAddress")
    @ApiOperation("查询个人配置的地址")
    public CommonRespon<List<ConsigeAddress>> queryAddressList(@RequestBody String userId){
        if (StringUtils.isEmpty(userId)){
            return CommonRespon.error(1,"请登录！");
        }
        return CommonRespon.success(consigeAddressMapper.selectByUserId(userId));
    }

    @PostMapping("/addAddress")
    @ApiOperation("新增个人配置的地址")
    public CommonRespon addAddress(@RequestBody ConsigeAddress address){
        if (null == address){
            return CommonRespon.error(1,"入参不能为空！");
        }
        address.setUpdateTimeStr(SDF.format(new Date()));
        address.setCreateTimeStr(address.getUpdateTimeStr());
        consigeAddressMapper.insert(address);
        return CommonRespon.success(null);
    }

    @PostMapping("/updateAddress")
    @ApiOperation("更新个人配置的地址")
    public CommonRespon updateAddress(@RequestBody ConsigeAddress address){
        if (null == address){
            return CommonRespon.error(1,"入参不能为空！");
        }
        address.setUpdateTimeStr(SDF.format(new Date()));
        consigeAddressMapper.updateByPrimaryKeySelective(address);
        return CommonRespon.success(null);
    }

    @PostMapping("/deleteAddress")
    @ApiOperation("删除个人配置的地址")
    public CommonRespon deleteAddress(@RequestBody String id){
        if (StringUtils.isEmpty(id)){
            return CommonRespon.error(1,"入参不能为空！");
        }
        consigeAddressMapper.deleteByPrimaryKey(Integer.parseInt(id));
        return CommonRespon.success(null);
    }
}
