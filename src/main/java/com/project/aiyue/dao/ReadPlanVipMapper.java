package com.project.aiyue.dao;

import com.project.aiyue.dao.po.ReadPlanVip;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface ReadPlanVipMapper {
    int deleteByPrimaryKey(Long vipId);

    int insert(ReadPlanVip record);

    int insertSelective(ReadPlanVip record);

    ReadPlanVip selectByPrimaryKey(Long vipId);

    List<ReadPlanVip> selectByCondition(ReadPlanVip record);

    int updateByPrimaryKeySelective(ReadPlanVip record);

    int updateByPrimaryKey(ReadPlanVip record);
}