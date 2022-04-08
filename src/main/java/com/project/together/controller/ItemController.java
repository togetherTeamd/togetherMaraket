package com.project.together.controller;

import com.project.together.entity.Item;
import com.project.together.entity.SellItem;
import com.project.together.entity.User;
import com.project.together.repository.SellRepository;
import com.project.together.service.ItemService;
import com.project.together.service.LoginService;
import com.project.together.service.SellService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;
    private final SellService sellService;
    private final LoginService loginService;
    private final SellRepository sellRepository;

    @GetMapping("items/new")
    public String createForm(Model model) {
        model.addAttribute("form", new ItemForm());
        return "items/createItemForm";
    }

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

        itemService.saveItem(item);
        sellService.sell(loginUser.getUserIdx(), item.getId());

        return "redirect:/";
    }

    @GetMapping(value = "/items")
    public String list(Model model) {
        List<Item> items = itemService.findItems();
        model.addAttribute("items", items);
        return "items/itemList";
    }
}
