package com.project.aiyue.service;


import com.github.pagehelper.PageInfo;
import com.project.aiyue.bo.BookRentWrapper;
import com.project.aiyue.bo.SearchWrapper;
import com.project.aiyue.dao.po.BookInfo;

import java.util.List;

public interface BookInfoService {
    PageInfo<BookInfo> getList(BookInfo bookInfo);
    BookInfo getInfo(Long id);
    Boolean insert(BookInfo bookInfo);
    List<BookRentWrapper> borrow(List<BookInfo> list, String userId);
    List<BookInfo> getNewlyBook();
    PageInfo<BookInfo> search(SearchWrapper searchWrapper);
}
