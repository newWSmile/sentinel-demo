package com.smile.sentineldemo.controller;

import com.smile.sentineldemo.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ：Smile(wangyajun)
 * @date ：Created in 2019/4/9 9:50
 * @description：
 */
@RestController
@RequestMapping("/hello")
public class HelloController {

    @Autowired
    private TestService service;


    @GetMapping("/test")
    public String test() {
        return service.test(System.currentTimeMillis());
    }


    @GetMapping("/foo")
    public String foo() {
        return service.hello(System.currentTimeMillis());
    }
}


