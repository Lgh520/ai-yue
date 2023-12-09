package com.project.aiyue.dao;

import com.project.aiyue.dao.po.BookCollection;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BookCollectionMapper {
    int deleteByPrimaryKey(@Param("userId") String userId, @Param("bookId") Long bookId);

    int insert(BookCollection record);

    int insertSelective(BookCollection record);

    BookCollection selectByPrimaryKey(@Param("userId") String userId, @Param("bookId") Long bookId);

    List<BookCollection> selectByUserId(@Param("userId") String userId);

    int updateByPrimaryKeySelective(BookCollection record);

    int updateByPrimaryKey(BookCollection record);
}