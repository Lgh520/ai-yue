package com.project.aiyue.controller;


import com.project.aiyue.constant.ResponCodeConstant;
import com.project.aiyue.dao.po.BookBag;
import com.project.aiyue.bo.base.CommonRespon;
import com.project.aiyue.service.BookBagService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/bookBag")
@Api(tags = "书包服务接口")
public class BookBagController {
    @Autowired
    private BookBagService bookBagService;

    @GetMapping()
    @ApiOperation("书包查询接口")
    public CommonRespon<List<BookBag>> getList(String userId) {
        try {
            if (userId == null) {
                return CommonRespon.error(ResponCodeConstant.ERROR_CODE, "用户id为空");
            }
            List<BookBag> list = bookBagService.getList(userId);
            return CommonRespon.success(list);
        } catch (Exception e) {
            e.printStackTrace();
            log.info("图书查询失败：{}", e.getMessage());
            return CommonRespon.error(ResponCodeConstant.ERROR_CODE, e.getMessage());
        }
    }

    @GetMapping("/{userId}/add")
    @ApiOperation("添加书包书籍接口")
    public CommonRespon<String> add(@PathVariable @Valid String userId, @Valid Long bookId) {
        try {
            if (userId == null || bookId == null) {
                return CommonRespon.error(ResponCodeConstant.ERROR_CODE, "用户id或书籍id为空");
            }
            Boolean insert = bookBagService.insert(userId, bookId);
            if (insert) {
                return CommonRespon.success("添加书包成功");
            }
            return CommonRespon.error(ResponCodeConstant.ERROR_CODE, "添加书包失败，请重试");
        } catch (Exception e) {
            e.printStackTrace();
            log.info("添加书包失败：{}", e.getMessage());
            return CommonRespon.error(ResponCodeConstant.ERROR_CODE, e.getMessage());
        }
    }

    @GetMapping("/remove")
    @ApiOperation("删除书包书籍接口")
    public CommonRespon<String> remove(Long id) {
        try {
            if (id == null) {
                return CommonRespon.error(ResponCodeConstant.ERROR_CODE, "书包id为空");
            }
            bookBagService.remove(id);
            return CommonRespon.success("删除书籍成功");

        } catch (Exception e) {
            e.printStackTrace();
            log.info("添加书包失败：{}", e.getMessage());
            return CommonRespon.error(ResponCodeConstant.ERROR_CODE, e.getMessage());
        }
    }
}
