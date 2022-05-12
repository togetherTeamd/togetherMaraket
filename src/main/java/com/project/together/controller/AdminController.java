package com.project.together.controller;

import com.project.together.entity.Inquiry;
import com.project.together.entity.Item;
import com.project.together.entity.Report;
import com.project.together.entity.User;
import com.project.together.service.InquiryService;
import com.project.together.service.ReportService;
import com.project.together.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class AdminController {//신고, 문의, 회원관리

    private final UserService userService;
    private final ReportService reportService;
    private final InquiryService inquiryService;

    @GetMapping("/admin/userManagement")
    public String userList(Model model) {
        List<User> userList = userService.findAll();
        model.addAttribute("userList", userList);
        return "admin/userManagement";
    }

    @PostMapping(value = "/admin/{userIdx}/setReport")
    public String setReport(@PathVariable Long userIdx) { //비매너 유저
        //log.info(userIdx.toString());
        System.out.println(userIdx);
        User user = userService.findByIdx(userIdx);
        user.setRole("ROLE_REPORT");//권한 수정
        Long check = userService.update(user);

        return "redirect:/admin/userManagement";
    }

    @GetMapping("/admin/report")
    public String reportUser(Model model) {
        List<Report> reports = reportService.findAll();
        model.addAttribute("reports", reports);
        return "admin/reportList";
    }

    @GetMapping("/admin/inquiry")
    public String inquiryList(Model model) {
        List<Inquiry> inquiryList = inquiryService.findAll();
        model.addAttribute("inquiryList", inquiryList);
        return "admin/inquiry";
    }

    @GetMapping("/admin/{inquiryId}/answer")
    public String inquiryAnswerForm(@PathVariable("inquiryId") Long inquiryId, Model model) {
        log.info("문의 번호:" + inquiryId.toString());
        model.addAttribute("form", new AnswerForm());
        return "admin/inquiryAnswerForm";
    }

    @PostMapping("/admin/{inquiryId}/answer")
    public String inquiryAnswerProc(@PathVariable("inquiryId") Long inquiryId, @ModelAttribute("form") AnswerForm form) {
        inquiryService.answerInquiry(inquiryId, form.getAnswer());
        return "redirect:/admin/inquiry";
    }
}
