package com.project.aiyue.service;


import com.github.pagehelper.PageInfo;
import com.project.aiyue.dao.po.BookInfo;
import com.project.aiyue.dao.po.UserInfo;

import java.util.List;

public interface BookInfoService {
    PageInfo<BookInfo> getList(BookInfo bookInfo);
    BookInfo getInfo(Long id);
    Boolean insert(BookInfo bookInfo);
    Boolean borrow(List<BookInfo> list);
}
