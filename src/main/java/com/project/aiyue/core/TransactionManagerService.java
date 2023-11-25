package com.project.aiyue.core;

import com.project.aiyue.constant.CommonConstant;
import com.project.aiyue.dao.BookInfoMapper;
import com.project.aiyue.dao.BookRentMapper;
import com.project.aiyue.dao.bo.BookRentWrapper;
import com.project.aiyue.dao.po.BookInfo;
import com.project.aiyue.dao.po.BookRent;
import com.project.aiyue.service.BookInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class TransactionManagerService {
    @Autowired
    private BookInfoMapper bookInfoMapper;
    @Autowired
    private BookRentMapper bookRentMapper;

    public BookRentWrapper borrowBook(BookInfo bookInfo, String userId){
        BookRentWrapper result = new BookRentWrapper();
        try{
            BookInfo info = bookInfoMapper.selectByPrimaryKey(bookInfo.getBookId());
            result.setBookName(info.getTitle());
            result.setBookId(info.getBookId());
            Long aLong = bookInfoMapper.borrowBookById(bookInfo.getBookId(), bookInfo.getBookCounts());
            if(aLong != 0){
                BookRent bookRent = new BookRent();
                bookRent.setUserId(userId);
                bookRent.setBookId(bookInfo.getBookId());
                bookRent.setIsBack(CommonConstant.BOOK_RENT_NOT_BACK);
                bookRent.setBookCount(bookInfo.getBookCounts());
                bookRentMapper.insert(bookRent);
                result.setRentSuccess(true);
                return result;
            }
        }catch (Exception e){
            log.info("借阅书记失败，请检查库存。bookId={}",bookInfo.getBookId());
            result.setRentSuccess(false);
        }
        result.setRentSuccess(false);
        return result;
    }
}
