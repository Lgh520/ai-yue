package com.project.aiyue.controller;

import com.project.aiyue.dao.SysParamMapper;
import com.project.aiyue.dao.po.SysParam;
import com.project.aiyue.responor.CommonRespon;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/common")
public class ClientController {

    @Autowired
    private SysParamMapper sysParamMapper;

    @PostMapping("/queryType")
    @ApiOperation("查询年龄和主题类型分类")
    public CommonRespon<Map<String,List<SysParam>>> queryType(){
        Map<String,List<SysParam>> map = new HashMap<>();
        SysParam param = new SysParam();
        param.setParamCode("AGE_TYPE");
        param.setParamKey("TYPE");
        List<SysParam> sysParams = sysParamMapper.selectDict(param);
        map.put("ageType",sysParams);
        param.setParamCode("THEME_TYPE");
        param.setParamKey("TYPE");
        List<SysParam> sysParams2 = sysParamMapper.selectDict(param);
        map.put("themeType",sysParams2);
        return CommonRespon.success(map);
    }


}
