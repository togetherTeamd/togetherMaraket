package com.project.together.VO;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UserVO {

    private Long userIdx;

    private String userId;

    private String userPw;

    private String userName;

    private String userPhone;

    private String city;

    private String street;

    private String zipcode;

    private LocalDateTime createdAt;

}
