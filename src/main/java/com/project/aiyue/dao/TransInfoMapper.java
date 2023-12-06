package com.project.aiyue.dao;

import com.project.aiyue.dao.po.TransInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TransInfoMapper {
    int deleteByPrimaryKey(Long transId);

    int insert(TransInfo record);

    int insertNew(TransInfo record);

    int insertSelective(TransInfo record);

    TransInfo selectByPrimaryKey(Long transId);

    int updateByPrimaryKeySelective(TransInfo record);

    int updateByPrimaryKey(TransInfo record);
}