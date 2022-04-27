package com.project.together.entity;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Data
@ToString
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userIdx;

    @Column(name = "userId", unique = true)
    private String userId;

    private String userPw;

    private String userName;

    @Column(name="userPhone", unique = true)
    private String userPhone;

    @Embedded
    private Address address;

    private LocalDateTime createdAt;

    private int kind = 0;
    private int goodPicture = 0;
    private int goodTime = 0;

    private int mannerScore = 0;

    @OneToMany(mappedBy = "user")// 연관관계 주인 설정 1:N 일 경우 N쪽이 주인
    private List<Buy> buyList = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Sell> sellList = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Wish> wishList = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Review> reviewList = new ArrayList<>();

    @Transient
    private List<ChatRoom> roomList = new ArrayList<>();
}
