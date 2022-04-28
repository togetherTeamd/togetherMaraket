package com.project.together.controller;

import com.project.together.VO.UserVO;
import com.project.together.entity.Address;
import com.project.together.entity.User;
import com.project.together.repository.UserMapper;
import com.project.together.service.LoginService;
import com.project.together.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.*;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    private final LoginService loginService;

    //Mybatis Mapper
    private final UserMapper userMapper;

    @GetMapping("/users/new")
    public String createForm(Model model) {
        model.addAttribute("userForm", new UserForm());
        log.info("회원가입 페이지");
        return "users/createUserForm";
    }

    @PostMapping("/users/new")
    public String create(@Valid UserForm form, BindingResult result, Model model) {

        if(result.hasErrors()) {
            return "users/createUserForm";
        }

        //try{
        User user = new User();
        Address address = new Address();
        user.setUserId(form.getUserId());
        user.setUserPw(form.getUserPw());
        user.setUserName(form.getUserName());
        user.setUserPhone(form.getUserPhone());
        user.setCreatedAt(LocalDateTime.now());
        address.setCity(form.getCity());
        address.setStreet(form.getStreet());
        address.setZipcode(form.getZipcode());
        user.setAddress(address);
        userService.join(user);
        log.info("회원가입 성공");
            return "redirect:/";
        /*} catch (IllegalStateException is) {
            return "items/rejectForm";
        }*/

    }

    /***
     * @Desc : 화면 호출
     * 회원가입
     */
    @GetMapping("/createUserForm2")
    public String createUserForm2(
            HttpServletRequest request, Model model) throws Exception{
        return "users/createUserForm2";
    }

    /***
     * @Desc : 화면 호출
     * 회원정보 mybatis DB 저장
     */
    @PostMapping("/createUserForm2")
    public String createUserForm2(@ModelAttribute UserVO userVO,
            HttpServletRequest request, Model model) throws Exception{
        try {
            int result = userMapper.joinUser(userVO);
            System.out.println(result);
        } catch(DataIntegrityViolationException e) {
            return "items/rejectForm";
        }
        return "redirect:/";
    }

    /***
     * @Desc : 화면 호출 및 유저 정보 조회
     * 회원정보 수정 - mybatis
     */
    @GetMapping("/updateUserForm")
    public String updateUserForm(
            HttpServletRequest request,
            @SessionAttribute(name = SessionConstants.LOGIN_USER, required = false) User loginUser
            , Model model) throws Exception{

        UserVO userVO = new UserVO();
        userVO.setUserId(loginUser.getUserId());
        List<UserVO> userVOList = userMapper.selectUser(userVO);

        //userID 는 무조건 하나이니깐 List 0번째에서 가져오면 됨.
        model.addAttribute("user", userVOList.get(0));
        System.out.println("updateUserForm 데이터 확인 : " + userVOList.get(0).toString());

        HttpSession session = request.getSession();                         // 세션이 있으면 있는 세션 반환, 없으면 신규 세션을 생성하여 반환
        session.setAttribute(SessionConstants.LOGIN_USER, loginUser);

        return "users/updateUserForm";
    }

    /***
     * @Desc : 유저 정보 수정 액션
     * 회원정보 수정 - mybatis
     */
    @PostMapping("/updateUserForm")
    public String updateUserForm(@ModelAttribute UserVO userVO, Model model) throws Exception{
        System.out.println("updateUserForm 수정할 데이터 확인 : " + userVO.toString());
        List<UserVO> originUserVO = userMapper.selectUser(userVO);
        if(!originUserVO.get(0).getUserPw().equals(userVO.getUserPw())){
            model.addAttribute("user", originUserVO.get(0));
            model.addAttribute("incorrectPW", "비밀번호가 틀렸습니다.");
            return "users/updateUserForm";
        }
        int check = userMapper.updateUser(userVO);
        System.out.println(check);
        return "redirect:/";
    }

    /***
     * @Desc : 화면 호출 및 유저 정보 조회
     * 회원정보 수정 - jpa
     */
    @GetMapping("/updateUserForm2")
    public String updateUserForm2(
            HttpServletRequest request,
            @SessionAttribute(name = SessionConstants.LOGIN_USER, required = false) User loginUser
            , Model model) throws Exception{

        User userInfo = loginService.login(loginUser.getUserId(), loginUser.getUserPw());
        UserForm userForm = new UserForm();
        userForm.setUserIdx(userInfo.getUserIdx());
        userForm.setUserId(userInfo.getUserId());
        userForm.setUserPw("");
        userForm.setUserName(userInfo.getUserName());
        userForm.setUserPhone(userInfo.getUserPhone());
        userForm.setCity(userInfo.getAddress()==null?"":userInfo.getAddress().getCity());
        userForm.setZipcode(userInfo.getAddress()==null?"":userInfo.getAddress().getZipcode());
        userForm.setStreet(userInfo.getAddress()==null?"":userInfo.getAddress().getStreet());

        //userID 는 무조건 하나이니깐 List 0번째에서 가져오면 됨.
        model.addAttribute("user", userForm);

        HttpSession session = request.getSession();                         // 세션이 있으면 있는 세션 반환, 없으면 신규 세션을 생성하여 반환
        session.setAttribute(SessionConstants.LOGIN_USER, userInfo);

        return "users/updateUserForm2";
    }

    /***
     * @Desc : 유저 정보 수정 액션
     * 회원정보 수정 - jpa
     */
    @PostMapping("/updateUserForm2")
//    public String updateUserForm2(@ModelAttribute UserVO userVO, Model model) throws Exception{
    public String updateUserForm2(@Valid UserForm form, Model model) throws Exception{

        User user = new User();

        user.setUserIdx(form.getUserIdx());
        user.setUserId(form.getUserId());
        user.setUserPw(form.getUserPw());
        user.setUserName(form.getUserName());
        user.setUserPhone(form.getUserPhone());

        Address address = new Address();
        address.setCity(form.getCity());
        address.setZipcode(form.getZipcode());
        address.setStreet(form.getStreet());

        user.setAddress(address);

        //update쪽에 데이터를 조회해온것에서 변경된 사항을 변경하기
        Long check = userService.update(user);
        System.out.println(check);
        return "redirect:/";
    }

    /***
     * @throws
     */
    @PostMapping("/updateUserForm2_postman")
    @ResponseBody
    public String updateUserForm2_postman(@RequestBody UserVO userVO, Model model) throws Exception{
        User user = new User();

        user.setUserIdx(userVO.getUserIdx());
        user.setUserId(userVO.getUserId());
        user.setUserPw(userVO.getUserPw());
        user.setUserName(userVO.getUserName());
        user.setUserPhone(userVO.getUserPhone());

        Address address = new Address();
        address.setCity(userVO.getCity());
        address.setZipcode(userVO.getZipcode());
        address.setStreet(userVO.getStreet());

        user.setAddress(address);

        Long check = userService.update(user);
        System.out.println(check);
        return "success";
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

    @ResponseBody
    @RequestMapping(value="/idCheck", method=RequestMethod.POST)
    public int IdCheck(@RequestBody String memberId) throws Exception {

        int count = 0;
        if(memberId != null) count = userService.idCheck(memberId);
        return count;
    }
}
