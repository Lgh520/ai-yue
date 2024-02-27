package com.project.aiyue.controller;

import com.alibaba.fastjson2.JSON;
import com.project.aiyue.dao.BookCollectionMapper;
import com.project.aiyue.dao.po.BookCollection;
import com.project.aiyue.bo.base.CommonRespon;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Slf4j
@RestController
@CrossOrigin(origins = "*")
@Api(tags = "图书收藏接口")
@RequestMapping("/bookCollection")
public class BookCollectionController {

    @Autowired
    private BookCollectionMapper bookCollectionMapper;

    private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @ApiOperation("用户添加收藏接口")
    @PostMapping("/addCollection")
    public CommonRespon addCollection(@RequestBody BookCollection req){
        log.info("addCollection-用户添加收藏接口入参:{}", JSON.toJSONString(req));
        BookCollection bookCollection = bookCollectionMapper.selectByPrimaryKey(req.getUserId(), req.getBookId());
        if (null != bookCollection){
            bookCollectionMapper.deleteByPrimaryKey(req.getUserId(),req.getBookId());
            return CommonRespon.success(null,"取消收藏成功");
        }
        Date date = new Date();
        String nowStr = SDF.format(date);
        req.setCreateTimeStr(nowStr);
        req.setUpdateTimeStr(nowStr);
        bookCollectionMapper.insertSelective(req);
        return CommonRespon.success(null,"收藏成功");
    }

    @ApiOperation("用户删除收藏接口")
    @PostMapping("/delCollection")
    public CommonRespon delCollection(@RequestBody BookCollection req){
        log.info("delCollection-用户删除收藏接口入参:{}", JSON.toJSONString(req));
        bookCollectionMapper.deleteByPrimaryKey(req.getUserId(),req.getBookId());
        return CommonRespon.success(null);
    }

    @ApiOperation("用户查询收藏接口")
    @PostMapping("/queryCollection")
    public CommonRespon<List<BookCollection>> queryCollection(@RequestBody BookCollection req){
        log.info("queryCollection-用户查询收藏接口入参:{}", JSON.toJSONString(req));
        return CommonRespon.success(bookCollectionMapper.selectByUserId(req.getUserId()));

    }
}
