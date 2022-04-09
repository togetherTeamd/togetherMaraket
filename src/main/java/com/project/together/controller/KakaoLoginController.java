package com.project.together.controller;

import com.project.together.entity.User;
import com.project.together.service.KakaoLoginService;
import com.project.together.service.LoginService;
import com.project.together.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;

@Controller
@Slf4j
@RequiredArgsConstructor
public class KakaoLoginController {

    private final KakaoLoginService kakaoLoginService;

    private final UserService userService;

    private final LoginService loginService;

    /***
     *
     * @param code : 인가코드로 access 토큰 받기 받기 자기
     * @param
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/login/oauthKakao")
    public String oauthKakao(
            @RequestParam(value = "code", required = false) String code
            , HttpServletRequest request
    ) throws Exception {
        log.info("code : " + code);

        String accessToken = kakaoLoginService.getAccessToken(code);

        log.info("accessToken : " + accessToken);

        HashMap<String, Object> userInfo = kakaoLoginService.getUserInfo(accessToken);
        System.out.println(userInfo);

        User loginUser = loginService.login((String)userInfo.get("email"), (String)userInfo.get("email"));

        if(loginUser == null) {
            User user = new User();
            user.setUserId((String)userInfo.get("email"));
            user.setUserPw((String)userInfo.get("email"));
            user.setUserName((String)userInfo.get("nickname"));
            user.setUserPhone("");
            user.setCreatedAt(LocalDateTime.now());
            userService.join(user);
            log.info("회원가입 성공");
            HttpSession session = request.getSession();
            session.setAttribute(SessionConstants.LOGIN_USER, user);
        } else {
            HttpSession session = request.getSession();
            session.setAttribute(SessionConstants.LOGIN_USER, loginUser);
        }

        return "redirect:/";
    }

}