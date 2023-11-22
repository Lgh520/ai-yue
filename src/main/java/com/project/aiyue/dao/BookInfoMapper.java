package com.project.aiyue.dao;

import com.project.aiyue.dao.po.BookInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BookInfoMapper {
    int deleteByPrimaryKey(Long bookId);

    int insert(BookInfo record);

    int insertSelective(BookInfo record);

    BookInfo selectByPrimaryKey(Long bookId);

    List<BookInfo> selectList();

    int updateByPrimaryKeySelective(BookInfo record);

    int updateByPrimaryKey(BookInfo record);
}