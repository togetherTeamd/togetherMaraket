package com.project.together.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class SellItem {

    @Id
    @GeneratedValue
    @Column(name = "sell_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;//판매 상품

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sell_id")
    private Sell sell;//구매

    public void setStatus() {
        getItem().setItemStatus(ItemStatus.SELLING);
    }

    public static SellItem createSellItem(Item item) {
        SellItem sellItem = new SellItem();
        sellItem.setItem(item);

        return sellItem;
    }
}
