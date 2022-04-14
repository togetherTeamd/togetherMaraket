package com.project.together.service;

import com.project.together.entity.Item;
import com.project.together.entity.ItemStatus;
import com.project.together.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class ItemServiceTest {
    @Autowired
    ItemService itemService;
    @Autowired
    UserService userService;
    @Autowired
    EntityManager em;
    @Test
    public void 검색() {
        User user = new User();
        user.setUserId("test");
        user.setUserName("test");
        userService.join(user);
        Item item = new Item();
        item.setSeller(user.getUserName());
        item.setName("test상품");
        itemService.saveItem(item);

        //System.out.println(itemService.findBySeller(user.getUserName()).get(0).getName());
        assertEquals(item, itemService.findBySeller(user.getUserId()).get(0));

    }

    @Test
    public void 판매중상품조회() {
        User user = new User();
        user.setUserId("test");
        user.setUserName("test");
        userService.join(user);

        Item item = new Item();
        item.setSeller(user.getUserName());
        item.setName("test상품");
        item.setItemStatus(ItemStatus.SELLING);
        itemService.saveItem(item);

        Item item2 = new Item();
        item2.setSeller(user.getUserName());
        item2.setName("test상품2");
        item2.setItemStatus(ItemStatus.SOLD);
        itemService.saveItem(item2);

        assertEquals(item,itemService.findSellingItem().get(0));
    }

    @Test
    public void 구매자설정() {

        User user = new User();
        user.setUserId("test");
        user.setUserName("test");
        userService.join(user);

        Item item = new Item();
        item.setSeller(user.getUserName());
        item.setName("test상품");
        item.setItemStatus(ItemStatus.SELLING);
        itemService.saveItem(item);

        System.out.println(itemService.findSellingItem());
        itemService.setBuyer(user.getUserIdx(), item.getId());
        System.out.println(itemService.findSellingItem());
    }

}