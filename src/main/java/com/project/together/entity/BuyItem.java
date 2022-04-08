package com.project.together.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class BuyItem {

    @Id
    @GeneratedValue
    @Column(name = "buy_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;//구매 상품

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "buy_id")
    private Buy buy;//구매

    public void setStatus() {
        getItem().setItemStatus(ItemStatus.SOLD);
    }
    /*
    생성 메서드
     */
    public static BuyItem createBuyItem(Item item) {
        BuyItem buyItem = new BuyItem();
        buyItem.setItem(item);

        return buyItem;
    }

}
