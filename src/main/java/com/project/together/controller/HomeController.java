package com.project.together.controller;

import com.project.together.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
@Slf4j
public class HomeController {

    @RequestMapping("/")
    public String home(@SessionAttribute(name = SessionConstants.LOGIN_USER, required = false) User loginUser,
                       Model model) {
        if(loginUser == null) {
            log.info("로그아웃 홈화면");
            return "home";
        }
        model.addAttribute("user", loginUser);
        log.info("로그인 홈화면");
        return "loginHome";
    }
}
