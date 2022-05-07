package com.project.together.service;

import com.project.together.entity.Item;
import com.project.together.entity.ReviewContents;
import com.project.together.entity.User;
import com.project.together.repository.ReviewContentsRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.core.AutoConfigureCache;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class ReviewServiceTest {

    @Autowired ItemService itemService;
    @Autowired UserService userService;
    @Autowired ReviewService reviewService;
    @Autowired
    ReviewContentsRepository reviewContentsRepository;

    @Test
    public void 리뷰추가() {

        User user = new User();
        user.setUserId("test");
        user.setUserName("test");
        userService.join(user);

        Item item = new Item();
        item.setSeller(user.getUserName());
        item.setName("test상품");
        itemService.saveItem(item);

        if(reviewContentsRepository.findAll().isEmpty()) {
            reviewService.createReview();
        }
        ReviewContents reviewContents = reviewContentsRepository.findAll().get(0);

        //Long id = reviewService.addReview(user.getUserIdx(), item.getId(), reviewContents.getId());

        assertEquals(user.getReviewList().get(0), item.getReview());


    }
}