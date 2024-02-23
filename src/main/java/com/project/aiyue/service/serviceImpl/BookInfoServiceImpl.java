package com.project.aiyue.service.serviceImpl;

import com.alibaba.fastjson2.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.project.aiyue.bo.SearchWrapper;
import com.project.aiyue.core.TransactionManagerService;
import com.project.aiyue.dao.*;
import com.project.aiyue.bo.BookRentWrapper;
import com.project.aiyue.dao.po.*;
import com.project.aiyue.exception.CommonException;
import com.project.aiyue.responor.BorrowReqBO;
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
public class BookInfoServiceImpl implements BookInfoService {
    @Autowired
    private BookInfoMapper bookInfoMapper;
    @Autowired
    private TransactionManagerService transactionManagerService;
    @Autowired
    private DeliveryRecordMapper deliveryRecordMapper;
    @Autowired
    private ReadPlanVipMapper readPlanVipMapper;
    @Autowired
    private UserInfoMapper userInfoMapper;
    @Autowired
    private BookRentMapper bookRentMapper;

    @Override
    public PageInfo<BookInfo> getList(BookInfo bookInfo) {
        if (Objects.isNull(bookInfo)) {
            bookInfo = new BookInfo();
            bookInfo.setPageSize(10);
            bookInfo.setPageNum(1);
        }
        if (bookInfo.getPageNum() == null || bookInfo.getPageSize() == null) {
            bookInfo.setPageSize(10);
            bookInfo.setPageNum(1);
        }
        PageHelper.startPage(bookInfo.getPageNum(), bookInfo.getPageSize());
        List<BookInfo> list = bookInfoMapper.getList(bookInfo);
        PageInfo<BookInfo> bookInfoPageInfo = new PageInfo<>(list);
        return bookInfoPageInfo;
    }

    @Override
    public BookInfo getInfo(Long id) {
        if (id == null) {
            throw new CommonException(-1, "图书ID不能为空");
        }
        BookInfo bookInfo = bookInfoMapper.selectByPrimaryKey(id);
        bookInfoMapper.updateViewCountById(id);
        if (bookInfo == null) {
            log.info("获取图书详情失败，图书ID也不存在，id={}", id);
            throw new CommonException(-1, "获取图书详情失败，图书ID不存在");
        }
        return bookInfo;
    }

    @Override
    public Boolean insert(BookInfo bookInfo) {
        if (bookInfo == null) {
            log.info("图书信息不能为空");
            throw new CommonException(-1, "图书信息不能为空");
        }
        boolean b = bookInfo.getIsbn10() == null || bookInfo.getIsbn13() == null;
        if (b) {
            log.error("请检查10位ISBN码和13位ISBN码是否为空，10位ISBN码={}，13位ISBN码={}", bookInfo.getIsbn10(), bookInfo.getIsbn13());
            throw new CommonException(-1, "请检查10位ISBN码和13位ISBN码是否为空");
        }
        //图书是否存在
        Long id = bookInfoMapper.getIdByIsbn(bookInfo.getIsbn10(), bookInfo.getIsbn13());
        if (id != null) {
            log.info("10位ISBN码={}，13位ISBN码={},图书:{}存在，开始更新库存，库存数量+{}",bookInfo.getIsbn10(), bookInfo.getIsbn13(),bookInfo.getTitle(),bookInfo.getBookCounts());
            //存在更新库存
            Long integer = bookInfoMapper.addBookCountById(id,bookInfo.getBookCounts());
            if (integer == 0) {
                log.info("图书添加失败，请重试");
                return false;
            }
            return true;
        }
        //不存在则添加图书
        int insert = bookInfoMapper.insert(bookInfo);
        if (insert == 0) {
            log.error("图书添加失败，请重试");
            return false;
        }
        return true;
    }

    @Override
    public List<BookRentWrapper> borrow(BorrowReqBO req, String userId) {
        ArrayList<BookRentWrapper> result = new ArrayList<>();
        if (CollectionUtils.isEmpty(req.getList())) {
            log.info("借阅图书不能为空");
            throw new CommonException(-1, "借阅图书不能为空");
        }
        List<BookInfo> list = req.getList();
        //判断用户是否可以借阅
        UserInfo info = userInfoMapper.selectByPrimaryKey(userId);
        if(Objects.isNull(info)){
            log.error("用户={}不存在",userId);
            throw new CommonException(-1,"用户不存在");
        }
        List<BookRent> bookRents = bookRentMapper.noReturnBooKByUserId(userId);
        if(!CollectionUtils.isEmpty(bookRents)){
            throw new CommonException(-1,"请先把上次借阅的书归还哦！");
        }
        Date vipEndTime = info.getVipEndTime();
        if(vipEndTime.before(new Date())){
            throw new CommonException(-1,"vip卡已到期啦，请重新购买！");
        }
//        int sum = list.stream().mapToInt(e -> e.getBookCounts()).sum();
        int sum = list.size();
        ReadPlanVip readPlanVip = readPlanVipMapper.selectByPrimaryKey(info.getVipID());
        if(sum > readPlanVip.getPerRentCount()){
            throw new CommonException(-1,"当前只能借阅"+readPlanVip.getPerRentCount()+"本哦。");
        }
        for (BookInfo bookInfo : list){
            BookRentWrapper bookRentWrapper = transactionManagerService.borrowBook(bookInfo, userId);
            result.add(bookRentWrapper);
        }
        //有借阅成功的书籍就插入一条未接单的配送信息
        List<BookRentWrapper> successBook = result.stream().filter(e -> e.getRentSuccess()).collect(Collectors.toList());
        if(!CollectionUtils.isEmpty(successBook)){
            StringBuilder sb = new StringBuilder();
            successBook.stream().forEach(e->sb.append(e.getRentId()));
            DeliveryRecord deliveryRecord = new DeliveryRecord();
            deliveryRecord.setRentId(sb.toString());
            deliveryRecord.setNameStr(req.getNameStr());
            deliveryRecord.setPhoneNumber(req.getPhoneNumber());
            deliveryRecord.setAddressStr(req.getAddressStr());
            deliveryRecord.setDeliveryStatus("0");
            deliveryRecordMapper.insert(deliveryRecord);
        }
        return result;
    }

    @Override
    public List<BookInfo> getNewlyBook() {
        List<BookInfo> newlyBook = bookInfoMapper.getNewlyBook();
        return newlyBook;
    }

    @Override
    public PageInfo<BookInfo> search(SearchWrapper searchWrapper) {
        PageInfo<BookInfo> result = new PageInfo<BookInfo>();
        if(searchWrapper.getName() == null){
            return getList(null);
        }
        if (searchWrapper.getPageNum() == null || searchWrapper.getPageSize() == null) {
            searchWrapper.setPageSize(10);
            searchWrapper.setPageNum(1);
        }
        PageHelper.startPage(searchWrapper.getPageNum(), searchWrapper.getPageSize());
        List<BookInfo> search = bookInfoMapper.search(searchWrapper.getName());
        result.setList(search);
        return result;
    }

    @Override
    public List<BookInfo> queryDeliveryBookInfo(List<String> list) {
        return bookInfoMapper.queryDeliveryBookInfo(list);
    }
}
