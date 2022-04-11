package com.project.together.controller;

import com.project.together.VO.UserVO;
import com.project.together.entity.Address;
import com.project.together.entity.User;
import com.project.together.repository.UserMapper;
import com.project.together.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.*;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    //Mybatis Mapper
    private final UserMapper userMapper;

    @GetMapping("/users/new")
    public String createForm(Model model) {
        model.addAttribute("userForm", new UserForm());
        log.info("회원가입 페이지");
        return "users/createUserForm";
    }

    @PostMapping("/users/new")
    public String create(@Valid UserForm form, BindingResult result) {

        if(result.hasErrors()) {
            return "users/createUserForm";
        }

        User user = new User();
        user.setUserId(form.getUserId());
        user.setUserPw(form.getUserPw());
        user.setUserName(form.getUserName());
        user.setUserPhone(form.getUserPhone());
        user.setCreatedAt(LocalDateTime.now());
        userService.join(user);
        log.info("회원가입 성공");
        return "redirect:/";
    }

    /***
     * @Desc : postman 전용, 서버개발
     * 회원정보 조회
     */
    @PostMapping("/selectUser")
    @ResponseBody
    public List<UserVO> selectUser(@RequestBody UserVO userVO) throws Exception{
        return userMapper.selectUser(userVO);
    }

    /***
     * @Desc : postman 전용, 서버개발
     * 회원정보 수정
     */
    @PostMapping("/updateUser")
    @ResponseBody
    public int updateUser(@RequestBody UserVO userVO) throws Exception{
        return userMapper.updateUser(userVO);
    }

    /***
     * @Desc : postman 전용, 서버개발
     * 회원가입
     */
    @PostMapping("/joinUser")
    @ResponseBody
    public int joinUser(@RequestBody UserVO userVO) throws Exception{
        return userMapper.joinUser(userVO);
    }
}
