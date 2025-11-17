package com.sky.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/demo")
public class DemoController {
    @Autowired
    RestTemplate restTemplate;

    @GetMapping
    public String hello() {
        return restTemplate.getForObject("https://www.baidu.com", String.class);
    }
}
