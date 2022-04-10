package com.project.together.controller;

import com.project.together.entity.User;
import com.project.together.entity.Item;
import com.project.together.entity.Wish;
import com.project.together.service.WishService;
import lombok.RequiredArgsConstructor;
import org.hibernate.cache.spi.support.AbstractReadWriteAccess;
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

    @GetMapping(value = "/wish")
    public String wishList(Model model, @SessionAttribute
            (name = SessionConstants.LOGIN_USER, required = false) User loginUser) {

        List<Wish> wishList = wishService.findByUser(loginUser.getUserIdx());

        model.addAttribute("wishList", wishList);

        return "wish/wishList";
    }

    @PostMapping(value = "/items/{itemId}/wish")
    public String addWish(@PathVariable Long itemId, @SessionAttribute
            (name = SessionConstants.LOGIN_USER, required = false) User loginUser) {

        wishService.Wish(loginUser.getUserIdx(), itemId);

        return "redirect:/";
    }

    /*@PostMapping(value = "/items/{itemId}/removeWish")
    public String removeWish(@PathVariable Long itemId, @SessionAttribute
            (name = SessionConstants.LOGIN_USER, required = false) User loginUser) {

        wishService.removeWish(itemId, loginUser.getUserIdx());

        return "redirect:/";
    }*/
}
