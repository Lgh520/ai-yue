package com.project.aiyue.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.project.aiyue.bo.ReqBO;
import com.project.aiyue.dao.BookInfoMapper;
import com.project.aiyue.dao.po.BookInfo;
import com.project.aiyue.responor.CommonRespon;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.ws.Response;
import java.util.List;

@Slf4j
@RestController
public class ClientController {

    @Autowired
    private BookInfoMapper bookInfoMapper;

//    @GetMapping("/getInfo")
//    @ResponseBody
//    public String getInfo(){
//        return "aspoifjwef";
//    }

    @GetMapping("/getInfo")
    @ResponseBody
    public CommonRespon<PageInfo<BookInfo>> getInfo(ReqBO reqBO){
        ReqBO reqBO1 = new ReqBO();
        reqBO1.setPageNum(1);
        reqBO1.setPageSize(1);
        PageHelper.startPage(reqBO1.getPageNum(),reqBO1.getPageSize());
        List<BookInfo> bookInfos = bookInfoMapper.selectList();
        CommonRespon<PageInfo<BookInfo>> success = CommonRespon.success(null);
        return CommonRespon.success(new PageInfo<BookInfo>(bookInfos));
    }


}
