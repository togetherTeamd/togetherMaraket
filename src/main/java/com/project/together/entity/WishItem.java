package com.project.together.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;


@Data
@Entity
public class WishItem {

    @Id @GeneratedValue
    @Column(name = "wish_item_id")
    private Long id;

    @JsonIgnore
    @OneToOne(mappedBy = "wishItem", fetch = FetchType.LAZY)
    private Wish wish;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    private Long wisherId;// 중복 찜 방지용 위시리스트 주인(한 유저는 중복된 아이템을 찜할 수 없다)

    public void cancel() {
        getItem().removeWishCount();
    }

    public static WishItem createWishItem(Item item) {
        WishItem wishItem = new WishItem();
        wishItem.setItem(item);

        item.addWishCount();
        return wishItem;
    }
}
