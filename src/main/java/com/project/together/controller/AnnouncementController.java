package com.project.together.controller;

import com.project.together.config.auth.PrincipalDetails;
import com.project.together.entity.Announcement;
import com.project.together.entity.Inquiry;
import com.project.together.entity.User;
import com.project.together.service.AnnouncementService;
import com.project.together.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
@Slf4j
public class AnnouncementController {

    private final UserService userService;
    private final AnnouncementService announcementService;

    //공지사항 입력
    @GetMapping("/admin/announcementForm")
    public String announcementForm() {
        return "admin/announcementForm";
    }

    @PostMapping("/admin/announcementForm")
    public String announcementForm(@AuthenticationPrincipal PrincipalDetails user, @RequestParam String announcementText,
                                   @RequestParam String announcementTitle) {
        User loginUser = userService.findById(user.getUsername());//로그인 유저 조회
        log.info(announcementText);

        Announcement announcement = new Announcement();
        announcement.setTitle(announcementTitle);
        announcement.setText(announcementText);
        announcement.setCreatedAt(LocalDateTime.now());
        announcementService.save(announcement);

        return "redirect:/";
    }

    //공지사항 한개씩 detail하게
    @GetMapping("/announcement/{aId}/details")
    public String announcementDetail(@PathVariable("aId") Long aId, Model model){
        Announcement adetail = announcementService.findOne(aId);
        model.addAttribute("adetail", adetail);
        return "/announcement/details";
    }

    //공지사항 수정
    @GetMapping("/announcement/{aId}/updateForm")
    public String announcementUpdate(@PathVariable("aId") Long aId, Model model){
        Announcement adetail = announcementService.findOne(aId);
        model.addAttribute("adetail", adetail);
        return "/announcement/updateForm";
    }

    @PostMapping("/announcement/updateForm")
    public String announcementUpdate(@PathVariable("aId") Long aId, Model model, @RequestParam String announcementText,
                                     @RequestParam String announcementTitle){
        Announcement adetail = announcementService.findOne(aId);
        adetail.setTitle(announcementTitle);
        adetail.setTitle(announcementText);
        adetail.setCreatedAt(LocalDateTime.now());
        announcementService.save(adetail);

        return "redirect:/";
    }
}
