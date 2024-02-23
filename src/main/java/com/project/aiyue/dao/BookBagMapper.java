package com.project.aiyue.dao;

import com.project.aiyue.dao.po.BookBag;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BookBagMapper {
    int deleteByPrimaryKey(Long id);

    int insert(BookBag record);

    int insertSelective(BookBag record);

    BookBag selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BookBag record);

    int updateByPrimaryKey(BookBag record);

    List<BookBag> getList(String userId);

    BookBag selectByUserIdAndBookId(String userId,Long bookId);
    void updateBookCount(Long id);
}