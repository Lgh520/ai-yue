package com.project.aiyue.service;


import com.github.pagehelper.PageInfo;
import com.project.aiyue.dao.bo.BookRentWrapper;
import com.project.aiyue.dao.po.BookInfo;
import com.project.aiyue.dao.po.UserInfo;

import java.util.List;

public interface BookInfoService {
    PageInfo<BookInfo> getList(BookInfo bookInfo);
    BookInfo getInfo(Long id);
    Boolean insert(BookInfo bookInfo);
    List<BookRentWrapper> borrow(List<BookInfo> list, String userId);
    List<BookInfo> getNewlyBook();
}
