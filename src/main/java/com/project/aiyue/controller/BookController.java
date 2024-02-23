package com.project.aiyue.controller;


import com.alibaba.fastjson2.JSON;
import com.github.pagehelper.PageInfo;
import com.project.aiyue.bo.SearchWrapper;
import com.project.aiyue.constant.ResponCodeConstant;
import com.project.aiyue.bo.BookRentWrapper;
import com.project.aiyue.dao.BookRentMapper;
import com.project.aiyue.dao.po.BookInfo;
import com.project.aiyue.dao.po.BookRent;
import com.project.aiyue.dao.po.DeliveryRecord;
import com.project.aiyue.responor.BorrowReqBO;
import com.project.aiyue.responor.CommonRespon;
import com.project.aiyue.service.BookInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

@Slf4j
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/bookInfo")
@Api(tags = "图书服务接口")
public class BookController {
    @Autowired
    private BookInfoService bookInfoService;

    @Autowired
    private BookRentMapper bookRentMapper;

    @GetMapping()
    @ApiOperation("图书查询接口")
    public CommonRespon<PageInfo<BookInfo>> getList(BookInfo bookInfo) {
        try {
            PageInfo<BookInfo> result = new PageInfo<BookInfo>();
            if (bookInfo == null) {
                result = bookInfoService.getList(null);
            } else {
                result = bookInfoService.getList(bookInfo);
            }
            if (result != null) {
                if (!CollectionUtils.isEmpty(result.getList())) {
                    result.getList().forEach(o -> {
                        if (6 < o.getTitle().length()) {
                            o.setTitle(o.getTitle().substring(0, 6) + "...");
                        }
                    });
                    return CommonRespon.success(result);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.info("图书查询失败：{}", e.getMessage());
            return CommonRespon.error(ResponCodeConstant.ERROR_CODE, e.getMessage());
        }
        return CommonRespon.error(ResponCodeConstant.ERROR_CODE, "图书查询失败，请重试");
    }

    @GetMapping("/{id}")
    @ApiOperation("获取图书详情")
    public CommonRespon<BookInfo> getInfo(@PathVariable Long id) {
        try {
            BookInfo info = bookInfoService.getInfo(id);
            if (info != null) {
                return CommonRespon.success(info);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.info("获取图书详情失败：{}", e.getMessage());
            return CommonRespon.error(ResponCodeConstant.ERROR_CODE, e.getMessage());
        }
        return CommonRespon.error(ResponCodeConstant.ERROR_CODE, "获取图书详情，请重试");
    }

    @PostMapping()
    @ApiOperation("新增图书")
    public CommonRespon<Boolean> insert(@RequestBody @Valid BookInfo bookInfo) {
        try {
            log.info("录入图书信息入参:{}", JSON.toJSONString(bookInfo));
            Boolean insert = bookInfoService.insert(bookInfo);
            if (insert) {
                return CommonRespon.success(insert);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.info("新增图书失败：{}", e.getMessage());
            return CommonRespon.error(ResponCodeConstant.ERROR_CODE, e.getMessage());
        }
        return CommonRespon.error(ResponCodeConstant.ERROR_CODE, "新增图书失败，请重试");
    }

    @PostMapping("/{id}/borrow")
    @ApiOperation("图书借阅")
    public CommonRespon<List<BookRentWrapper>> borrow(@RequestBody @Valid BorrowReqBO reqBO, @PathVariable String id) {
        try {
            log.info("borrow-图书借阅接口入参:reqBO:{},id:{}",JSON.toJSONString(reqBO),JSON.toJSONString(id));
            List<BookRentWrapper> borrow = bookInfoService.borrow(reqBO, id);
            if (borrow != null) {
                return CommonRespon.success(borrow);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("图书借阅失败：{}", e.getMessage());
            return CommonRespon.error(ResponCodeConstant.ERROR_CODE, e.getMessage());
        }
        return CommonRespon.error(ResponCodeConstant.ERROR_CODE, "图书借阅失败，请重试");
    }

    @GetMapping("/newlyBook")
    @ApiOperation("获取最新上架图书")
    public CommonRespon<List<BookInfo>> newlyBook() {
        try {
            List<BookInfo> newlyBook = bookInfoService.getNewlyBook();
            if (newlyBook != null) {
                newlyBook.forEach(o -> {
                    if (6 < o.getTitle().length()) {
                        o.setTitle(o.getTitle().substring(0, 6) + "...");
                    }
                });
                return CommonRespon.success(newlyBook);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.info("获取最新上架图书：{}", e.getMessage());
            return CommonRespon.error(ResponCodeConstant.ERROR_CODE, e.getMessage());
        }
        return CommonRespon.error(ResponCodeConstant.ERROR_CODE, "获取最新上架图书失败，请重试");
    }

    @PostMapping("/search")
    @ApiOperation("图书查询")
    public CommonRespon<PageInfo<BookInfo>> search(@RequestBody SearchWrapper searchWrapper) {
        try {
            PageInfo<BookInfo> result = bookInfoService.search(searchWrapper);
            if (result != null) {
                if (!CollectionUtils.isEmpty(result.getList())) {
                    result.getList().forEach(o -> {
                        if (6 < o.getTitle().length()) {
                            o.setTitle(o.getTitle().substring(0, 6) + "...");
                        }
                    });
                    return CommonRespon.success(result);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.info("图书查询：{}", e.getMessage());
            return CommonRespon.error(ResponCodeConstant.ERROR_CODE, e.getMessage());
        }
        return CommonRespon.error(ResponCodeConstant.ERROR_CODE, "图书查询失败，请重试");
    }

    @ApiOperation("用户查询借阅记录接口")
    @PostMapping("/queryUserRentHistory")
    public CommonRespon queryUserRentHistory(@RequestBody String userId){
        List<BookRent> bookRents = bookRentMapper.selectByUserId(userId);
        return CommonRespon.success(null,"已被别人抢先一步，请看看其他单子吧");
    }

    @ApiOperation("查询租借图书的详细信息")
    @PostMapping("/queryDeliveryBookInfo")
    public CommonRespon<List<BookInfo>> queryDeliveryBookInfo(@RequestBody String rentIdStr){
        log.info("查询租借图书的详细信息,入参:{}",rentIdStr);
        String[] split = rentIdStr.split(",");
        List<BookInfo> bookInfos = bookInfoService.queryDeliveryBookInfo(Arrays.asList(split));
        log.info("查询租借图书的详细信息出参:{}",JSON.toJSONString(bookInfos));
        return CommonRespon.success(bookInfos);
    }
}
