package com.project.together.controller;

import com.project.together.entity.Item;
import com.project.together.entity.Sell;
import com.project.together.entity.SellItem;
import com.project.together.entity.User;
import com.project.together.repository.SellRepository;
import com.project.together.service.ItemService;
import com.project.together.service.LoginService;
import com.project.together.service.SellService;
import com.project.together.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ItemController {

    private final ItemService itemService;
    private final SellService sellService;
    private final LoginService loginService;
    private final SellRepository sellRepository;
    private final UserService userService;

    @GetMapping("items/new")
    public String createForm(Model model) {
        model.addAttribute("form", new ItemForm());
        return "items/createItemForm";
    }//상품생성

    @PostMapping("items/new")//
    public String create(ItemForm form, @SessionAttribute
            (name = SessionConstants.LOGIN_USER, required = false) User loginUser,
                         BindingResult result){
        if(result.hasErrors()) {
            return "items/createItemForm";
        }
        //User user = loginService.login(loginUser.getUserId(), loginUser.getUserPw());

        if(loginUser == null) {
            result.reject("sellFail", "로그인 후 이용해 주세요");
            return "items/createItemForm";
        }
        Item item = new Item();
        item.setName(form.getName());
        item.setPrice(form.getPrice());
        item.setSeller(loginUser.getUserId());
        item.setCreatedAt(LocalDateTime.now());

        itemService.saveItem(item);
        sellService.sell(loginUser.getUserIdx(), item.getId());

        return "redirect:/items";
    }

    @GetMapping(value = "/items")
    public String list(Model model) {
        List<Item> items = itemService.findSellingItem();
        model.addAttribute("items", items);
        return "items/itemList";
    }

    @GetMapping("items/{itemId}/edit")
    public String updateItemForm(@PathVariable("itemId") Long itemId, Model model) {
        Item item = itemService.findOne(itemId);

        ItemForm form = new ItemForm();
        form.setId(item.getId());
        form.setName(item.getName());
        form.setPrice(item.getPrice());

        model.addAttribute("form", form);
        return "items/updateItemForm";
    }

    @PostMapping(value = "/items/{itemId}/edit")
    public String updateItem(@PathVariable Long itemId, @ModelAttribute("form") ItemForm form) {
        itemService.updateItem(form.getId(), form.getName(), form.getPrice());
        return "redirect:/";
    }
}