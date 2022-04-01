package com.project.together.entity;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

@Entity
@NoArgsConstructor
@Data
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userIdx;

    @Column(name = "userId", unique = true)
    private String userId;
    private String userPw;
    private String userName;
    @Column(name="userPhone", unique = true)
    private String userPhone;
    @Embedded
    private Address address;
    private LocalDate createdAt;
    private Role role; //로그인 상태 [ADMIN, USER]

    /*@Builder
    public User(@NotEmpty String userId, @NotEmpty String userPw, @NotEmpty String userName
    , @NotEmpty String userPhone, @NotEmpty String address) {
        this.address = address;
        this.createdAt = LocalDate.now();
        this.userId = userId;
        this.userPw = userPw;
        this.userName = userName;
        this.userPhone = userPhone;
    }*/

}
