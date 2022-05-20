package com.project.together.controller;

import com.project.together.config.auth.PrincipalDetails;
import com.project.together.entity.Report;
import com.project.together.entity.User;
import com.project.together.service.ReportService;
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

@Controller
@RequiredArgsConstructor
@Slf4j
public class ReportController {

    private final ReportService reportService;
    private final UserService userService;

    @GetMapping("/report/reportForm")
    public String reportForm() {
        return "report/reportForm";
    }

    @PostMapping("/report/reportForm")
    public String reportProc(Model model, @RequestParam String reportedUser, @RequestParam String reportText,
                             @AuthenticationPrincipal PrincipalDetails user) {
        if(userService.findById(reportedUser) == null) {
            log.info(user.getUsername());
            return "items/rejectForm";
        }

        User reportingUser = userService.findById(user.getUsername());

        Report report = new Report();
        report.setReportedUser(reportedUser);
        report.setReportingUser(reportingUser.getUserId());
        report.setReportText(reportText);
        report.setCreatedAt(LocalDateTime.now());
        reportService.save(report);

        return "redirect:/";
    }
}
