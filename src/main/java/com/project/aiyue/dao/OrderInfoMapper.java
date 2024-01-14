package com.project.aiyue.dao;

import com.project.aiyue.dao.po.OrderInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderInfoMapper {
    int deleteByPrimaryKey(Long orderId);

    int insert(OrderInfo record);

    long insertNew(OrderInfo record);

    int insertSelective(OrderInfo record);

    OrderInfo selectByPrimaryKey(Long orderId);

    int updateByPrimaryKeySelective(OrderInfo record);

    int updateByPrimaryKey(OrderInfo record);

    int updateByStatus(OrderInfo record);
}