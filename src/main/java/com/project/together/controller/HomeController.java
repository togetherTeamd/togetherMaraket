package com.project.together.controller;

import com.project.together.config.auth.PrincipalDetails;
import com.project.together.entity.Files;
import com.project.together.entity.Recent;
import com.project.together.entity.User;
import com.project.together.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.project.together.entity.Item;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Controller
@Slf4j
public class HomeController {

    private final UserService userService;
    private final RecentService recentService;
    private final ItemService itemService;
    private final FileService filesService;
    private final WishService wishService;
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
            List<Item> items = itemService.findSellingItem();
            List<Files> files = filesService.findAll();
            model.addAttribute("items", items);
            model.addAttribute("files", files);
            log.info("로그아웃 홈화면");
            return "home";
        }

        if(loginUser.getAuthorities().toString().equals("[ROLE_REPORT]")) {
            return "badUser";
        }

        if(loginUser.getAuthorities().toString().equals("[ROLE_ADMIN]")) {
            log.info("관리자 홈화면");
            return "admin/adminHome";
        }
        else {
            log.info("로그인 홈화면");
            //최근 본 상품 조회
            User user = userService.findById(loginUser.getUsername());
            List<Files> files = filesService.findAll();
            List<Item> itemList = new ArrayList<>();
            List<Recent> recentList = recentService.findByUserIdx(user.getUserIdx());
            for (Recent recent : recentList) {
                itemList.add(itemService.findOne(recent.getItemId()));
            }
            model.addAttribute("files", files);
            model.addAttribute("itemList", itemList);
            model.addAttribute("wishCnt",wishService.findByUser(user.getUserIdx()).size());
            model.addAttribute("itemList", itemList);

            //로그인 홈화면 모든 상품목록 조회
            List<Item> items = itemService.findSellingItem();
            model.addAttribute("items", items);
            return "loginHome";
        }
    }
}
