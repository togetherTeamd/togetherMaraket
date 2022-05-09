package com.project.together.controller;

import com.project.together.config.auth.PrincipalDetails;
import com.project.together.entity.User;
import com.project.together.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

@RequiredArgsConstructor
@Controller
@Slf4j
public class HomeController {

    private final UserService userService;
    @GetMapping("/")
    public String home(@AuthenticationPrincipal PrincipalDetails loginUser,//스프링 시큐리티 적용 후 세션에 담긴 유저
            Model model) {

        if(loginUser != null) {
            //log.info(loginUser.getUsername());
            log.info(loginUser.getAuthorities().toString());
            User user = userService.findById(loginUser.getUsername());
            model.addAttribute("user", user);
        }
        if(loginUser == null) {
            log.info("로그아웃 홈화면");
            return "home";
        }
        if(loginUser.getAuthorities().toString().equals("[ROLE_ADMIN]")) {
            log.info("관리자 홈화면");
            return "admin/adminHome";
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
