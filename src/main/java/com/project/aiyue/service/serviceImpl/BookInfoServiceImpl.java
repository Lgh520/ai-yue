package com.project.aiyue.service.serviceImpl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.project.aiyue.dao.BookInfoMapper;
import com.project.aiyue.dao.po.BookInfo;
import com.project.aiyue.exception.CommonException;
import com.project.aiyue.service.BookInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@Slf4j
public class BookInfoServiceImpl implements BookInfoService {
    @Autowired
    private BookInfoMapper bookInfoMapper;
    @Override
    public PageInfo<BookInfo> getList(BookInfo bookInfo) {
        if(bookInfo == null){
            bookInfo.setPageSize(10);
            bookInfo.setPageNum(0);
        }
        PageHelper.startPage(bookInfo.getPageNum(), bookInfo.getPageSize());
        List<BookInfo> list = bookInfoMapper.getList();
        PageInfo<BookInfo> bookInfoPageInfo = new PageInfo<>(list);
        return bookInfoPageInfo;
    }

    @Override
    public BookInfo getInfo(Long id) {
        if(id == null){
            throw new CommonException(-1,"图书ID不能为空");
        }
        BookInfo bookInfo = bookInfoMapper.selectByPrimaryKey(id);
        if (bookInfo == null){
            log.info("获取图书详情失败，图书ID也不存在，id={}",id);
            throw new CommonException(-1,"获取图书详情失败，图书ID不存在");
        }
        return bookInfo;
    }

    @Override
    public Boolean insert(BookInfo bookInfo) {
        if(bookInfo == null){
            log.info("图书信息不能为空");
            throw new CommonException(-1,"图书信息不能为空");
        }
        boolean b = bookInfo.getIsbn10() == null || bookInfo.getIsbn13() == null;
        if(b){
            log.info("请检查10位ISBN码和13位ISBN码是否为空，10位ISBN码={}，13位ISBN码={}",bookInfo.getIsbn10(),bookInfo.getIsbn13());
            throw new CommonException(-1,"请检查10位ISBN码和13位ISBN码是否为空");
        }
        //图书是否存在
        Long id = bookInfoMapper.getIdByIsbn(bookInfo.getIsbn10(), bookInfo.getIsbn13());
        if(id != null){
            //存在更新库存
            Long integer = bookInfoMapper.updateBookCountById(id);
            if (integer == 0){
                log.info("图书添加失败，请重试");
                return false;
            }
            return true;
        }
        //不存在则添加图书
        if(bookInfo.getTitle() == null || bookInfo.getTitle().equals("")){
            throw new CommonException(-1,"图书名称不能为空");
        }
        int insert = bookInfoMapper.insert(bookInfo);
        if(insert == 0){
            log.info("图书添加失败，请重试");
            return false;
        }
        return true;
    }
}
