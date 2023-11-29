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
    List<BookInfo> getList(BookInfo bookInfo);
    List<BookInfo> search(String name);
    List<BookInfo> getNewlyBook();

    Long getIdByIsbn(String isbn10,String isbn13);

    Long addBookCountById(Long id,Integer count);
    Long borrowBookById(Long id,Integer count);
    Integer updateViewCountById(Long id);
    Integer updateRentCountById(Long id);
}