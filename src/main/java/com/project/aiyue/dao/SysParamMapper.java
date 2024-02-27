package com.project.aiyue.dao;

import com.project.aiyue.dao.po.SysParam;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysParamMapper {
    int insert(SysParam record);

    List<SysParam> selectDict(SysParam record);

    int insertSelective(SysParam record);

    String selectByParamCodeAndParamValue(String code,String value);
}