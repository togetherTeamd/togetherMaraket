package com.project.together.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Wish {
    @Id @GeneratedValue
    @Column(name = "wish_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userIdx")
    private User user; // 찜한 사용자

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "wish_Item_id")
    private WishItem wishItem;

    private int isCancel;//취소 확인 용 db연관관계가 복잡해 삭제가 되지 않아 안에 속성으로 넣었습니다.

    //조회용으로 만들었습니다.
    private String wishItemName;
    private Long wishItemIdx;
    private int wishItemPrice;
    private String wishItemSeller;
    private LocalDateTime wishItemCreateTime;
    //조회용으로 만들었습니다.

    public void setUser(User user) {
        this.user = user;
        user.getWishList().add(this);
    }//위시 주인 생성

    public static Wish createWish(User user, WishItem wishItem) {
        Wish wish = new Wish();
        wish.setUser(user);
        //조회용
        wish.setWishItemName(wishItem.getItem().getName());
        wish.setWishItemIdx(wishItem.getItem().getId());
        wish.setWishItemPrice(wishItem.getItem().getPrice());
        wish.setWishItemSeller(wishItem.getItem().getSeller());
        wish.setWishItemCreateTime(wishItem.getItem().getCreatedAt());
        //여기까지
        wishItem.setWisherId(user.getUserIdx());// 중복방지용 위시유저 설정
        wish.setIsCancel(0);//취소시 1 등록시 0 반환
        wish.setWishItem(wishItem);
        
        return wish;
    }//위시 생성(위시 주인 추가, 위시리스트에 아이템 추가)

    /**
     * 찜목록 삭제
     */
    public void cancel() {
        wishItem.cancel();
    }

}
