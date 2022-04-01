package com.project.together.controller;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class LoginForm {

    @NotEmpty(message = "아이디를 입력해 주세요")
    private String userId;

    @NotEmpty(message = "비밀번호를 입력해 주세요")
    private String userPw;
}
