package com.project.together.controller;

import com.project.together.entity.*;
import com.project.together.repository.SellRepository;
import com.project.together.service.*;
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
    private final CategoryService categoryService;

    @GetMapping("items/new")
    public String createForm(Model model) {
        if(categoryService.findAll().isEmpty()) {
            categoryService.createCategory();
        }
        model.addAttribute("form", new ItemForm());
        model.addAttribute("categories", categoryService.findAll());
        return "items/createItemForm";
    }//상품생성

    @PostMapping("items/new")//
    public String create(ItemForm form, @RequestParam("categoryId") Long categoryId,
                         @SessionAttribute(name = SessionConstants.LOGIN_USER, required = false)
                                 User loginUser, BindingResult result) {
        System.out.println("상품정보 잘 들어왔나 : " + form.toString());
        System.out.println("카테고리 아이디는 왔는가 : " + categoryId);
        System.out.println("유저정보 왔는가 : " + loginUser.toString());
        if(result.hasErrors()) {
            return "items/createItemForm";
        }

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
        categoryService.addCategory(item.getId(), categoryId);
        sellService.sell(loginUser.getUserIdx(), item.getId());

        return "redirect:/";
    }

    @GetMapping(value = "/items")
    public String list(Model model) {
        List<Item> items = itemService.findSellingItem();
        List<ItemCategory> itemCategories = categoryService.findAllItemCategory();

        model.addAttribute("items", items);
        model.addAttribute("itemCategories", itemCategories);
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