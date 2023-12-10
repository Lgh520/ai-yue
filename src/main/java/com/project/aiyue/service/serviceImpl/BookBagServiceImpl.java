package com.project.aiyue.service.serviceImpl;

import com.alibaba.fastjson2.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.project.aiyue.bo.BookRentWrapper;
import com.project.aiyue.bo.SearchWrapper;
import com.project.aiyue.core.TransactionManagerService;
import com.project.aiyue.dao.*;
import com.project.aiyue.dao.po.*;
import com.project.aiyue.exception.CommonException;
import com.project.aiyue.service.BookBagService;
import com.project.aiyue.service.BookInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
public class BookBagServiceImpl implements BookBagService {
    @Autowired
    private BookBagMapper bookBagMapper;
    @Override
    public List<BookBag> getList(String userId) {
        return bookBagMapper.getList(userId);
    }

    @Override
    public Boolean insert(String userId, Long bookId) {
        try{
            //若书包有对应的书籍则+1，否则插入
            BookBag bookBag = bookBagMapper.selectByUserIdAndBookId(userId, bookId);
            if(bookBag == null){
                BookBag bag = new BookBag();
                bag.setBookId(bookId);
                bag.setUserId(userId);
                bookBagMapper.insert(bag);
            }else {
                bookBagMapper.updateBookCount(bookBag.getId());
            }
            return true;
        }catch (Exception e){
            e.printStackTrace();
            log.info("添加书包失败");
            return false;
        }

    }

    @Override
    public void remove( Long id) {
     bookBagMapper.deleteByPrimaryKey(id);
    }
}
