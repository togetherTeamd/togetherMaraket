package com.project.together.controller;

import com.project.together.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
@Slf4j
public class HomeController {

    @RequestMapping("/")
    public String home(@SessionAttribute(name = SessionConstants.LOGIN_USER, required = false) User loginUser,
                       Model model) {
        model.addAttribute("user", loginUser);
        if(loginUser == null) {
            log.info("로그아웃 홈화면");
            return "home";
        }
        if(loginUser.getUserId().equals("admin")) {
            log.info("관리자 홈화면");
            return "adminHome";
        }
        else {
            log.info("로그인 홈화면");
            return "loginHome";
        }
    }

    @GetMapping("/test")
    public String showIndex(){
        return "test";
    }
}
