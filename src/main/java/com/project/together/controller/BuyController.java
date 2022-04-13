package com.project.together.controller;

import com.project.together.entity.Item;
import com.project.together.entity.User;
import com.project.together.service.BuyService;
import com.project.together.service.ItemService;
import com.project.together.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.*;
@Controller
@RequiredArgsConstructor
@Slf4j
public class BuyController {

    private final BuyService buyService;
    private final UserService userService;
    private final ItemService itemService;

    /*@GetMapping("/buy")
    public String createForm(Model model) {
        model.addAttribute("form", new ItemForm());
        return "buy/buyForm";
    }

    @PostMapping("/buy")//
    public String create(ItemForm form, @SessionAttribute
            (name = SessionConstants.LOGIN_USER, required = false) User loginUser,
                         BindingResult result){
        if(result.hasErrors()) {
            return "items/createItemForm";
        }
        //User user = loginService.login(loginUser.getUserId(), loginUser.getUserPw());

        Item item = new Item();
        item.setName(form.getName());
        item.setPrice(form.getPrice());

        itemService.saveItem(item);
        buyService.buy(loginUser.getUserIdx(), item.getId());

        return "redirect:/";
    }*/

    @GetMapping("items/{itemId}/buy")
    public String buyForm(Model model, @PathVariable Long itemId) {
        BuyForm buyForm = new BuyForm();
        model.addAttribute("form", buyForm);
        return "items/buyForm";
    }

    @PostMapping(value = "/items/{itemId}/buy")
    public String buyItem(@PathVariable Long itemId, @ModelAttribute("form") BuyForm form) {

        if(userService.findById(form.getId()).isEmpty()) {
            log.info(form.getId());
            return "redirect:/";
        }
        itemService.setBuyer(userService.findById(form.getId()).get(0).getUserIdx(),itemId);

        return "redirect:/items";
    }

    @GetMapping(value = "/buy")
    public String list(Model model, @SessionAttribute
            (name = SessionConstants.LOGIN_USER, required = false) User loginUser) {

        List<Item> items = itemService.findByBuyer(loginUser.getUserId());
        model.addAttribute("items", items);

        return "buy/buyList";
    }
}