package com.project.together.controller;

import com.project.together.entity.User;
import com.project.together.repository.UserRepository;
import com.project.together.service.LoginService;
import com.project.together.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Optional;


@Controller
@Slf4j
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;
    private final UserService userService;
    @GetMapping("/login/new")
    public String LoginForm(Model model, @RequestParam(value = "error", required = false) String error,
                            @RequestParam(value = "exception", required = false) String exception) {
        log.info("로그인 페이지");
        model.addAttribute("error", error);
        model.addAttribute("exception", exception);
        return "login/loginForm";
    }

    @PostMapping("/login/proc")
    public String login(@RequestParam("username")String userId, @RequestParam("password")String userPw) {

        if(loginService.login(userId,userPw) == null){
         return "users/rejectForm";
        }
        log.info("로그인 성공");
        log.info(userId);
        if(userId == "admin")
            return "redirect:/admin";


        return "redirect:/";
    }
    @GetMapping("/users/rejectForm")
    public String loginFail() {
        return "login/rejectForm";
    }


    @PostMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();   // 세션 날림

        }
        log.info("로그아웃 성공");
        return "redirect:/";
    }
}
