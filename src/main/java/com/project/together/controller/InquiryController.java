package com.project.together.controller;

import com.project.together.config.auth.PrincipalDetails;
import com.project.together.entity.Inquiry;
import com.project.together.entity.User;
import com.project.together.service.InquiryService;
import com.project.together.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class InquiryController {

    private final InquiryService inquiryService;
    private final UserService userService;

    @GetMapping("/inquiry/myInquiryList")
    public String myInquiryList(Model model, @AuthenticationPrincipal PrincipalDetails user) {
        User loginUser = userService.findById(user.getUsername());//로그인 유저 조회
        List<Inquiry> userInquiryList = inquiryService.findByUserIdx(loginUser.getUserIdx());//로그인 유저 아이디를 통해 문의 내역조회
        model.addAttribute("inquiryList", userInquiryList);
        return "inquiry/myInquiryList";
    }

    @GetMapping("/inquiry/inquiryForm")
    public String inquiryForm() {
        return "inquiry/inquiryForm";
    }

    @PostMapping("/inquiry/inquiryProc")
    public String inquiryPorc(@AuthenticationPrincipal PrincipalDetails user, @RequestParam String inquiryText) {
        User loginUser = userService.findById(user.getUsername());//로그인 유저 조회
        log.info(inquiryText);
        //문의 생성
        Inquiry inquiry = new Inquiry();
        inquiry.setInquiryText(inquiryText);
        inquiry.setUserIdx(loginUser.getUserIdx());
        inquiry.setCreatedAt(LocalDateTime.now());
        inquiryService.save(inquiry);

        return "redirect:/inquiry/myInquiryList";
    }
}
