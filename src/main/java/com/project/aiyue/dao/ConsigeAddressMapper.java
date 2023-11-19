package com.project.aiyue.dao;

import com.project.aiyue.dao.po.ConsigeAddress;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ConsigeAddressMapper {
    int deleteByPrimaryKey(Integer consignId);

    int insert(ConsigeAddress record);

    int insertSelective(ConsigeAddress record);

    ConsigeAddress selectByPrimaryKey(Integer consignId);

    int updateByPrimaryKeySelective(ConsigeAddress record);

    int updateByPrimaryKey(ConsigeAddress record);
}