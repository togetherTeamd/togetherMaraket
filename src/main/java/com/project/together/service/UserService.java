package com.project.together.service;

import com.project.together.entity.User;
import com.project.together.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    /**
     * 회원가입
     */
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

    @Transactional(readOnly = true)
    public List<User> findById(String userId) {return userRepository.findById(userId);}

    @Transactional(readOnly = true)
    public User findByIdx(Long userIdx) {
        return userRepository.findByIdx(userIdx);
    }

}
