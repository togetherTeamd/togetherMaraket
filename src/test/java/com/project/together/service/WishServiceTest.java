package com.project.together.service;

import com.project.together.entity.Item;
import com.project.together.entity.User;
import com.project.together.entity.Wish;
import com.project.together.entity.WishItem;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class WishServiceTest {

    @Autowired UserService userService;
    @Autowired ItemService itemService;
    @Autowired WishService wishService;

    @Test
    public void 찜() {

        User user = new User();
        user.setUserId("test");
        user.setUserName("test");
        userService.join(user);

        Item item = new Item();
        item.setSeller(user.getUserName());
        item.setName("test상품");
        itemService.saveItem(item);

        Wish wish = wishService.Wish(user.getUserIdx(), item.getId());

        assertEquals(user.getUserIdx(), wish.getUser().getUserIdx());
    }

    @Test
    public void 찜목록조회() {
        User user = new User();
        user.setUserId("test");
        user.setUserName("test");
        userService.join(user);

        Item item = new Item();
        item.setSeller(user.getUserName());
        item.setName("test상품");
        itemService.saveItem(item);

        Item item2 = new Item();
        item2.setSeller(user.getUserName());
        item2.setName("test상품2");
        itemService.saveItem(item2);

        wishService.Wish(user.getUserIdx(), item.getId());
        wishService.Wish(user.getUserIdx(), item2.getId());

        List<Item> items = new ArrayList<>();

        Wish wish = wishService.findByUser(user.getUserIdx()).get(0);

        wishService.findByUser(user.getUserIdx()).get(0).getWishItem().getItem().getId();


    //System.out.println(wish.getId());

        for(Wish wish1 : user.getWishList()) {
            System.out.println(wish1.getWishItem().getItem().getName());
        }

    }
//차후 해야할것: 중복 찜하기x, 본인 상품 찜하기x
}