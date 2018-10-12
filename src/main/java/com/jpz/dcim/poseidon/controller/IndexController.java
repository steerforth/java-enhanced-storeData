package com.jpz.dcim.poseidon.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Program: poseidon
 * @Author: Steerforth
 * @Description: 首页
 * @Date: 2018-10-12 19:51
 */
@Controller
@RequestMapping("")
public class IndexController {
    @GetMapping("")
    public String index(){
        return "forward:static/index.html";
    }
}
