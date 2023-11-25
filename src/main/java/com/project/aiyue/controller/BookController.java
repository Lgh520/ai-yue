package com.project.aiyue.controller;


import com.github.pagehelper.PageInfo;
import com.project.aiyue.constant.ResponCodeConstant;
import com.project.aiyue.dao.bo.BookRentWrapper;
import com.project.aiyue.dao.po.BookInfo;
import com.project.aiyue.dao.po.UserInfo;
import com.project.aiyue.responor.CommonRespon;
import com.project.aiyue.service.BookInfoService;
import com.project.aiyue.service.UserInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/bookInfo")
@Api(tags = "图书服务接口")
public class BookController {
    @Autowired
    private BookInfoService bookInfoService;

    @GetMapping()
    @ApiOperation("图书查询接口")
    public CommonRespon<PageInfo<BookInfo>> getList(BookInfo bookInfo)  {
        try {
            PageInfo<BookInfo> result = new PageInfo<BookInfo>();
            if(bookInfo == null){
                result = bookInfoService.getList(null);
            }else {
                result = bookInfoService.getList(bookInfo);
            }
            if(result != null){
                return CommonRespon.success(result);
            }
        }catch (Exception e){
            e.printStackTrace();
            log.info("图书查询失败：{}",e.getMessage());
            return  CommonRespon.error(ResponCodeConstant.ERROR_CODE,e.getMessage());
        }
        return  CommonRespon.error(ResponCodeConstant.ERROR_CODE,"图书查询失败，请重试");
    }
    @GetMapping("/{id}")
    @ApiOperation("获取图书详情")
    public CommonRespon<BookInfo> getInfo(@PathVariable Long id)  {
        try {
            BookInfo info = bookInfoService.getInfo(id);
            if(info != null){
                return CommonRespon.success(info);
            }
        }catch (Exception e){
            e.printStackTrace();
            log.info("获取图书详情失败：{}",e.getMessage());
            return  CommonRespon.error(ResponCodeConstant.ERROR_CODE,e.getMessage());
        }
        return  CommonRespon.error(ResponCodeConstant.ERROR_CODE,"获取图书详情，请重试");
    }
    @PostMapping()
    @ApiOperation("新增图书")
    public CommonRespon<Boolean> insert(@RequestBody @Valid BookInfo bookInfo)  {
        try {
            Boolean insert = bookInfoService.insert(bookInfo);
            if(insert){
                return CommonRespon.success(insert);
            }
        }catch (Exception e){
            e.printStackTrace();
            log.info("新增图书失败：{}",e.getMessage());
            return  CommonRespon.error(ResponCodeConstant.ERROR_CODE,e.getMessage());
        }
        return  CommonRespon.error(ResponCodeConstant.ERROR_CODE,"新增图书失败，请重试");
    }
    @PostMapping("/{id}/borrow")
    @ApiOperation("图书借阅")
    public CommonRespon<List<BookRentWrapper>> Borrow(@RequestBody @Valid List<BookInfo> list,@PathVariable String id)  {
        try {
            List<BookRentWrapper> borrow = bookInfoService.borrow(list,id);
            if(borrow != null){
                return CommonRespon.success(borrow);
            }
        }catch (Exception e){
            e.printStackTrace();
            log.info("图书借阅失败：{}",e.getMessage());
            return  CommonRespon.error(ResponCodeConstant.ERROR_CODE,e.getMessage());
        }
        return  CommonRespon.error(ResponCodeConstant.ERROR_CODE,"图书借阅失败，请重试");
    }
    @GetMapping("/newlyBook")
    @ApiOperation("获取最新上架图书")
    public CommonRespon<List<BookInfo>> newlyBook()  {
        try {
            List<BookInfo> newlyBook = bookInfoService.getNewlyBook();
            if(newlyBook != null){
                return CommonRespon.success(newlyBook);
            }
        }catch (Exception e){
            e.printStackTrace();
            log.info("获取最新上架图书：{}",e.getMessage());
            return  CommonRespon.error(ResponCodeConstant.ERROR_CODE,e.getMessage());
        }
        return  CommonRespon.error(ResponCodeConstant.ERROR_CODE,"获取最新上架图书，请重试");
    }

}
