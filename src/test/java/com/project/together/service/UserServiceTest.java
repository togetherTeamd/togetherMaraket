package com.project.together.service;

import com.project.together.entity.User;
import com.project.together.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class UserServiceTest {

    @Autowired UserService userService;
    @Autowired UserRepository userRepository;
    @Autowired EntityManager em;

    @Test
    public void 회원가입() throws Exception {

        User user = new User();
        user.setUserId("kim");

        Long saveId= userService.join(user);

        em.flush();
        assertEquals(user, userRepository.findByIdx(saveId));
    }

    @Test
    public void 중복_회원_예외() throws Exception {

        User user1 = new User();
        user1.setUserId("kim");

        User user2 = new User();
        user2.setUserId("kim");

        userService.join(user1);
        try {
            userService.join(user2);
        } catch(IllegalStateException e) {
            return;
        }

        fail("예외 발생");

    }

}