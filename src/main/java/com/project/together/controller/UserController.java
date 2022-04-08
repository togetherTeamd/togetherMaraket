package com.project.together.controller;

import com.project.together.entity.Address;
import com.project.together.entity.User;
import com.project.together.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.*;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @GetMapping("/users/new")
    public String createForm(Model model) {
        model.addAttribute("userForm", new UserForm());
        log.info("회원가입 페이지");
        return "users/createUserForm";
    }

    @PostMapping("/users/new")
    public String create(@Valid UserForm form, BindingResult result) {

        if(result.hasErrors()) {
            return "users/createUserForm";
        }

        User user = new User();
        user.setUserId(form.getUserId());
        user.setUserPw(form.getUserPw());
        user.setUserName(form.getUserName());
        user.setUserPhone(form.getUserPhone());
        user.setCreatedAt(LocalDateTime.now());
        userService.join(user);
        log.info("회원가입 성공");
        return "redirect:/";
    }
}
