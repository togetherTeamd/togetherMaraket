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

import javax.servlet.http.HttpServletRequest;
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
    private final LoginService loginService;

    @GetMapping("items/new")
    public String createForm(Model model, @SessionAttribute(name = SessionConstants.LOGIN_USER, required = false)
            User loginUser) {
        if(categoryService.findAll().isEmpty()) {
            categoryService.createCategory();
        }
        User user = loginService.login(loginUser.getUserId(), loginUser.getUserPw());
        model.addAttribute("user", user);
        model.addAttribute("form", new ItemForm());
        model.addAttribute("categories", categoryService.findAll());
        return "items/createItemForm";
    }//상품생성

    @PostMapping("items/new")//
    public String create(ItemForm form, @RequestParam("categoryId") Long categoryId,
                         @SessionAttribute(name = SessionConstants.LOGIN_USER, required = false)
                                 User loginUser, BindingResult result) {
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

    @GetMapping(value = "/items2")
    public String list2(Model model) {
        List<Item> items = itemService.findSellingItem();
        List<ItemCategory> itemCategories = categoryService.findAllItemCategory();

        model.addAttribute("items", items);
        model.addAttribute("itemCategories", itemCategories);
        return "items2/itemList2";
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