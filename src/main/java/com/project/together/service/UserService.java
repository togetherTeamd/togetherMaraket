package com.project.together.service;

import com.project.together.entity.User;
import com.project.together.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    /**
     * 회원가입
     */
    @Transactional
    public Long join(User user) {
        validateDuplicateUser(user); // 중복아이디 검증
        userRepository.save(user);
        return user.getUserIdx();
    }

    private void validateDuplicateUser(User user) {
        List<User> findUsers = userRepository.findById(user.getUserId());
        if(!findUsers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 아이디 입니다.");
        }
    }

    public List<User> findById(String userId) {return userRepository.findById(userId);}

    public User findByIdx(Long userIdx) {
        return userRepository.findByIdx(userIdx);
    }

}
