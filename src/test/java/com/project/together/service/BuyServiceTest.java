package com.project.together.service;

import com.project.together.entity.*;
import com.project.together.repository.BuyRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;
import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class BuyServiceTest {

    @Autowired
    EntityManager em;
    @Autowired
    BuyService buyService;
    @Autowired
    BuyRepository buyRepository;

    @Test
    public void 구매() throws Exception {
        User user = new User();
        user.setUserId("test");
        user.setUserPw("1234");
        user.setUserPhone("1234");
        user.setUserName("테스트");
        em.persist(user);

        Category category = new Category();
        category.setName("도서");
        em.persist(category);

        List<Category> categories = new ArrayList<>();
        Item item = new Item();
        item.setName("테스트 상품");
        item.setCategories(categories);
        em.persist(item);

        Long buyId = buyService.buy(user.getUserIdx(), item.getId());

        Buy getBuy = buyRepository.findOne(buyId);

        assertEquals(ItemStatus.SOLD, getBuy.getBuyItems().get(0).getItem().getItemStatus());

    }
}