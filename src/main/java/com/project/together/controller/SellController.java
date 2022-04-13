package com.project.together.controller;

import com.project.together.entity.Item;
import com.project.together.entity.Sell;
import com.project.together.entity.SellItem;
import com.project.together.entity.User;
import com.project.together.service.ItemService;
import com.project.together.service.SellService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.cache.spi.SecondLevelCacheLogger_$logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class SellController {

    private final ItemService itemService;

    @GetMapping(value = "/sell")
    public String sellList(Model model, @SessionAttribute
            (name = SessionConstants.LOGIN_USER, required = false) User loginUser) {

        if(loginUser != null) {
            log.info("회원 정보 얻어오기 성공");
        }

        List<Item> items = itemService.findBySeller(loginUser.getUserId());
        model.addAttribute("items", items);

        return "sell/sellList";
    }
}
