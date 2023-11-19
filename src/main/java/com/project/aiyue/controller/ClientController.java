package com.project.aiyue.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class ClientController {

    @GetMapping("/getInfo")
    @ResponseBody
    public String getInfo(){
        return "aspoifjwef";
    }
}
