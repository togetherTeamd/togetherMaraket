package com.project.together.config.auth;

import com.project.together.entity.User;
import com.project.together.repository.UserRepository;
import com.project.together.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PrincipalDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserService userService;
    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        User user = userService.findById(userId);
        System.out.println("유저아이디:"+userId);
        //log.info(userId);
        //log.info(user.getUserId());
        //Optional<User> optionalUser = userRepository.findByLoginId(userId);
        if(user == null) {
            return null;
        }
        return new PrincipalDetails(user);
        //log.info(optionalUser.toString());
        /*return optionalUser
                .map(PrincipalDetails::new)
                .orElse(null);*/
    }
}
