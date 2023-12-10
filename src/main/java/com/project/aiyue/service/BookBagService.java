package com.project.aiyue.service;


import com.github.pagehelper.PageInfo;
import com.project.aiyue.bo.BookRentWrapper;
import com.project.aiyue.bo.SearchWrapper;
import com.project.aiyue.dao.po.BookBag;
import com.project.aiyue.dao.po.BookInfo;

import java.util.List;

public interface BookBagService {
    List<BookBag> getList(String userId);
    Boolean insert(String userId,Long bookId);
    void remove(Long id);
}
