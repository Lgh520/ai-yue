package com.project.aiyue.dao;

import com.project.aiyue.dao.po.BookRent;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BookRentMapper {
    int deleteByPrimaryKey(Long rentId);

    long insert(BookRent record);

    int insertSelective(BookRent record);

    BookRent selectByPrimaryKey(Long rentId);

    int updateByPrimaryKeySelective(BookRent record);

    int updateByPrimaryKey(BookRent record);

    List<BookRent> noReturnBooKByUserId(String userId);

    List<BookRent> selectByUserId(String userId);
}