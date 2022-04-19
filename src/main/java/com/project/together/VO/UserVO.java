package com.project.together.VO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
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

    private int kind = 0;
    private int goodPicture = 0;
    private int goodTime = 0;

    private int mannerScore = 0;
}
