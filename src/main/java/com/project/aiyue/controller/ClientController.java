package com.project.aiyue.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@Controller("/client")
public class ClientController {

    @GetMapping("/getInfo")
    @ResponseBody
    public String getInfo(){
        return "aspoifjwef";
    }
}
