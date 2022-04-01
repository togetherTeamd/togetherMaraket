package com.project.together.controller;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class UserForm {

    @NotEmpty(message = "아이디를 입력해 주세요")
    private String userId;
    @NotEmpty(message = "비밀번호를 입력해 주세요")
    private String userPw;
    @NotEmpty(message = "이름을 입력해 주세요")
    private String userName;
    @NotEmpty(message = "전화번호를 입력해 주세요")
    private String userPhone;
}
