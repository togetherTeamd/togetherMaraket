package com.project.together.controller;

import com.project.together.config.auth.PrincipalDetails;
import com.project.together.entity.*;
import com.project.together.service.CategoryService;
import com.project.together.service.FileService;
import com.project.together.service.UserService;
import com.project.together.service.WishService;
import lombok.RequiredArgsConstructor;
import org.hibernate.cache.spi.support.AbstractReadWriteAccess;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class WishController {

    private final WishService wishService;
    private final CategoryService categoryService;
    private final UserService userService;
    private final FileService filesService;
    @GetMapping(value = "/wish")
    public String wishList(Model model, @AuthenticationPrincipal PrincipalDetails user) {

        User loginUser = userService.findById(user.getUsername());

        List<Wish> wishList = wishService.findByUser(loginUser.getUserIdx());
        List<ItemCategory> itemCategories = categoryService.findAllItemCategory();
        List<Files> files = filesService.findAll();

        model.addAttribute("files", files);
        model.addAttribute("itemCategories", itemCategories);
        model.addAttribute("wishList", wishList);

        return "wish/wishList";
    }

    @PostMapping(value = "/items/{itemId}/wish")
    public String addWish(@PathVariable Long itemId, @AuthenticationPrincipal PrincipalDetails user) {

        User loginUser = userService.findById(user.getUsername());

        if(wishService.checkDuplicate(loginUser.getUserIdx(), itemId) == 1) {
            return "wish/rejectForm";
        }

        wishService.Wish(loginUser.getUserIdx(), itemId);

        return "redirect:/";
    }

    @PostMapping(value = "/wish/{wishId}/removeWish")
    public String removeWish(@PathVariable Long wishId, @AuthenticationPrincipal PrincipalDetails user) {

        User loginUser = userService.findById(user.getUsername());

        wishService.removeWish(loginUser.getUserIdx(), wishId);
        return "redirect:/";
    }
}
