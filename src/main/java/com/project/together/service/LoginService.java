package com.project.together.service;

import com.project.together.entity.User;
import com.project.together.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class LoginService {
    private final UserRepository userRepository;

    /**
     *
     * @param userId
     * @param userPw
     * @return null이면 로그인 실패
     */
    public User login(String userId, String userPw) {

        return userRepository.findByLoginId(userId)
                .filter(m -> m.getUserPw().equals(userPw))
                .orElse(null);
    }
}
