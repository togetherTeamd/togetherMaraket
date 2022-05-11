package com.project.together.controller;

import com.project.together.entity.Report;
import com.project.together.entity.User;
import com.project.together.service.ReportService;
import com.project.together.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class AdminController {//신고, 문의, 회원관리

    private final UserService userService;
    private final ReportService reportService;
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
        //User user2 = user;//권한 수정
        user.setRole("ROLE_REPORT");
        Long check = userService.update(user);
        System.out.println(check);
        System.out.println(user.getRole());
        System.out.println(userService.findByIdx(userIdx).getRole());

        return "redirect:/admin/userManagement";
    }

    @GetMapping("/admin/report")
    public String reportUser(Model model) {
        List<Report> reports = reportService.findAll();
        model.addAttribute("reports", reports);
        return "admin/reportList";
    }
}
