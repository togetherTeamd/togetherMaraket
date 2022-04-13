package com.project.together.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Sell {
    @Id
    @GeneratedValue
    @Column(name = "sell_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userIdx")
    private User user; //판매자

    @OneToMany(mappedBy = "sell", cascade = CascadeType.ALL)
    private List<SellItem> sellItems = new ArrayList<>();

    private LocalDateTime sellDate;

    /*
    연관 관계 메서드
     */
    public void setUser(User user) {
        this.user = user;
        user.getSellList().add(this);
    }

    public void addSellItem(SellItem sellItem) {
        sellItems.add(sellItem);
        sellItem.setSell(this);
    }

    public static Sell createSell(User user, SellItem... sellItems) {
        Sell sell = new Sell();
        sell.setUser(user);
        for (SellItem sellItem : sellItems) {
            sellItem.setStatus();
            sell.addSellItem(sellItem);
        }
        sell.setSellDate(LocalDateTime.now());
        return sell;
    }

}