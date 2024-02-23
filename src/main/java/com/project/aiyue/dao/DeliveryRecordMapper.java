package com.project.aiyue.dao;

import com.project.aiyue.dao.po.DeliveryRecord;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DeliveryRecordMapper {
    int deleteByPrimaryKey(Long deliveryId);

    int insert(DeliveryRecord record);

    int insertSelective(DeliveryRecord record);

    DeliveryRecord selectByPrimaryKey(Long deliveryId);

    List<DeliveryRecord> queryNoDelivery();

    int updateByPrimaryKeySelective(DeliveryRecord record);

    int updateByPrimaryKey(DeliveryRecord record);
}