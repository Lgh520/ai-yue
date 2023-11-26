package com.project.aiyue.dao;

import com.project.aiyue.dao.po.ReadPlanVip;

import java.util.List;

public interface ReadPlanVipMapper {
    int deleteByPrimaryKey(Long vipId);

    int insert(ReadPlanVip record);

    int insertSelective(ReadPlanVip record);

    ReadPlanVip selectByPrimaryKey(Long vipId);

    List<ReadPlanVip> selectByCondition(ReadPlanVip record);

    int updateByPrimaryKeySelective(ReadPlanVip record);

    int updateByPrimaryKey(ReadPlanVip record);
}