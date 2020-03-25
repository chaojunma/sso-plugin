package com.mk.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

    @Value("${server.port}")
    private String port;

    @GetMapping("/test")
    public String index(){
        return  "测试sso服务,端口号为:" + port;
    }
}
