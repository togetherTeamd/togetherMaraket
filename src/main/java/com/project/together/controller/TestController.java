package com.project.together.controller;

import com.project.together.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

//테스트용도 입니당
@Controller
public class TestController {

    @Autowired
    private TestService testService;

    //db연동이 잘되는지 확인용도
    @GetMapping("/list")
    public String testList(Model model){
        model.addAttribute("list", testService.testList());
        return "list";
    }

    @GetMapping("/")
    public String index(){
        return "index";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    //qr코드 읽히는지 테스트용
    @GetMapping("/test")
    public String showIndex(){
        return "test";
    }
}
