package com.project.together.controller;

import com.project.together.entity.Item;
import com.project.together.entity.User;
import com.project.together.service.BuyService;
import com.project.together.service.ItemService;
import com.project.together.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import java.util.*;
@Controller
@RequiredArgsConstructor
public class BuyController {

    private final BuyService buyService;
    private final UserService userService;
    private final ItemService itemService;

    @GetMapping("/buy")
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
    }
}