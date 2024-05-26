package com.test.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyAuthController {

    @RequestMapping("/")
    public String index() {
        return "index";
    }

    @RequestMapping("/toLogin")
    public String toLogin() {
        return "toLogin";
    }

    @RequestMapping("/role1")
    public String role1() {
        return "role1";
    }

    @RequestMapping("/role2")
    public String role2() {
        return "role2";
    }

    @RequestMapping("/role3")
    public String role3() {
        return "role3";
    }

}
