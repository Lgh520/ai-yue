package com.project.aiyue.dao;

import com.project.aiyue.dao.po.SysParam;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SysParamMapper {
    int insert(SysParam record);

    int insertSelective(SysParam record);
}