package com.example.demo.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {


    @RequestMapping(value = "/loginSuccess")
    @ResponseBody
    public String OK()
    {
        System.out.println("in Login");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("in Login2" + authentication.getPrincipal());
        return "test";
    }

}


